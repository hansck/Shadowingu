package com.hansck.shadowingu.screen.learn

import android.content.Context
import com.hansck.shadowingu.model.Stage
import com.hansck.shadowingu.presentation.adapter.SectionListAdapter
import com.hansck.shadowingu.util.Manager

/**
 * Created by Hans CK on 07-Jun-18.
 */
class LearnViewModel(var context: Context?) {

    val categories = ArrayList<SectionListAdapter.Section>()
    var stages: ArrayList<Stage> = ArrayList()

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