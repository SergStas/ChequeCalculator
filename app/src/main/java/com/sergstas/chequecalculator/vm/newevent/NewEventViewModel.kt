package com.sergstas.chequecalculator.vm.newevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergstas.chequecalculator.validation.INewEventValidator
import com.sergstas.domain.models.SessionData
import com.sergstas.domain.models.UserData
import com.sergstas.domain.repository.IUserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

class NewEventViewModel @Inject constructor(
    private val validator: INewEventValidator,
    private val usersRepository: IUserRepository,
): ViewModel() {
    val eventName: LiveData<String> get() = _eventName
    private val _eventName = MutableLiveData<String>()

    val eventDate: LiveData<String> get() = _eventDate
    private val _eventDate = MutableLiveData<String>()

    val allUsers: LiveData<List<UserData>> get() = _allUsers
    private val _allUsers = MutableLiveData(emptyList<UserData>())

    val members: LiveData<List<UserData>> get() = _members
    private val _members = MutableLiveData(emptyList<UserData>())

    val receipts: LiveData<List<SessionData.ReceiptData>> get() = _receipts
    private val _receipts = MutableLiveData(emptyList<SessionData.ReceiptData>())

    val receiptName: LiveData<String?> get() = _receiptName
    private val _receiptName = MutableLiveData<String?>()

    val receiptPayer: LiveData<UserData?> get() = _receiptPayer
    private val _receiptPayer = MutableLiveData<UserData?>()

    val receiptPositions: LiveData<List<PositionWithMessage>?> get() = _receiptPositions
    private val _receiptPositions = MutableLiveData<List<PositionWithMessage>?>()

    init {
        viewModelScope.launch {
            _allUsers.value = usersRepository.getAllUsers()
        }
    }

    fun setEventName(name: String) {
        _eventName.value = name
    }

    fun setEventDate(token: String) {
        val valid = validator.validateDate(token)
        if (!valid) return
        _eventDate.value = token
    }

    fun addMember(user: UserData) {
        if (user !in (_allUsers.value ?: emptyList())) return
        _members.value = _members.value?.toMutableList()?.toMutableList()?.apply { add(user) }
    }

    fun deleteMember(user: UserData) {
        _members.value = _members.value?.toMutableList()?.apply { remove(user) }
    }

    fun saveReceipt() {
        _receipts.value = _receipts.value?.toMutableList()?.apply {
            add(SessionData.ReceiptData(
                name = _receiptName.value ?: return,
                payer = _receiptPayer.value ?: return,
                positions = _receiptPositions.value?.map { it.position } ?: return,
            ))
        }
    }

    fun replaceReceipt(old: SessionData.ReceiptData) {
        val index = _receipts.value?.indexOf(old)?.takeIf { it >= 0 } ?: return
        val new = SessionData.ReceiptData(
            name = _receiptName.value ?: return,
            payer = _receiptPayer.value ?: return,
            positions = _receiptPositions.value?.map { it.position } ?: return,
        )
        _receipts.value = _receipts.value?.toMutableList()?.apply { set(index, new) }
    }

    fun deleteReceipt(receipt: SessionData.ReceiptData) {
        _receipts.value = _receipts.value?.toMutableList()?.apply { remove(receipt) }
    }

    fun setEditReceipt(receipt: SessionData.ReceiptData?) {
        _receiptName.value = receipt?.name
        _receiptPayer.value = receipt?.payer
        _receiptPositions.value = receipt?.positions?.map(::PositionWithMessage)
    }

    fun updateEditReceiptName(name: String) {
        _receiptName.value = name
    }

    fun updateEditReceiptPayer(name: String) {
        val user = members.value?.firstOrNull { it.name == name } ?: return
        _receiptPayer.value = user
    }

    fun addPosition() {
        val position = PositionWithMessage(SessionData.PositionData("", 0.0, emptyList()))
        _receiptPositions.value = _receiptPositions.value?.toMutableList()?.apply { add(position) }
            ?: listOf(position)
    }

    fun deletePosition(position: SessionData.PositionData) {
        val pos = findPos(position) ?: return
        val editedPositions = _receiptPositions.value?.toMutableList()?.apply { remove(pos) } ?: return
        if (editedPositions == _receiptPositions.value) return
        _receiptPositions.value = editedPositions
    }

    fun editPositionTitle(position: SessionData.PositionData, title: String) {
        if (position.name == title) return
        val edited = findPos(position)?.copy(position = position.copy(name = title)) ?: return
        val index = indexOfPos(position) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    fun editPositionPrice(position: SessionData.PositionData, price: Double) {
        if (abs(position.price - price) < 1e-2) return
        val edited = findPos(position)?.copy(position = position.copy(price = price)) ?: return
        val index = indexOfPos(position) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    fun addPart(position: SessionData.PositionData, username: String) {
        val user = members.value?.firstOrNull { it.name == username } ?: return
        if (user in position.parts.map { it.user }) return
        val users = position.parts.map { it.user }.toMutableList().apply { add(user) }
        val parts = SessionData.PartData.distributeBetween(users)
        val edited = findPos(position)?.copy(position = position.copy(parts = parts)) ?: return
        val index = indexOfPos(position) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    fun editPart(position: SessionData.PositionData, part: SessionData.PartData, value: Double) {
        if (part !in position.parts || abs(part.part - value) < 1e-2) return
        val editedPart = part.copy(part = value)
        val partIndex = position.parts.indexOf(part).takeIf { it >= 0 } ?: return
        val editedParts = position.parts.toMutableList().apply { set(partIndex, editedPart) }
        val editedPos = findPos(position)?.copy(position = position.copy(parts = editedParts)) ?: return
        val posIndex = indexOfPos(position) ?: return
        _receiptPositions.value = positionsWithModifiedItem(posIndex, editedPos)
    }

    fun removePart(position: SessionData.PositionData, part: SessionData.PartData) {
        if (part !in position.parts) return
        val parts = position.parts.toMutableList().apply { remove(part) }
        val edited = findPos(position)?.copy(position = position.copy(parts = parts)) ?: return
        val index = indexOfPos(position) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    private fun findPos(position: SessionData.PositionData) =
        _receiptPositions.value?.firstOrNull { it.position == position }

    private fun indexOfPos(position: SessionData.PositionData) =
        _receiptPositions.value?.indexOf(findPos(position))?.takeIf { it >= 0 }

    private fun positionsWithModifiedItem(index: Int, edited: PositionWithMessage) =
        _receiptPositions.value?.toMutableList()?.apply { set(index, edited) }

    data class PositionWithMessage(
        val position: SessionData.PositionData,
        val messages: List<String> = emptyList(),
    )
}
