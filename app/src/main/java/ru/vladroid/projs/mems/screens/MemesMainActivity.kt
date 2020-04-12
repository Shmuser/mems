package ru.vladroid.projs.mems.screens

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.fragments.TestFragment

class MemesMainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memes_main)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        frameLayout = findViewById(R.id.fragment_container)

        toMemesActivityTestImpl()
    }

    private fun toMemesActivityTestImpl() {
        val fragmentFirst = TestFragment()
        val argsFirst = Bundle()
        argsFirst.putString(TestFragment.textKey, "1st fragment")
        fragmentFirst.arguments = argsFirst
        val fragmentSecond = TestFragment()
        val argsSecond = Bundle()
        argsSecond.putString(TestFragment.textKey, "2nd fragment")
        fragmentSecond.arguments = argsSecond
        val fragmentThird = TestFragment()
        val argsThird = Bundle()
        argsThird.putString(TestFragment.textKey, "3rd fragment")
        fragmentThird.arguments = argsThird

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragmentFirst).commit()

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_news -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentFirst).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_add -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentSecond).commit()
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.action_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragmentThird).commit()
                    return@setOnNavigationItemSelectedListener true

                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}
