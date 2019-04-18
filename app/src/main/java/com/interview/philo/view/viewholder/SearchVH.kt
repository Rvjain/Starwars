package com.interview.philo.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.interview.philo.R
import com.interview.philo.view.item.SearchItem

class SearchVH(itemView: View, val clickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {
    private val displayName: TextView = itemView.findViewById(R.id.tv_display_name)

    init {
        itemView.setOnClickListener {
            if (itemView.tag != null) {
                val item = itemView.tag as SearchItem
                clickListener.onItemClick(item)
            }
        }
    }

    fun bindItem(item: SearchItem) {
        itemView.tag = item
        displayName.text = item.result.name
    }

    interface ItemClickListener {
        fun onItemClick(item: SearchItem)
    }
}