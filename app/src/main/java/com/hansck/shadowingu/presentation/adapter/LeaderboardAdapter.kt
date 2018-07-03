package com.hansck.shadowingu.presentation.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.LeaderboardUser
import com.hansck.shadowingu.util.Common
import kotlinx.android.synthetic.main.item_user.view.*
import java.util.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class LeaderboardAdapter(private val items: ArrayList<LeaderboardUser>)
    : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardAdapter.ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_user)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: LeaderboardUser) = with(itemView) {
            rank.text = user.rank.toString()
            username.text = user.name
            title.text = user.level.toString()
            Common.instance.setImageByName(context, user.image, picture)
            for (badge in user.badges) {
                addBadges(context, badgesContainer, badge.unlockedImage)
            }
        }

        private fun addBadges(context: Context, container: LinearLayout, imageName: String) {
            val menuView = LayoutInflater.from(context).inflate(R.layout.item_badge_icon_small, container, false)
            Common.instance.setImageByName(context, imageName, menuView.findViewById(R.id.picture))
            container.addView(menuView)
        }
    }
}