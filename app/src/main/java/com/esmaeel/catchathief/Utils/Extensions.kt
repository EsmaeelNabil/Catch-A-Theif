package com.esmaeel.getlearn.Utils

//import androidx.navigation.NavController

import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.esmaeel.catchathief.R
import com.esmaeel.catchathief.Utils.Constants
import com.esmaeel.catchathief.Utils.Constants.name
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.snackbar.Snackbar


//import org.greenrobot.eventbus.EventBus

/*
fun openDrawer() {
    EventBus.getDefault().post(toggleDrawer(open = true))
}

fun closeDrawer() {
    EventBus.getDefault().post(toggleDrawer(open = false))
}

fun FragmentActivity.showLoader() {
    EventBus.getDefault().post(toggleLoader(show = true))

}

fun FragmentActivity.hideLoader() {
    EventBus.getDefault().post(toggleLoader(show = false))
}


fun Fragment.showLoader() {
    EventBus.getDefault().post(toggleLoader(show = true))

}

fun Fragment.hideLoader() {
    EventBus.getDefault().post(toggleLoader(show = false))
}*/

/**
 *
 * @receiver FragmentActivity
 * @param view View?
 * @param navController NavController?
 * @param destination Int
 * @param pow Function0<Unit> this will be triggered after the navigation do it's job
 */
/*
fun FragmentActivity.bindNavigation(
    view: View?,
    navController: NavController?,
    destination: Int,
    pow: (Boolean) -> Unit
) {
    view?.let {
        it.setOnClickListener {
            navController?.let {
                if (navController.currentDestination!!.id != destination) {
                    navController.navigate(destination)
                    closeDrawer()
                    // if have farther things to do
                    pow?.let {
                        pow.invoke(true)
                    }
                } else {
                    // if have farther things to do
                    pow?.let {
                        pow.invoke(false)
                    }
                    closeDrawer()
                }

            }
        }
    }
}

fun FragmentActivity.navigateTo(
    navController: NavController,
    destination: Int
) {
    navController?.let {
        if (navController.currentDestination!!.id != destination) {
            navController.navigate(destination)
            closeDrawer()
        } else closeDrawer()
    }

}
*/


fun combineClickListeners(vararg views: View?, function: () -> Unit) {
    views.forEach {
        it?.setOnClickListener {
            function.invoke()
        }
    }
}

fun TextView.GoneIfEmpty() {
    print("FUCK" + this.text.toString())
    if (this.text.toString().isNullOrEmpty())
        this.Gone()
}

fun View.Gone() {
    this.visibility = View.GONE
}

fun View.Visible() {
    this.visibility = View.VISIBLE
}

fun View.Hide() {
    this.visibility = View.INVISIBLE
}

fun String.isJson(): Boolean {
    return this.startsWith("{") || this.startsWith("[")
}

fun String?.buildProfilePath(base: String?): String {
    print("$base$this")
    return "$base$this"
}

fun doSafely(success: () -> Unit, error: (message: String?) -> Unit) {
    try {
        success.invoke()
    } catch (e: Exception) {
        error(e.localizedMessage)
    }
}


fun FragmentActivity.showSnackMessage(message: String?, view: View) =
    message?.let {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .show()
    }

fun Context.showSnackMessage(message: String?, view: View) =
    message?.let {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .show()
    }


fun FragmentActivity.showSnackMessageWithAction(
    message: String?,
    view: View,
    actionButton: String? = "",
    function: () -> Unit?
) =
    message?.let {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(actionButton) {
                function.invoke()
            }
            .show()
    }


fun FragmentActivity.isNetworkAvailable(): Boolean {
    if (applicationContext == null) return false
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}

fun Fragment.showSnackMessage(message: String?, view: View?) =
    view?.let {
        message?.let {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .show()
        }
    }

fun Fragment.showSnackMessageWithAction(
    message: String?,
    view: View,
    actionButton: String? = "",
    function: () -> Unit?
) =
    message?.let {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction(actionButton) {
                function.invoke()
            }
            .show()
    }


fun Fragment.isNetworkAvailable(): Boolean {
    if (context == null) return false
    val connectivityManager =
        this.requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}

fun FragmentActivity.open(
    thisActivity: Class<*>?,
    finish: Boolean = false,
    finishAffinity: Boolean = false
) {

    startActivity(Intent(this, thisActivity))

    if (finishAffinity)
        this.finishAffinity()
    if (finish)
        this.finish()

}

