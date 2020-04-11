package ru.vladroid.projs.mems.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.vladroid.projs.mems.R
import java.util.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemeWithoutBar)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val authIntent = Intent(this@SplashActivity, AuthActivity::class.java)
                startActivity(authIntent)
            }
        }, 300)
    }
}
