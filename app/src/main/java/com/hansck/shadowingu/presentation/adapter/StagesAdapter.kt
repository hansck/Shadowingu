package com.hansck.shadowingu.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Stage
import com.hansck.shadowingu.presentation.customview.OnStageSelected
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.DataManager
import kotlinx.android.synthetic.main.item_stage.view.*
import java.util.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class StagesAdapter(private val items: ArrayList<Stage>, private val isLearnStage: Boolean, private val listener: OnStageSelected)
    : RecyclerView.Adapter<StagesAdapter.ViewHolder>() {

    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StagesAdapter.ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_stage)
        return ViewHolder(inflatedView, isLearnStage, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View, private val isLearnStage: Boolean, private val listener: OnStageSelected) : RecyclerView.ViewHolder(itemView) {

        fun bind(stage: Stage) = with(itemView) {
            idStage.text = stage.idStage.toString()
            if ((!isLearnStage && (stage.idStage == Constants.General.FIRST_LEVEL || stage.idStage <= DataManager.instance.getUnclearLevel()))
                    || (isLearnStage && stage.cleared)) {
                Common.instance.setImageByName(context, stage.unlockedImage, image)
                image.setOnClickListener { listener.onStageSelected(stage) }
            } else {
                Common.instance.setImageByName(context, stage.lockedImage, image)
            }
        }
    }
}