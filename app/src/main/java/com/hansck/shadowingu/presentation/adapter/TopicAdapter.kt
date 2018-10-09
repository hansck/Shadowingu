package com.hansck.shadowingu.presentation.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Topic
import com.hansck.shadowingu.presentation.customview.OnStageSelected
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.DataManager
import kotlinx.android.synthetic.main.item_topic.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class TopicAdapter(private val items: ArrayList<Topic>, private val isLearnStage: Boolean, private val listener: OnStageSelected)
	: RecyclerView.Adapter<TopicAdapter.ViewHolder>() {

	private fun ViewGroup.inflate(layoutRes: Int): View {
		return LayoutInflater.from(context).inflate(layoutRes, this, false)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicAdapter.ViewHolder {
		val inflatedView = parent.inflate(R.layout.item_topic)
		return ViewHolder(inflatedView, isLearnStage, listener)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

	override fun getItemCount() = items.size

	class ViewHolder(itemView: View, private val isLearnStage: Boolean, private val listener: OnStageSelected) : RecyclerView.ViewHolder(itemView) {

		@SuppressLint("SimpleDateFormat")
		var formatter: DateFormat = SimpleDateFormat(Constants.Time.TIME_SHORT)

		fun bind(topic: Topic) = with(itemView) {
			idTopic.text = topic.idTopic.toString()
			val time = Date(topic.fastestTime * 1000L)
			if (!isLearnStage) fastestTime.text = resources.getString(R.string.fastest_time, formatter.format(time))
			if ((!isLearnStage && (topic.idTopic == Constants.General.FIRST_LEVEL || topic.idTopic <= DataManager.instance.getUnclearLevel()))
					|| (isLearnStage && topic.cleared)) {
				Common.instance.setImageByName(context, topic.unlockedImage, image)
				image.setOnClickListener { listener.onStageSelected(topic) }
			} else {
				Common.instance.setImageByName(context, topic.lockedImage, image)
			}
		}
	}
}