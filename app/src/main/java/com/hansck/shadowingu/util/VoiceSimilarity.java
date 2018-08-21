package com.hansck.shadowingu.util;

import android.content.Context;
import android.util.Log;

import com.hansck.shadowingu.presentation.customview.VoiceSimilarityListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.UniversalAudioInputStream;
import be.tarsos.dsp.mfcc.MFCC;

/**
 * Created by Hans CK on 04-Jun-18.
 */
public class VoiceSimilarity {

	private static VoiceSimilarity instance = new VoiceSimilarity();
	private int index;
	private int fileNum = 1;
	private double[][] features1 = new double[2][12];
	private double[][] features2 = new double[1][12];

	double[] tes1 = new double[12];
	double[] tes2 = new double[12];
	VoiceSimilarityListener listener;

	public VoiceSimilarity() {
	}

	public static VoiceSimilarity getInstance() {
		return instance;
	}

	public void calculateSimilarity(final Context context, final int templateAudio, final File recordingAudio, final VoiceSimilarityListener listener) throws FileNotFoundException {

		this.listener = listener;
		index = 0;
		int sampleRate = 16000;
		int bufferSize = 2560;
		int bufferOverlap = 128;

		AudioDispatcher dispatcher;
		if (fileNum == 1) {
			InputStream inStream = context.getResources().openRawResource(templateAudio);
			dispatcher = new AudioDispatcher(new UniversalAudioInputStream(inStream, new TarsosDSPAudioFormat(sampleRate, bufferSize, 1, true, true)), bufferSize, bufferOverlap);
		} else {
			InputStream inStream = new FileInputStream(recordingAudio);
			dispatcher = new AudioDispatcher(new UniversalAudioInputStream(inStream, new TarsosDSPAudioFormat(sampleRate, bufferSize, 1, true, true)), bufferSize, bufferOverlap);
		}
		final MFCC mfcc = new MFCC(bufferSize, sampleRate, 12, 40, 300, 3000);

		dispatcher.addAudioProcessor(mfcc);
		dispatcher.addAudioProcessor(new AudioProcessor() {

			@Override
			public void processingFinished() {
				Log.e("MFCC", "DONE");
				if (fileNum == 1) {
					fileNum = 2;
					try {
						calculateSimilarity(context, templateAudio, recordingAudio, listener);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					calculateDTW();
				}
			}

			@Override
			public boolean process(AudioEvent audioEvent) {
				mfcc.process(audioEvent);
				float[] audio_float = mfcc.getMFCC();

				final double[] audio_double = new double[audio_float.length];
				for (int i = 0; i < audio_float.length; i++) {
					audio_double[i] = audio_float[i];  // no casting needed
				}

				if (fileNum == 1) {
					features1[index] = audio_double;
				} else {
					features2[index] = audio_double;
				}
				index++;
				Log.e("MFCC", Arrays.toString(audio_float));
				return true;
			}
		});
		dispatcher.run();
	}

	private void calculateDTW() {
		double[] variance = new double[12];
		Arrays.fill(variance, 1);

		DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(features1, features2, variance);

		// calculate the distance
		double distance = dtw.calDistance();
		listener.onSimilarityCalculated(distance);
	}
}
