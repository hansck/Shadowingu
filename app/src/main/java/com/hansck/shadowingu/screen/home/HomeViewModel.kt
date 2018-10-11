package com.hansck.shadowingu.screen.home

import android.content.Context
import com.hansck.shadowingu.model.*
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class HomeViewModel(var context: Context?) {

	val categories = ArrayList<SectionListAdapter.Section>()
	var lessons: ArrayList<Lesson> = ArrayList()
	var badges: ArrayList<Badge> = ArrayList()
	var levels: ArrayList<Level> = ArrayList()
	lateinit var title: Title
	lateinit var user: User

	fun setData() {
		user = DataManager.instance.user
		levels = DataManager.instance.levels
		title = DataManager.instance.getActiveTitle(user.level)
	}

	fun setStagesAndBadges() {
		badges = DataManager.instance.badges
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

	fun getLevelAndTitle(): String {
		return "Level ${user.level} ~${title.name}~"
	}

	fun getExp(): String {
		return "EXP : ${user.exp} / ${levels[user.level - 1].exp}"
	}
}