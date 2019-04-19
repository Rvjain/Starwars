package com.interview.philo.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.interview.philo.R
import com.interview.philo.view.item.SearchItem
import com.interview.philo.view.viewholder.SearchVH

class MainAdapter(private val clickListener: SearchVH.ItemClickListener) : RecyclerView.Adapter<SearchVH>() {

    var items = mutableListOf<SearchItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItems(list: List<SearchItem>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SearchVH {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.search_item_layout, viewGroup, false)
        return SearchVH(view, clickListener)
    }

    override fun onBindViewHolder(vh: SearchVH, i: Int) {
        vh.bindItem(items[i])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onViewRecycled(holder: SearchVH) {
        super.onViewRecycled(holder)
    }
}