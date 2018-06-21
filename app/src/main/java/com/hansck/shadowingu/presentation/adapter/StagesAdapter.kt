package com.hansck.shadowingu.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Stage
import com.hansck.shadowingu.presentation.customview.OnStageSelected
import kotlinx.android.synthetic.main.item_stage.view.*
import java.util.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class StagesAdapter(private val items: ArrayList<Stage>, private val listener: OnStageSelected)
    : RecyclerView.Adapter<StagesAdapter.ViewHolder>() {

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StagesAdapter.ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_stage)
        return ViewHolder(inflatedView, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, private val listener: OnStageSelected) : RecyclerView.ViewHolder(itemView) {

        fun bind(stage: Stage) = with(itemView) {
            idStage.text = stage.idStage.toString()
            image.setOnClickListener { listener.onStageSelected(stage) }
        }
    }
}