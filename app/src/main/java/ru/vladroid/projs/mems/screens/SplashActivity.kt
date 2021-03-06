package ru.vladroid.projs.mems.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.utils.AppConstants
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val authIntent = Intent(this@SplashActivity, AuthActivity::class.java)
                startActivity(authIntent)
                finish()
            }
        }, AppConstants.SPLASH_SCREEN_SHOW_TIME)
    }
}
