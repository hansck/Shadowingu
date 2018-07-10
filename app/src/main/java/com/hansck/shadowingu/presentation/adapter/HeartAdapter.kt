package com.hansck.shadowingu.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Heart
import com.hansck.shadowingu.util.Common
import kotlinx.android.synthetic.main.item_badge.view.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class HeartAdapter(private val items: ArrayList<Heart>)
    : RecyclerView.Adapter<HeartAdapter.ViewHolder>() {

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeartAdapter.ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_heart)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(heart: Heart) = with(itemView) {
            if (heart.status) {
                Common.instance.setImageByName(context, heart.fullImage, picture)
            } else {
                Common.instance.setImageByName(context, heart.emptyImage, picture)
            }
        }
    }
}