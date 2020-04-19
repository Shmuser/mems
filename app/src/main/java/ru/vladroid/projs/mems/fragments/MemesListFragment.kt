package ru.vladroid.projs.mems.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.network.Mem
import ru.vladroid.projs.mems.views.MemesMainView

class MemesListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val adapter = MemesListAdapter()

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

    fun setMemes(memes: ArrayList<Mem>) {
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


class MemesListAdapter : RecyclerView.Adapter<MemesListAdapter.MemViewHolder>() {
    private var memes = ArrayList<Mem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mem_item_layout, parent, false)
        return MemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memes.size
    }

    override fun onBindViewHolder(holder: MemViewHolder, position: Int) {
        holder.bind(memes[position])
    }

    fun setData(memes: ArrayList<Mem>) {
        this.memes = memes
        notifyDataSetChanged()
    }

    class MemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.mem_title)
        private val image = view.findViewById<ImageView>(R.id.mem_image)
        private val favButton = view.findViewById<ImageView>(R.id.mem_like)

        fun bind(mem: Mem) {
            title.text = mem.title
            Glide.with(image).load(mem.photoUrl)
                .into(image)

            favButton.setOnClickListener {
                favButton.isSelected = !favButton.isSelected
            }
        }
    }
}