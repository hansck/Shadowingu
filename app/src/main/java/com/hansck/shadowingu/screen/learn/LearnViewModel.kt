package com.hansck.shadowingu.screen.learn

import android.content.Context
import com.hansck.shadowingu.model.Topic
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.util.DataManager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LearnViewModel(var context: Context?) {

    val categories = ArrayList<SectionListAdapter.Section>()
    var topics: ArrayList<Topic> = ArrayList()

    fun setStages() {
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

    fun getProgressPercentage(): Int {
        val clearedStages = topics.filter { it.cleared }.size
        return (clearedStages * 100) / topics.size
    }
}