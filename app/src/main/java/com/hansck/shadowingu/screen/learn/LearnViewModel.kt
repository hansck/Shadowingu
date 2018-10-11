package com.hansck.shadowingu.screen.learn

import android.content.Context
import com.hansck.shadowingu.model.Lesson
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LearnViewModel(var context: Context?) {

	val categories = ArrayList<SectionListAdapter.Section>()
	var lessons: ArrayList<Lesson> = ArrayList()

	fun setStages() {
		lessons = DataManager.instance.lessons

		categories.clear()
		val titles: ArrayList<String> = ArrayList()
		for (i in lessons.indices) {
			if (lessons[i].category !in titles) {
				titles.add(lessons[i].category)
				categories.add(SectionListAdapter.Section(i, lessons[i].category))
			}
		}
	}

	fun getProgressPercentage(): Int {
		val clearedStages = lessons.filter { it.cleared }.size
		return (clearedStages * 100) / lessons.size
	}
}