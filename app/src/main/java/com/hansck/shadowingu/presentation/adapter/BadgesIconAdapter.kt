package com.hansck.shadowingu.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Badge
import com.hansck.shadowingu.presentation.customview.OnBadgeSelected
import com.hansck.shadowingu.util.Common
import kotlinx.android.synthetic.main.item_badge.view.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class BadgesIconAdapter(private val items: ArrayList<Badge>, private val listener: OnBadgeSelected)
    : RecyclerView.Adapter<BadgesIconAdapter.ViewHolder>() {

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgesIconAdapter.ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_badge_icon)
        return ViewHolder(inflatedView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, private val listener: OnBadgeSelected) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onBadgeSelected(adapterPosition)
        }

        fun bind(badge: Badge) = with(itemView) {
            if (badge.unlock) {
                Common.instance.setImageByName(context, badge.unlockedImage, picture)
            } else {
                Common.instance.setImageByName(context, badge.lockedImage, picture)
            }
        }
    }
}