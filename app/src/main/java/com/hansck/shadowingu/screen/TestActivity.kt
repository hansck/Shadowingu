package com.hansck.shadowingu.screen

import android.os.Bundle
import android.util.Log
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.customview.SpeechSimilarityListener
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.SimilarityMatching
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity(), SpeechSimilarityListener {

	var index = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		btnCalculate.setOnClickListener {
			SimilarityMatching.instance.calculateSimilarity(this,
					resources.getIdentifier(file1.text.toString(), "raw", packageName),
					null,
					resources.getIdentifier(file2.text.toString(), "raw", packageName),
					this, 1)
		}
//        checkSimilarity()
	}

	override fun onSimilarityCalculated(distance: Double) {
		Log.e("SIMILARITY", distance.toString())
		Log.e("END", "------------------------------------------------------------------------------------------")
		index++
//        checkSimilarity()
	}

	fun checkSimilarity() {
		if (index < 50) {
			SimilarityMatching.instance.calculateSimilarity(this,
					resources.getIdentifier("a", "raw", packageName),
					null,
					resources.getIdentifier(DataManager.instance.words[index].audio, "raw", packageName),
					this, 1)
		}
	}
}
