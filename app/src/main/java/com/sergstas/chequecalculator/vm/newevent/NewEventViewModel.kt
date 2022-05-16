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
    companion object {
        fun nextId() = ++idIndex
        private var idIndex = 0
    }

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
    val receiptPositions: LiveData<List<PositionIndexed>?> get() = _receiptPositions

    private val _receiptPositions = MutableLiveData<List<PositionIndexed>?>()

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
                positions = _receiptPositions.value?.map { it.toPositionData() } ?: return,
            ))
        }
    }

    fun replaceReceipt(old: SessionData.ReceiptData) {
        val index = _receipts.value?.indexOf(old)?.takeIf { it >= 0 } ?: return
        val new = SessionData.ReceiptData(
            name = _receiptName.value ?: return,
            payer = _receiptPayer.value ?: return,
            positions = _receiptPositions.value?.map { it.toPositionData() } ?: return,
        )
        _receipts.value = _receipts.value?.toMutableList()?.apply { set(index, new) }
    }

    fun deleteReceipt(receipt: SessionData.ReceiptData) {
        _receipts.value = _receipts.value?.toMutableList()?.apply { remove(receipt) }
    }

    fun setEditReceipt(receipt: SessionData.ReceiptData?) {
        _receiptName.value = receipt?.name
        _receiptPayer.value = receipt?.payer
        _receiptPositions.value = receipt?.positions?.map(PositionIndexed::fromPositionData)
    }

    fun updateEditReceiptName(name: String) {
        _receiptName.value = name
    }

    fun updateEditReceiptPayer(name: String) {
        val user = members.value?.firstOrNull { it.name == name } ?: return
        _receiptPayer.value = user
    }

    fun addPosition() {
        val position = PositionIndexed(nextId(), "", 0.0, emptyList())
        _receiptPositions.value = _receiptPositions.value?.toMutableList()?.apply { add(position) }
            ?: listOf(position)
    }

    fun deletePosition(id: Int) {
        val pos = findPos(id) ?: return
        val editedPositions = _receiptPositions.value?.toMutableList()?.apply { remove(pos) } ?: return
        if (editedPositions == _receiptPositions.value) return
        _receiptPositions.value = editedPositions
    }

    fun editPositionTitle(id: Int, title: String) {
        val pos = findPos(id) ?: return
        if (pos.name == title) return
        val edited = pos.copy(name = title)
        val index = indexOfPos(id) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    fun editPositionPrice(id: Int, price: Double) {
        val pos = findPos(id) ?: return
        if (abs(pos.price - price) < 1e-2) return
        val edited = pos.copy(price = price)
        val index = indexOfPos(id) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    fun addPart(id: Int, username: String) {
        val user = members.value?.firstOrNull { it.name == username } ?: return
        val pos = findPos(id) ?: return
        if (user in pos.parts.map { it.user }) return
        val users = pos.parts.map { it.user }.toMutableList().apply { add(user) }
        val parts = SessionData.PartData.distributeBetween(users)
            .map(PositionIndexed.PartIndexed::fromPartData)
        val edited = pos.copy(parts = parts)
        val index = indexOfPos(id) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    fun editPart(positionId: Int, partId: Int, value: Double) {
        val pos = findPos(positionId) ?: return
        val part = pos.parts.firstOrNull { it.id == partId } ?: return
        if (abs(part.value - value) < 1e-2) return
        val partIndex = pos.parts.indexOf(part).takeIf { it >= 0 } ?: return
        val editedPart = part.copy(value = value)
        val editedParts = pos.parts.toMutableList().apply { set(partIndex, editedPart) }
        val editedPos = pos.copy(parts = editedParts)
        val posIndex = indexOfPos(positionId) ?: return
        _receiptPositions.value = positionsWithModifiedItem(posIndex, editedPos)
    }

    fun removePart(positionId: Int, partId: Int) {
        val pos = findPos(positionId) ?: return
        val part = pos.parts.firstOrNull { it.id == partId } ?: return
        val editedParts = pos.parts.toMutableList().apply { remove(part) }
        val edited = pos.copy(parts = editedParts)
        val index = indexOfPos(positionId) ?: return
        _receiptPositions.value = positionsWithModifiedItem(index, edited)
    }

    private fun findPos(id: Int) =
        _receiptPositions.value?.firstOrNull { it.id == id }

    private fun indexOfPos(id: Int) =
        _receiptPositions.value?.indexOf(findPos(id))?.takeIf { it >= 0 }

    private fun positionsWithModifiedItem(index: Int, edited: PositionIndexed) =
        _receiptPositions.value?.toMutableList()?.apply { set(index, edited) }

    data class PositionIndexed(
        val id: Int,
        val name: String,
        val price: Double,
        val parts: List<PartIndexed>,
        val messages: List<String> = emptyList(),
    ) {
        companion object {
            fun fromPositionData(data: SessionData.PositionData) =
                PositionIndexed(
                    id = nextId(),
                    name = data.name,
                    price = data.price,
                    parts = data.parts.map { PartIndexed.fromPartData(it) },
                )
        }

        fun toPositionData()  = SessionData.PositionData(
            name = name,
            price = price,
            parts = parts.map { it.toPartData() }
        )

        data class PartIndexed(
            val id: Int,
            val user: UserData,
            val value: Double,
        ) {
            companion object {
                fun fromPartData(data: SessionData.PartData) =
                    PartIndexed(
                        id = nextId(),
                        user = data.user,
                        value = data.part,
                    )
            }

            fun toPartData() = SessionData.PartData(
                user = user,
                part = value,
            )
        }
    }
}
