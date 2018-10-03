package com.hansck.shadowingu.screen.home

import android.content.Context
import com.hansck.shadowingu.model.Badge
import com.hansck.shadowingu.model.Title
import com.hansck.shadowingu.model.Topic
import com.hansck.shadowingu.model.User
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class HomeViewModel(var context: Context?) {

	val categories = ArrayList<SectionListAdapter.Section>()
	var topics: ArrayList<Topic> = ArrayList()
	var badges: ArrayList<Badge> = ArrayList()
	var titles: ArrayList<Title> = ArrayList()
	lateinit var user: User

	fun setData() {
		user = DataManager.instance.user
		titles = DataManager.instance.titles
	}

	fun setStagesAndBadges() {
		badges = DataManager.instance.badges
		topics = DataManager.instance.topics

		categories.clear()
		val titles: ArrayList<String> = ArrayList()
		for (i in topics.indices) {
			if (topics[i].category !in titles) {
				titles.add(topics[i].category)
				categories.add(SectionListAdapter.Section(i, topics[i].category))
			}
		}
	}

	fun getActiveTitle(): Title {
		lateinit var activeTitle: Title
		for (title in titles) {
			if (title.minLevel <= user.level) {
				activeTitle = title
			}
		}
		return activeTitle
	}
}