package ru.vladroid.projs.mems.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.domain.Mem
import ru.vladroid.projs.mems.screens.MemDetailsActivity
import ru.vladroid.projs.mems.utils.adapters.MemesListAdapter
import ru.vladroid.projs.mems.views.MemesMainView


class MemesListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val adapter = MemesListAdapter(object : MemesListAdapter.OnMemClickListener {
        override fun onMemClick(mem: Mem) {
            val intent = Intent(activity, MemDetailsActivity::class.java)
            intent.putExtra(MemDetailsActivity.MEM_KEY, mem)
            startActivity(intent)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_memes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        configureSwipeRefreshLayout()
        configureRecyclerView()
    }

    fun setMemes(memes: List<Mem>) {
        adapter.setData(memes)
    }

    fun closeRefreshProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    fun hideMemes() {
        setMemes(arrayListOf())
    }

    private fun initViews(view: View) {
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        recyclerView = view.findViewById(R.id.recycler_view)
    }

    private fun configureSwipeRefreshLayout() {
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent)
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        swipeRefreshLayout.setOnRefreshListener {
            (activity as MemesMainView).onReloadMemesList()
        }
    }

    private fun configureRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}