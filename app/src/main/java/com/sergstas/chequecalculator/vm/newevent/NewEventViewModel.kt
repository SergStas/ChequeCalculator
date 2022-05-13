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

    fun addReceipt(receipt: SessionData.ReceiptData) {
        _receipts.value = _receipts.value?.toMutableList()?.apply { add(receipt) }
    }

    fun deleteMember(user: UserData) {
        _members.value = _members.value?.toMutableList()?.apply { remove(user) }
    }

    fun deleteReceipt(receipt: SessionData.ReceiptData) {
        _receipts.value = _receipts.value?.toMutableList()?.apply { remove(receipt) }
    }

    fun replaceReceipt(old: SessionData.ReceiptData, new: SessionData.ReceiptData) {
        val index = _receipts.value?.indexOf(old)
        if (index == null || index < 0) return
        _receipts.value = _receipts.value?.toMutableList()?.apply { set(index, new) }
    }
}