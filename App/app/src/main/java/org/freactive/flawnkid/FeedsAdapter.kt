package org.freactive.flawnkid

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.feed_container.view.*
import kotlinx.android.synthetic.main.view_feed.view.*

/**
 * Created by akshat on 18/3/18.
 */

class FeedsAdapter(val context: Context): RecyclerView.Adapter<FeedsAdapter.ViewHolder>() {

    var feeds: List<Feeds> = listOf()

    fun update(feeds: List<Feeds>) {
        this.feeds = feeds
        Log.d("test",feeds.size.toString())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.feed_container, parent, false))

    override fun getItemCount(): Int = feeds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeds[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(feed: Feeds) {
            itemView.title.text = feed.title
            itemView.description.text = feed.description
        }
    }
}