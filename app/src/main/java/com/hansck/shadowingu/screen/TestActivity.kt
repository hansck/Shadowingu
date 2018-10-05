package com.hansck.shadowingu.screen

import android.os.Bundle
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.customview.SpeechSimilarityListener
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.SimilarityMatching
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity(), SpeechSimilarityListener {

	var index = 0
	var name1: String = ""
	var name2: String = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		btnCalculate.setOnClickListener {
			name1 = file1.text.toString()
			name2 = file2.text.toString()
			index = 0
//			checkSimilarity()
			SimilarityMatching.instance.calculateSimilarity(this,
					resources.getIdentifier(file1.text.toString(), "raw", packageName),
					null,
					resources.getIdentifier(file2.text.toString(), "raw", packageName),
					this, 1)
		}
	}

	override fun onSimilarityCalculated(distance: Double) {
//		Log.e("SIMILARITY", distance.toString())
//		Log.e("END", "------------------------------------------------------------------------------------------")
		println(distance.toString())
		index++
//		checkSimilarity()
	}

	fun checkSimilarity() {
		if (index < 70) {
			SimilarityMatching.instance.calculateSimilarity(this,
					resources.getIdentifier(name1, "raw", packageName),
					null,
					resources.getIdentifier(DataManager.instance.words[index].template, "raw", packageName),
					this, 1)
		}
	}
}
