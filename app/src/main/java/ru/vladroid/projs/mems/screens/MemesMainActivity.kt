package ru.vladroid.projs.mems.screens

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.fragments.MemesListFragment
import ru.vladroid.projs.mems.fragments.TestFragment
import ru.vladroid.projs.mems.network.Mem
import ru.vladroid.projs.mems.presenters.MemesMainPresenterImpl
import ru.vladroid.projs.mems.views.MemesMainView

class MemesMainActivity : AppCompatActivity(), MemesMainView {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var initialLoadingView: FrameLayout
    private lateinit var errorMessage: TextView
    private var snackbar: Snackbar? = null
    private val memesMainPresenter = MemesMainPresenterImpl()
    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memes_main)
        memesMainPresenter.attachView(this)
        constraintLayout = findViewById(R.id.constraint_layout)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        fragmentContainer = findViewById(R.id.fragment_container)
        initialLoadingView = findViewById(R.id.overlay_view)
        errorMessage = findViewById(R.id.memes_error_message)
        createFragments()

        memesMainPresenter.onFirstMemesLoading()
    }

    private fun createFragments() {
        fragments.add(MemesListFragment())
        val fragmentSecond = TestFragment()
        val argsSecond = Bundle()
        argsSecond.putString(TestFragment.textKey, "2nd fragment")
        fragmentSecond.arguments = argsSecond
        fragments.add(fragmentSecond)
        val fragmentThird = TestFragment()
        val argsThird = Bundle()
        argsThird.putString(TestFragment.textKey, "3rd fragment")
        fragmentThird.arguments = argsThird
        fragments.add(fragmentThird)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragments[0]).commit()

        configureBottomNavView()
    }

    private fun configureBottomNavView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_news -> {
                    hideErrorMessage()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragments[0], memesListFragmentTag)
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_add -> {
                    hideErrorMessage()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragments[1]).commit()
                    return@setOnNavigationItemSelectedListener true

                }
                R.id.action_profile -> {
                    hideErrorMessage()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragments[2]).commit()
                    return@setOnNavigationItemSelectedListener true

                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    override fun onDestroy() {
        memesMainPresenter.detachView()
        super.onDestroy()
    }

    override fun showInitialLoading() {
        initialLoadingView.isVisible = true
    }

    override fun hideInitialLoading() {
        initialLoadingView.isVisible = false
    }

    override fun onMemesLoadingError() {
        errorMessage.isVisible = true
        getErrorSnackbar().show()
    }

    override fun setMemesListData(memes: ArrayList<Mem>) {
        val memesListFragment = fragments[0] as MemesListFragment
        memesListFragment.setMemes(memes)
    }

    override fun onReloadMemesList() {
        memesMainPresenter.reloadMemesList()
    }

    override fun hideReloadMemesListProgress() {
        (fragments[0] as? MemesListFragment)?.closeRefreshProgress()
    }

    override fun onMemesReloadingError() {
        (fragments[0] as? MemesListFragment)?.hideMemes()
        onMemesLoadingError()
    }

    private fun hideErrorMessage() {
        errorMessage.isVisible = false
    }

    private fun getErrorSnackbar(): Snackbar {
        if (snackbar == null) {
            snackbar = Snackbar.make(
                constraintLayout,
                R.string.memes_error_snackbar_text,
                Snackbar.LENGTH_LONG
            )
                .setBackgroundTint(ContextCompat.getColor(this, R.color.colorError))
                .setTextColor(ContextCompat.getColor(this, R.color.colorText))
        }
        return snackbar!!
    }

    companion object {
        private val memesListFragmentTag = "memes_list_fragment"
    }
}
