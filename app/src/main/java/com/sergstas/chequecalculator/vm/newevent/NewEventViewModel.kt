package com.sergstas.chequecalculator.vm.newevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergstas.domain.models.SessionData
import com.sergstas.domain.models.UserData

class NewEventViewModel: ViewModel() {
    val eventName: LiveData<String> get() = _eventName
    private val _eventName = MutableLiveData("")

    val eventDate: LiveData<Long> get() = _eventDate
    private val _eventDate = MutableLiveData<Long>()

    val allUsers: LiveData<List<UserData>> get() = _allUsers
    private val _allUsers = MutableLiveData(emptyList<UserData>())

    val members: LiveData<List<UserData>> get() = _members
    private val _members = MutableLiveData(emptyList<UserData>())

    val receipts: LiveData<List<SessionData.ReceiptData>> get() = _receipts
    private val _receipts = MutableLiveData(emptyList<SessionData.ReceiptData>())

    fun setEventName(name: String) {
        _eventName.value = name
    }

    fun setEventDate(timestamp: Long) {
        _eventDate.value = timestamp
    }

    fun setMembersList(list: List<UserData>) {
        _members.value = list
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