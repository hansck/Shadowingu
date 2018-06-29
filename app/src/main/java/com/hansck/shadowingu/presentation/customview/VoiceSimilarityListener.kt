package com.hansck.shadowingu.presentation.customview

/**
 * Created by Hans CK on 26-Jun-18.
 */
interface VoiceSimilarityListener {

    abstract fun onMFCCCalculated(distance: Double)
}