fun Fragment.open(
    thisActivity: Class<*>?,
    finish: Boolean = false,
    finishAffinity: Boolean = false
) {
    activity?.let {
        startActivity(Intent(it, thisActivity))

        if (finishAffinity)
            it.finishAffinity()
        if (finish)
            it.finish()
    }
}

fun doIn(inTime: Long, givenFun: () -> Unit) {
    object : CountDownTimer(inTime, inTime) {
        override fun onFinish() {
            givenFun.invoke()
        }

        override fun onTick(p0: Long) {}
    }.start()
}

fun doThen(inTime: Long, givenFun: () -> Unit, LastFunctions: () -> Unit) {
    var done = false
    object : CountDownTimer(inTime, 50) {
        override fun onFinish() {
            LastFunctions.invoke()
        }

        override fun onTick(p0: Long) {
            if (!done) {
                givenFun.invoke()
                done = true
            }
        }
    }.start()
}

fun MaterialCardView.setCorners(
    topLeft: Float = 0f,
    bottomLeft: Float = 0f,
    bottomRight: Float = 0f,
    topRight: Float = 0f,
    all: Float = 0f
) {
    shapeAppearanceModel = if (all.toInt() == 0)
        shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, bottomLeft)
            .setBottomRightCorner(CornerFamily.ROUNDED, bottomRight)
            .setTopLeftCorner(CornerFamily.ROUNDED, topLeft)
            .setTopRightCorner(CornerFamily.ROUNDED, topRight)
            .build()
    else
        shapeAppearanceModel
            .toBuilder()
            .setAllCornerSizes(all)
            .build()


}

fun MaterialCardView.setBackgroundColorEX(colorId: Int) =
    setCardBackgroundColor(ContextCompat.getColorStateList(this.context, colorId))

fun Context.getColorEX(colorId: Int): Int = ContextCompat.getColor(this, colorId)
fun FragmentActivity.getColorEX(colorId: Int): Int = ContextCompat.getColor(this, colorId)
fun Fragment.getColorEX(colorId: Int): Int {
    context?.let {
        return ContextCompat.getColor(it, colorId)
    }
    return 0
}


fun Context.lifecycleOwner(): LifecycleOwner? {
    var curContext = this
    var maxDepth = 20
    while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
        curContext = (curContext as ContextWrapper).baseContext
    }
    return if (curContext is LifecycleOwner) {
        curContext
    } else {
        null
    }
}


fun log(message: String?) =
    message?.let {
        println("ESMA3EL : $it")
    }


fun FragmentActivity.isAppAvailable(appName: String?): Boolean {
    val pm = applicationContext.packageManager
    return try {
        pm.getPackageInfo(appName!!, PackageManager.GET_ACTIVITIES)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}


fun FragmentActivity.copyThis(text: String?) {
    text?.let {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("labelForTextCopied", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(
            applicationContext,
            "Text Copied Successfully Paste it, Send it, and Go back to confirm!",
            Toast.LENGTH_LONG
        ).show()
    }
}

fun FragmentActivity.openTelegram(): Boolean {
    if (isAppAvailable(Constants.TELEGRAM_PACKEDGE_NAME)) {
        val telegram = Intent(Intent.ACTION_VIEW)
        telegram.type = "text/plain"
        telegram.setData(Uri.parse("https://telegram.me/CatchAThief_Bot"));
        telegram.setPackage(Constants.TELEGRAM_PACKEDGE_NAME)
        startActivity(Intent.createChooser(telegram, getString(R.string.send_using)))
        return true
    }
    return false
}

fun FragmentActivity.saveUserID(userId: Int) {
    getSharedPreferences(name, Context.MODE_PRIVATE)
        .edit()
        .putInt(Constants.USER_ID, userId)
        .apply()
}

fun FragmentActivity.saveUserIDWithName(userId: Int, username: String) {
    getSharedPreferences(name, Context.MODE_PRIVATE)
        .edit()
        .putInt(Constants.USER_ID, userId)
        .putString(Constants.USER_NAME, username)
        .putBoolean(Constants.LINKED, true)
        .apply()
}


fun doSafely(givenFun: () -> Unit) {
    try {
        givenFun.invoke()
    } catch (e: Exception) {
        log(e.message)
    }
}