package com.esmaeel.catchathief.ui.TelegramLink

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.esmaeel.catchathief.R
import com.esmaeel.catchathief.ServiceLogic.getConnectedUser
import com.esmaeel.catchathief.ServiceLogic.isLinkedToTelegram
import com.esmaeel.catchathief.Utils.Status
import com.esmaeel.catchathief.databinding.ActivityTelegramLinkBinding
import com.esmaeel.getlearn.Utils.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TelegramLinkActivity : AppCompatActivity() {

    @Inject
    lateinit var repo: TelegramRepository
    private lateinit var binder: ActivityTelegramLinkBinding
    private var check_user_now = false
    private var current_token = ""
    private var isLinkedAlready = false

    val viewModel: TelegramViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityTelegramLinkBinding.inflate(layoutInflater)
        setContentView(binder.root)

        isLinkedAlready = isLinkedToTelegram(context = applicationContext)

        if (isLinkedAlready) {
            showLinkedView()
        }

        // generate token
        repo.generateToken().also {
            binder.tokenTextview.text = it
            current_token = it
        }


        // open telegram with the token copied
        combineClickListeners(binder.tokenTextview, binder.copyAndOpenTelegramTextview) {
            binder.tokenTextview.text.toString()?.let {
                if (!openTelegram()) {
                    showSnackMessage(getString(R.string.install_telegram), binder.root)
                } else {
                    copyThis(it)
                    check_user_now = true
                }
            }
        }


        viewModel.users.observe(this, Observer { contract ->
            when (contract.status) {
                Status.LOADING -> {
                    binder.usernameTextview.text = "Loading ...."
                }
                Status.SUCCESS -> {
                    updateUI(contract.data)
                }
                Status.ERROR -> {
                    showSnackMessage(contract.message, binder.root)
                }
            }

        })

    }

    private fun showLinkedView() {
        binder.connectedLayout.Visible()
        binder.connectedWithUserText.text = getConnectedUser(applicationContext)
        binder.changeAccountButton.setOnClickListener {
            binder.connectedLayout.Gone()
        }
    }

    override fun onStart() {
        super.onStart()
        if (check_user_now) {
            if (current_token.isNotEmpty())
                viewModel.getUsers(current_token)
        }
    }

    private fun updateUI(foundedUsers: FoundedUsers?) {
        binder.usernameTextview.Visible()
        binder.confirmButton.Visible()

        binder.usernameTextview.text = "${foundedUsers?.users!![0].username}"

        binder.confirmButton.setOnClickListener {
            val userToken = foundedUsers.users[0].chat_id!!
            val userName = foundedUsers.users[0].username!!
            if (userToken != 0 && userName.isNotEmpty()) {
                saveUserIDWithName(userToken, userName)
                showSnackMessage(
                    "Linked Successfully to ${userName}'s Telegram Account",
                    binder.root
                )

                doIn(2500) {
                    onBackPressed()
                }
            }
        }

    }
}