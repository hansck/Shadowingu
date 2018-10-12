package com.hansck.shadowingu.util

import android.content.Context
import android.util.Log
import be.tarsos.dsp.AudioDispatcher
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.AudioProcessor
import be.tarsos.dsp.io.TarsosDSPAudioFormat
import be.tarsos.dsp.io.UniversalAudioInputStream
import be.tarsos.dsp.mfcc.MFCC
import com.hansck.shadowingu.presentation.customview.SpeechCalculationListener
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
class CalculationMatching : SpeechCalculationListener {

	private lateinit var calculationListener: SpeechCalculationListener
	private lateinit var similarityListener: SpeechSimilarityListener
	private var feature = Feature()
	private var index: Int = 0
	private var sum: Double = 0.0
	private var compareDistance: Double = 0.0
	private var idWord: Int = 0
	private lateinit var ctx: Context
	private var referenceAudioId: Int = 0

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
		calculationListener = this
		feature = Feature()
		index = 0
		sum = 0.0
		val inStream = FileInputStream(testRecording)
		calculateMFCC(inStream, 1)
	}

	fun calculateSimilarity(ctx: Context, idWord: Int, testAudioId: Int, referenceAudioId: Int, listener: SpeechCalculationListener) {
		this.ctx = ctx
		this.idWord = idWord
		this.calculationListener = listener
		this.referenceAudioId = referenceAudioId
		feature = Feature()
		index = 0
		sum = 0.0
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
		calculationListener.onSimilarityCalculated(distance)
	}

	override fun onSimilarityCalculated(distance: Double) {
		Log.e("SIMILARITY", distance.toString())
		if (index == idWord) compareDistance = distance
		sum += distance
		index++
		if (index < 70) {
			calculateMFCCReferences()
		} else {
			val avgDistance = sum / 70
			similarityListener.onSimilarityMatching(compareDistance <= avgDistance)
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
//			for (i in features1.indices) {
//				Log.e("Features $iteration", Arrays.toString(features1[i]))
//			}
		} else {
			features2 = features
//			for (i in features2.indices) {
//				Log.e("Features $iteration", Arrays.toString(features2[i]))
//			}
		}
	}
}
