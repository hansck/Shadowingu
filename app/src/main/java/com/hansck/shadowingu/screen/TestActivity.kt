package com.hansck.shadowingu.screen

import android.os.Bundle
import android.util.Log
import com.hansck.shadowingu.R
import com.hansck.shadowingu.presentation.customview.VoiceSimilarityListener
import com.hansck.shadowingu.screen.base.BaseActivity
import com.hansck.shadowingu.util.DataManager
import com.hansck.shadowingu.util.SimilarityMatching
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity(), VoiceSimilarityListener {

    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        btnCalculate.setOnClickListener {
            SimilarityMatching.getInstance().calculateSimilarity2(this,
                    resources.getIdentifier(file1.text.toString(), "raw", packageName),
                    resources.getIdentifier(file2.text.toString(), "raw", packageName),
                    this, 1, sampleRate.text.toString().toInt())
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
            SimilarityMatching.getInstance().calculateSimilarity2(this,
                    resources.getIdentifier("anata", "raw", packageName),
                    resources.getIdentifier(DataManager.instance.words[index].audio, "raw", packageName),
                    this, 1, 44100)
        }
    }
}
