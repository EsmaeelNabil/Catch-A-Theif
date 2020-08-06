package com.esmaeel.catchathief.ServiceLogic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.Ringtone
import android.os.BatteryManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.esmaeel.getlearn.Utils.log


class PowerListenerBroadcast : BroadcastReceiver() {

    var am: AudioManager? = null
    private var mVibrator: Vibrator? = null
    var mp: MediaPlayer? = null
    private val r: Ringtone? = null

    val CONNECTED: String = "android.intent.action.ACTION_POWER_CONNECTED"
    val DISCONNECTED: String = "android.intent.action.ACTION_POWER_DISCONNECTED"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == CONNECTED) {
            mVibrator?.cancel()
            log("charging".toUpperCase())
//            updateBatteryStats(context)
            updateListeners(
                context, """ is Charging """.trimIndent()
            )

        } else if (intent.action == DISCONNECTED) {
            vibrate(context)
            log("not charging".toUpperCase())
//            updateBatteryStats(context)
            updateListeners(
                context, """ is Not Charging """.trimIndent()
            )
        }

    }

    private fun updateBatteryStats(context: Context) {

        // Register for the battery changed event
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, filter)

        batteryStatus?.let { batteryStatusInner ->
            val status = batteryStatusInner.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
            val isCharging =
                status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
            val isFull = status == BatteryManager.BATTERY_STATUS_FULL
            // How are we charging?
            val chargePlug = batteryStatusInner.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
            val usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
            val acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC
            updateListeners(
                context, """
                is Charging : $isCharging
                is isFull   :  $isFull
                ${if (isCharging && usbCharge) "Connected to USB : Yes" else ""} 
                ${if (isCharging && acCharge) "Connected to POWER : Yes" else ""}
            """.trimIndent()
            )
        }


    }

    private fun updateListeners(context: Context, message: String) {
        if (isLinkedToTelegram(context))
            getUserID(context = context)
                ?.let { userID ->
                    sendToTelegram(
                        userId = userID,
                        message = message
                    )
                }
    }


/*
    private fun pingFakeServer() {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmmZ")
        val gmtTime = df.format(Date())

        val deviceId = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        val json =
            """
                {
                    "deviceId": "$deviceId",
                    "createdAt": "$gmtTime"
                }
            """
        try {

        } catch (e: Exception) {
            log("Error making the request: ${e.message}")
        }
    }
*/

    private fun vibrate(context: Context) {
        val mVibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mVibrator.vibrate(
                VibrationEffect.createOneShot(
                    2000,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            mVibrator.vibrate(2000)
        }
    }


}