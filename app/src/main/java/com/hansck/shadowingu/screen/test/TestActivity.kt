package com.hansck.shadowingu.screen.test

import android.os.Bundle
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.customview.SpeechSimilarityListener
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.util.CalculationMatching
import com.hansck.shadowingu.util.DataManager
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity(), SpeechSimilarityListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		btnCalculate.setOnClickListener {
			val file1 = file1.text.toString()
			val file2 = file2.text.toString()

			CalculationMatching.instance.calculateSimilarity(this,
					DataManager.instance.getWordByAudioName(file1).idWord,
					resources.getIdentifier(file2, "raw", packageName),
					0, this)

//			CalculationMatching.instance.calculateSimilarity(this,
//					0,
//					resources.getIdentifier(file1, "raw", packageName),
//					resources.getIdentifier(file2, "raw", packageName),
//					this)
		}
	}

	override fun onSimilarityMatching(isSimilar: Boolean) {

	}
}
