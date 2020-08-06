package com.esmaeel.catchathief.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.esmaeel.catchathief.HeyLAdapter
import com.esmaeel.catchathief.databinding.ActivitySwapperBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class swapperActivity : AppCompatActivity() {

    @Inject
    lateinit var heyAdapter: HeyLAdapter

    lateinit var binder: ActivitySwapperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivitySwapperBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.swapper.apply {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            adapter = heyAdapter
            isUserInputEnabled = true


            registerOnPageChangeCallback(object : OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.e("Selected_Page", position.toString())
                }

            })
        }


        heyAdapter.submitList(
            listOf(
                "Hey you.",
                "Yeah You!",
                "Jihan?!",
                "\uD83E\uDD14",
                "How Are You?",
                "I wanna tell you something",
                "Thank you!",
                "Not only for",
                "being here!",
                "But also for",
                "always being here\n \uD83D\uDC99♥️"

            )
        )


        binder.up.setOnClickListener {
            binder.swapper.setCurrentItem(0, true)
        }

        binder.down.setOnClickListener {
            binder.swapper.setCurrentItem(heyAdapter.itemCount - 1, true)
        }


    }
}