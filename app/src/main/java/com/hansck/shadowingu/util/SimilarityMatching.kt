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

/**
 * Created by Hans CK on 04-Jun-18.
 */
class SimilarityMatching {

	private val feature = Feature()
	private var listener: SpeechSimilarityListener? = null

	companion object {
		val instance = SimilarityMatching()
	}

	@Throws(FileNotFoundException::class)
	fun calculateSimilarity(context: Context, template: Int, recording: File?, testAudio: Int, listener: SpeechSimilarityListener, fileIdx: Int) {

		this.listener = listener
		val bufferOverlap = 0
		val samplesPerFrame = 256
		val sampleRate = 44100
		val amountOfCepstrumCoef = 13
		val amountOfMelFilters = 26
		val lowerFilterFreq = 300.0f
		val upperFilterFreq = sampleRate / 2.0f
		val dispatcher: AudioDispatcher
		val mfcc: MFCC
		val temp = ArrayList<FloatArray>()

		Log.e("FILE", "FILE $fileIdx")
		val inStream: InputStream = when {
			fileIdx == 1 -> context.resources.openRawResource(template)
			recording == null -> context.resources.openRawResource(testAudio)
			else -> FileInputStream(recording)
		}

		dispatcher = AudioDispatcher(UniversalAudioInputStream(inStream, TarsosDSPAudioFormat(sampleRate.toFloat(), samplesPerFrame, 1, true, false)), samplesPerFrame, bufferOverlap)
		mfcc = MFCC(samplesPerFrame, sampleRate.toFloat(), amountOfCepstrumCoef, amountOfMelFilters, lowerFilterFreq, upperFilterFreq)

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
					try {
						calculateSimilarity(context, template, recording, testAudio, listener, 2)
					} catch (e: FileNotFoundException) {
						e.printStackTrace()
					}
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

		val dtw = DynamicTimeWrapping2D(feature.features1, feature.features2)
		val distance = dtw.calDistance()
		listener!!.onSimilarityCalculated(distance)
	}
}

internal class Feature {

	lateinit var features1: Array<DoubleArray>
	lateinit var features2: Array<DoubleArray>

	fun setFeatures(iteration: Int, features: Array<DoubleArray>) {
		if (iteration == 1) {
			features1 = features
			for (i in features1.indices) {
				Log.e("Features $iteration", Arrays.toString(features1[i]))
			}
		} else {
			features2 = features
			for (i in features2.indices) {
				Log.e("Features $iteration", Arrays.toString(features2[i]))
			}
		}
	}
}
