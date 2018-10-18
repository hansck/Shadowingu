package com.hansck.shadowingu.presentation.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hansck.shadowingu.R
import com.hansck.shadowingu.model.Lesson
import com.hansck.shadowingu.presentation.customview.OnLessonSelected
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
class LessonsAdapter(private val items: ArrayList<Lesson>, private val isLearnStage: Boolean, private val listener: OnLessonSelected)
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

	class ViewHolder(itemView: View, private val isLearnStage: Boolean, private val listener: OnLessonSelected) : RecyclerView.ViewHolder(itemView) {

		@SuppressLint("SimpleDateFormat")
		var formatter: DateFormat = SimpleDateFormat(Constants.Time.TIME_SHORT)

		fun bind(lesson: Lesson) = with(itemView) {
			topic.text = lesson.topic
			if (!isLearnStage && lesson.cleared) {
				val time = Date(lesson.fastestTime * 1000L)
				score.text = resources.getString(R.string.score_point, lesson.high_score.toString(), Constants.General.WORDS_PER_LEVEL.toString())
				score.visibility = View.VISIBLE
				fastestTime.text = formatter.format(time)
				fastestTime.visibility = View.VISIBLE
			}
			if ((!isLearnStage && (lesson.idLesson == Constants.General.FIRST_LEVEL || lesson.idLesson <= DataManager.instance.getUnclearLevel()))
					|| (isLearnStage && lesson.cleared)) {
				Common.instance.setImageByName(context, lesson.unlockedImage, imgBackground)
				Common.instance.setImageByName(context, lesson.unlockedIcon, icon)
				imgBackground.setOnClickListener { listener.onStageSelected(lesson) }
				icon.setOnClickListener { listener.onStageSelected(lesson) }
			} else {
				Common.instance.setImageByName(context, lesson.lockedImage, imgBackground)
				Common.instance.setImageByName(context, lesson.lockedIcon, icon)
			}
		}
	}
}