package com.hansck.shadowingu.presentation.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Lesson
import com.hansck.shadowingu.presentation.customview.OnStageSelected
import com.hansck.shadowingu.util.Common
import com.hansck.shadowingu.util.Constants
import com.hansck.shadowingu.util.DataManager
import kotlinx.android.synthetic.main.item_lesson.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hans CK on 20-Jun-18.
 */
class LessonsAdapter(private val items: ArrayList<Lesson>, private val isLearnStage: Boolean, private val listener: OnStageSelected)
	: RecyclerView.Adapter<LessonsAdapter.ViewHolder>() {

	private fun ViewGroup.inflate(layoutRes: Int): View {
		return LayoutInflater.from(context).inflate(layoutRes, this, false)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsAdapter.ViewHolder {
		val inflatedView = parent.inflate(R.layout.item_lesson)
		return ViewHolder(inflatedView, isLearnStage, listener)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

	override fun getItemCount() = items.size

	class ViewHolder(itemView: View, private val isLearnStage: Boolean, private val listener: OnStageSelected) : RecyclerView.ViewHolder(itemView) {

		@SuppressLint("SimpleDateFormat")
		var formatter: DateFormat = SimpleDateFormat(Constants.Time.TIME_SHORT)

		fun bind(lesson: Lesson) = with(itemView) {
			idLesson.text = lesson.idLesson.toString()
			val time = Date(lesson.fastestTime * 1000L)
			if (!isLearnStage) fastestTime.text = formatter.format(time)
			if ((!isLearnStage && (lesson.idLesson == Constants.General.FIRST_LEVEL || lesson.idLesson <= DataManager.instance.getUnclearLevel()))
					|| (isLearnStage && lesson.cleared)) {
				Common.instance.setImageByName(context, lesson.unlockedImage, image)
				image.setOnClickListener { listener.onStageSelected(lesson) }
			} else {
				Common.instance.setImageByName(context, lesson.lockedImage, image)
			}
		}
	}
}