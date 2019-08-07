package com.volodymyr.shykun.androidfinaltest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.volodymyr.shykun.androidfinaltest.R
import com.volodymyr.shykun.androidfinaltest.entity.SearchUrl
import com.volodymyr.shykun.androidfinaltest.entity.Status
import kotlinx.android.synthetic.main.item_search_result.view.*

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {


    val items = arrayListOf<SearchUrl>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchUrl = items[position]
        holder.bind(searchUrl)
    }

    fun updateItems(newItems: List<SearchUrl>) {
        items.clear()
        items.addAll(newItems)
    }
}

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(searchUrl: SearchUrl) {
        itemView.apply {
            searchUrlText.text = searchUrl.url
            searchUrlStatus.text = when (searchUrl.status) {
                is Status.Loading -> "Loading"
                is Status.Error -> "Error"
                is Status.Stopped -> "Stopped"
                is Status.Complete.Found -> "Found"
                is Status.Complete.NotFound -> "Not Found"
            }
        }
    }
}

class SearchUrlDiffUtilCallback(
    private val oldList: List<SearchUrl>,
    private val newList: List<SearchUrl>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem == newItem
    }
}