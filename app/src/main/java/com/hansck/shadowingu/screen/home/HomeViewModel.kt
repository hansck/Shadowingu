package com.hansck.shadowingu.screen.home

import android.content.Context
import com.hansck.shadowingu.util.Manager
import com.hansck.shadowingu.model.Badge
import com.hansck.shadowingu.model.Stage
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter

/**
 * Created by Hans CK on 07-Jun-18.
 */
class HomeViewModel(var context: Context?) {

    val categories = ArrayList<SectionListAdapter.Section>()
    var stages: ArrayList<Stage> = ArrayList()
    var badges: ArrayList<Badge> = ArrayList()

    fun setBadges() {
        badges = Manager.instance.badges
    }

    fun setStages() {
        stages = Manager.instance.stages

        categories.clear()
        val titles: ArrayList<String> = ArrayList()
        for (i in stages.indices) {
            if (stages[i].category !in titles) {
                titles.add(stages[i].category)
                categories.add(SectionListAdapter.Section(i, stages[i].category))
            }
        }
    }
}