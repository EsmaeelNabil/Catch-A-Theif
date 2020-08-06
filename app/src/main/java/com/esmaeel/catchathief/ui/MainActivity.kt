package com.esmaeel.catchathief.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esmaeel.catchathief.R
import com.esmaeel.catchathief.ServiceLogic.Actions
import com.esmaeel.catchathief.ServiceLogic.EndlessService
import com.esmaeel.catchathief.ServiceLogic.ServiceState
import com.esmaeel.catchathief.ServiceLogic.getServiceState
import com.esmaeel.catchathief.ui.TelegramLink.TelegramLinkActivity
import com.esmaeel.getlearn.Utils.log
import com.esmaeel.getlearn.Utils.open
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start?.setOnClickListener {
            actionOnService(Actions.START)
        }
        stop?.setOnClickListener {
            actionOnService(Actions.STOP)
        }

        telegramLinkImageButton?.setOnClickListener {
            open(thisActivity = TelegramLinkActivity::class.java)
        }
    }


    private fun actionOnService(action: Actions) {
        if (getServiceState(this) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(this, EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log("Starting the service in >=26 Mode")
                startForegroundService(it)
                return
            }
            log("Starting the service in < 26 Mode")
            startService(it)
        }
    }
}