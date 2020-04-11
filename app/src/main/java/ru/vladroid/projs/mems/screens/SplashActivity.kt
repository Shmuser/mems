package ru.vladroid.projs.mems.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.vladroid.projs.mems.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppThemeWithoutBar)
        setContentView(R.layout.activity_splash)
    }
}
