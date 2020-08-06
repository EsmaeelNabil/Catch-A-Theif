package com.esmaeel.catchathief.ui.TelegramLink

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmaeel.catchathief.Utils.Contract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TelegramViewModel @ViewModelInject constructor(
    private val repository: TelegramRepository
) : ViewModel() {

    var users: MutableLiveData<Contract<FoundedUsers?>> = MutableLiveData()


    fun getUsers(token: String) {
        viewModelScope.launch {
            repository.getUsersWithToken(token)
                .flowOn(Dispatchers.IO)
                .collect { data ->
                    users.value = data
                }
        }
    }


}