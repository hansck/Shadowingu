package com.hansck.shadowingu.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Avatar
import com.hansck.shadowingu.presentation.customview.OnAvatarSelected
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.PersistentManager
import kotlinx.android.synthetic.main.item_avatar.view.*
import java.util.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class AvatarsAdapter(private val items: ArrayList<Avatar>, private val listener: OnAvatarSelected)
    : RecyclerView.Adapter<AvatarsAdapter.ViewHolder>() {

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarsAdapter.ViewHolder {
        val inflatedView = parent.inflate(layoutRes = R.layout.item_avatar)
        return ViewHolder(inflatedView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, val listener: OnAvatarSelected) : RecyclerView.ViewHolder(itemView) {

        fun bind(avatar: Avatar) = with(itemView) {
            avatarName.text = avatar.name
            description.text = avatar.description
            price.text = resources.getString(R.string.price, avatar.price.toString())
            Common.instance.setImageByName(context, avatar.image, picture)
            if (avatar.unlock && PersistentManager.instance.getActiveAvatar() == avatar.idAvatar) {
                activeText.visibility = View.VISIBLE
                btnSetActive.visibility = View.GONE
                btnBuy.visibility = View.GONE
            } else if (avatar.unlock) {
                btnSetActive.visibility = View.VISIBLE
                btnSetActive.setOnClickListener { listener.onAvatarActivate(avatar.idAvatar) }
                activeText.visibility = View.GONE
                btnBuy.visibility = View.GONE
            } else {
                btnBuy.visibility = View.VISIBLE
                btnBuy.setOnClickListener { listener.onAvatarBought(avatar.idAvatar) }
                activeText.visibility = View.GONE
                btnSetActive.visibility = View.GONE
            }
        }
    }
}