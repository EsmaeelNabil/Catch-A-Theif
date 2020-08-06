package com.esmaeel.catchathief.ui.TelegramLink

import android.content.Context
import com.esmaeel.catchathief.Utils.Contract
import com.esmaeel.catchathief.data.models.BotMessagesResponse
import com.esmaeel.moviesapp.data.remote.NetworkService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class TelegramRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mService: NetworkService
) {

    fun getUsersWithToken(token: String) = flow {
        emit(Contract.onLoading(data = null))
        try {
            val response = mService.getUserIDs()

            if (response.isSuccessful) {
                val usernamesAndTokens = getUsernames(response.body()?.result, token)
                emit(Contract.onSuccess(FoundedUsers(usernamesAndTokens)))

            } else {
                emit(Contract.onError(null, ""))
            }

        } catch (e: Exception) {
            emit(Contract.onError(null, e.message ?: ""))
        }
    }

    private fun getUsernames(
        result: List<BotMessagesResponse.Result?>?,
        userToken: String
    ): ArrayList<FoundedUsers.UserDetails> {
        val usersList: ArrayList<FoundedUsers.UserDetails> = arrayListOf()
        result?.let {
            result.forEach { item ->
                // text == token && chat is private
                if (item?.message?.chat?.type == "private" && item.message.text == userToken) {
                    // put username + token in the map list

                    usersList.add(
                        FoundedUsers.UserDetails(
                            chat_id = item.message.chat.id,
                            username = "${item.message.from?.first_name} ${item.message.from?.last_name} "
                        )
                    )
                }
            }
        }
        return usersList
    }


    fun generateToken(): String {
        return UUID.randomUUID().toString()
    }

}

data class FoundedUsers(
    val users: List<UserDetails> = listOf()
) {
    data class UserDetails(val chat_id: Int? = 0, val username: String? = "")
}

