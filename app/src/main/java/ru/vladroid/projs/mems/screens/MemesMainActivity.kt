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
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.domain.Mem
import ru.vladroid.projs.mems.fragments.MemesListFragment
import ru.vladroid.projs.mems.fragments.TestFragment
import ru.vladroid.projs.mems.presenters.MemesMainPresenterImpl
import ru.vladroid.projs.mems.utils.showSnackbar
import ru.vladroid.projs.mems.views.MemesMainView

class MemesMainActivity : AppCompatActivity(), MemesMainView {
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentContainer: FrameLayout
    private lateinit var initialLoadingView: FrameLayout
    private lateinit var errorMessage: TextView
    private val memesMainPresenter = MemesMainPresenterImpl()
    private val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memes_main)

        initViews()
        createFragments()
        initPresenter()
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
        constraintLayout.showSnackbar(R.string.memes_error_snackbar_text)
    }

    override fun setMemesListData(memes: List<Mem>) {
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

    private fun initViews() {
        constraintLayout = findViewById(R.id.constraint_layout)
        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        fragmentContainer = findViewById(R.id.fragment_container)
        initialLoadingView = findViewById(R.id.overlay_view)
        errorMessage = findViewById(R.id.memes_error_message)
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
                        .replace(R.id.fragment_container, fragments[0], MEMES_LIST_FRAGMENT_TAG)
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

    private fun initPresenter() {
        memesMainPresenter.attachView(this)
        memesMainPresenter.onFirstMemesLoading()
    }

    private fun hideErrorMessage() {
        errorMessage.isVisible = false
    }

    companion object {
        private val MEMES_LIST_FRAGMENT_TAG = "memes_list_fragment"
    }
}
