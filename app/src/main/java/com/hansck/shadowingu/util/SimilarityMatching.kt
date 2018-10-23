package com.hansck.shadowingu.util

import android.content.Context
import android.util.Log
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.io.TarsosDSPAudioFormat
import be.tarsos.dsp.io.UniversalAudioInputStream
import be.tarsos.dsp.mfcc.MFCC
import com.hansck.shadowingu.presentation.customview.SpeechSimilarityListener
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Hans CK on 04-Jun-18.
 */
class CalculationMatching {

	private lateinit var similarityListener: SpeechSimilarityListener
	private var feature = Feature()
	private var index: Int = 0
	private var sum: Double = 0.0
	private var compareDistance: Double = 0.0
	private var idWord: Int = 0
	private lateinit var ctx: Context
	private var referenceAudioId: Int = 0
	private var wordsCount: Int = 0
	private var distances: ArrayList<Double> = ArrayList()

	// MFCC Parameters
	val bufferOverlap = 0
	val samplesPerFrame = 256
	val sampleRate = 44100
	val amountOfCepstrumCoef = 13
	val amountOfMelFilters = 26
	val lowerFilterFreq = 300.0f
	val upperFilterFreq = sampleRate / 2.0f

	companion object {
		val instance = CalculationMatching()
	}

	fun calculateSimilarity(ctx: Context, idWord: Int, testRecording: File?, listener: SpeechSimilarityListener) {
		this.ctx = ctx
		this.idWord = idWord
		this.similarityListener = listener
		feature = Feature()
		distances = ArrayList()
		index = 0; sum = 0.0; compareDistance = 0.0; wordsCount = 0
		val inStream = FileInputStream(testRecording)
		calculateMFCC(inStream, 1)
	}

	fun calculateSimilarity(ctx: Context, idWord: Int, testAudioId: Int, referenceAudioId: Int, listener: SpeechSimilarityListener) {
		this.ctx = ctx
		this.idWord = idWord
		this.similarityListener = listener
		this.referenceAudioId = referenceAudioId
		feature = Feature()
		distances = ArrayList()
		index = 0; sum = 0.0; compareDistance = 0.0; wordsCount = 0
		val inStream = ctx.resources.openRawResource(testAudioId)
		calculateMFCC(inStream, 1)
	}

	@Throws(FileNotFoundException::class)
	fun calculateMFCC(inStream: InputStream, fileIdx: Int) {

		val temp = ArrayList<FloatArray>()
		val dispatcher = AudioDispatcher(UniversalAudioInputStream(inStream, TarsosDSPAudioFormat(sampleRate.toFloat(), samplesPerFrame, 1, true, false)), samplesPerFrame, bufferOverlap)
		val mfcc = MFCC(samplesPerFrame, sampleRate.toFloat(), amountOfCepstrumCoef, amountOfMelFilters, lowerFilterFreq, upperFilterFreq)

		dispatcher.addAudioProcessor(mfcc)
		dispatcher.addAudioProcessor(object : AudioProcessor {
			override fun processingFinished() {

				val tempFeatures = Array(temp.size) { DoubleArray(amountOfCepstrumCoef - 1) }

				for (i in temp.indices) {
					for (j in 0 until amountOfCepstrumCoef - 1) {
						tempFeatures[i][j] = temp[i][j].toDouble()
					}
				}

				feature.setFeatures(fileIdx, tempFeatures)

				if (fileIdx == 1) {
					calculateMFCCReferences()
				} else {
					calculateDTW()
				}
			}

			override fun process(audioEvent: AudioEvent): Boolean {
				mfcc.process(audioEvent)
				var mfccOutput = mfcc.mfcc
				mfccOutput = Arrays.copyOfRange(mfccOutput, 1, mfccOutput.size)
				temp.add(mfccOutput)
				return true
			}
		})
		dispatcher.run()
	}

	private fun calculateDTW() {
		//		double[] variance = new double[features1[0].length];
		//		for (int i = 0; i < variance.length; i++)
		//			variance[i] = 1;

		val dtw = DynamicTimeWrapping(feature.features1, feature.features2)
		val distance = dtw.calDistance()
		calculateResult(distance)
	}

	private fun calculateResult(distance: Double) {
		Log.e("SIMILARITY", distance.toString())
		if (index == idWord) {
			compareDistance = if (distance < 100) {
				distance
			} else {
				100.0
			}
		}
		if (distance < 100) {
			sum += distance
			wordsCount++
			distances.add(distance)
		} else {
			distances.add(0.0)
			Log.e("ERROR DATA", "${DataManager.instance.words[index]}")
		}
//		Log.e("SUM", "$wordsCount $sum")
		index++
		if (index < Constants.General.TOTAL_WORDS) {
			calculateMFCCReferences()
		} else {
			val avgDistance = sum / wordsCount
			Log.e("COMPARE", "$compareDistance $sum $wordsCount $avgDistance")

			var isSimilar = compareDistance <= avgDistance
//			if (isSimilar) {
//				var moreSimilarWords = 0
//				for (x in distances) {
//					if (x <= compareDistance) {
//						moreSimilarWords++
//					}
//				}
//				Log.e("WORDS", "$moreSimilarWords")
//				if (moreSimilarWords > 25) isSimilar = false
//			}
			similarityListener.onSimilarityMatching(isSimilar)
		}
	}

	private fun calculateMFCCReferences() {
		val inStream = if (referenceAudioId == 0) {
			val referenceId = ctx.resources.getIdentifier(DataManager.instance.words[index].reference, "raw", ctx.packageName)
			ctx.resources.openRawResource(referenceId)
		} else {
			ctx.resources.openRawResource(referenceAudioId)
		}
		calculateMFCC(inStream, 2)
	}
}

internal class Feature {

	var features1: Array<DoubleArray>? = null
	var features2: Array<DoubleArray>? = null

	fun setFeatures(iteration: Int, features: Array<DoubleArray>) {
		if (iteration == 1) {
			features1 = features
//			for (i in features1!!.indices) {
//				Log.e("Features $iteration", Arrays.toString(features1!![i]))
//			}
		} else {
			features2 = features
//			for (i in features2!!.indices) {
//				Log.e("Features $iteration", Arrays.toString(features2!![i]))
//			}
		}
	}
}
