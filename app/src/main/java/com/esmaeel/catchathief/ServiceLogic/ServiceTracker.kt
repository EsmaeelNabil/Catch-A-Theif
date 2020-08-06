package com.esmaeel.catchathief.ServiceLogic

import android.content.Context
import android.content.SharedPreferences
import com.esmaeel.catchathief.Utils.Constants
import com.esmaeel.catchathief.Utils.Constants.key
import com.esmaeel.catchathief.Utils.Constants.name
import com.esmaeel.catchathief.Utils.CoroutinesManager
import com.esmaeel.getlearn.Utils.log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import java.text.SimpleDateFormat
import java.util.*

enum class ServiceState {
    STARTED,
    STOPPED,
}


fun setServiceState(context: Context, state: ServiceState) {
    val sharedPrefs =
        getPreferences(context)
    sharedPrefs.edit().let {
        it.putString(key, state.name)
        it.apply()
    }
}

fun getServiceState(context: Context): ServiceState {
    val sharedPrefs =
        getPreferences(context)
    val value = sharedPrefs.getString(key, ServiceState.STOPPED.name)
    return ServiceState.valueOf(value!!)
}

fun currentDateTime(): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Date())
}

fun getUserID(context: Context): Int? {
    return getPreferences(context).getInt(Constants.USER_ID, 0)
}

fun getConnectedUser(context: Context): String? {
    return getPreferences(context).getString(Constants.USER_NAME, "")
}


fun isLinkedToTelegram(context: Context): Boolean {
    return getPreferences(context).getBoolean(Constants.LINKED, false)
}

fun getPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(name, 0)
}


fun sendToTelegram(userId: Int, message: String) {
    val json = """
                {
                    "chat_id": "$userId",
                    "text": "$message in : ${currentDateTime()}"
                }
            """
    CoroutinesManager.onIOThread {
        Fuel.post(Constants.SEND_URL)
            .jsonBody(json)
            .response { _, _, result ->

                val (bytes, error) = result
                if (bytes != null) {
                    log("[response bytes] ${String(bytes)}")
                } else {
                    log("[response error] ${error?.message}")
                }

            }
    }

}