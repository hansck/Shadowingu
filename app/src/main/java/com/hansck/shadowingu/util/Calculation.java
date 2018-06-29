package com.hansck.shadowingu.util;

import android.content.Context;
import android.util.Log;

import com.hansck.shadowingu.presentation.customview.VoiceSimilarityListener;

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
public class Calculation {

	private static Calculation instance = new Calculation();
	private int index;
	private int fileNum = 1;
	private double[][] features1 = new double[2][12];
	private double[][] features2 = new double[1][12];

	double[] tes1 = new double[12];
	double[] tes2 = new double[12];
	VoiceSimilarityListener listener;

	public Calculation() {
	}

	public static Calculation getInstance() {
		return instance;
	}

	public void calculateMFCC(final Context context, final int file1, final int file2, final VoiceSimilarityListener listener) {

		this.listener = listener;
		index = 0;
		int sampleRate = 16000;
		int bufferSize = 512;
		int bufferOverlap = 128;

//		new AndroidFFMPEGLocator(context);
		InputStream inStream = context.getResources().openRawResource(fileNum == 1 ? file1 : file2);
		AudioDispatcher dispatcher = new AudioDispatcher(new UniversalAudioInputStream(inStream, new TarsosDSPAudioFormat(sampleRate, bufferSize, 1, true, true)), bufferSize, bufferOverlap);
		final MFCC mfcc = new MFCC(bufferSize, sampleRate, 12, 40, 300, 3000);

		dispatcher.addAudioProcessor(mfcc);
		dispatcher.addAudioProcessor(new AudioProcessor() {

			@Override
			public void processingFinished() {
				Log.e("MFCC", "DONE");
				if (fileNum == 1) {
					fileNum = 2;
					calculateMFCC(context, file1, file2, listener);
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
		listener.onMFCCCalculated(distance);
	}
}
