package ru.vladroid.projs.mems.utils.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.vladroid.projs.mems.R
import ru.vladroid.projs.mems.domain.Mem

class MemesListAdapter(private val onMemClickListener: OnMemClickListener) :
    RecyclerView.Adapter<MemesListAdapter.MemViewHolder>() {
    private var memes = emptyList<Mem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.mem_item_layout, parent, false)
        return MemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return memes.size
    }

    override fun onBindViewHolder(holder: MemViewHolder, position: Int) {
        holder.bind(memes[position], onMemClickListener)
    }

    fun setData(memes: List<Mem>) {
        this.memes = memes
        notifyDataSetChanged()
    }

    class MemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.findViewById<TextView>(R.id.mem_title)
        private val image = view.findViewById<ImageView>(R.id.mem_image)
        private val favButton = view.findViewById<ImageView>(R.id.mem_like)

        fun bind(mem: Mem, onMemClickListener: OnMemClickListener) {
            view.setOnClickListener {
                onMemClickListener.onMemClick(mem)
            }
            title.text = mem.title
            Glide.with(image)
                .load(mem.photoUrl)
                .placeholder(R.drawable.mem_placeholder)
                .into(image)

            favButton.setOnClickListener {
                favButton.isSelected = !favButton.isSelected
            }
        }
    }

    interface OnMemClickListener {
        fun onMemClick(mem: Mem)
    }
}