package com.hansck.shadowingu.util;

import android.content.Context;
import android.util.Log;

import com.hansck.shadowingu.presentation.customview.VoiceSimilarityListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
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
public class SimilarityMatching {

    private static SimilarityMatching instance = new SimilarityMatching();
    private ArrayList<float[]> temp;
    private Feature feature = new Feature();
    private VoiceSimilarityListener listener;

    public SimilarityMatching() {
    }

    public static SimilarityMatching getInstance() {
        return instance;
    }

    public void calculateSimilarity(final Context context, final int template, final File recording, final int testAudio,
                                    final VoiceSimilarityListener listener, final int fileIdx) throws FileNotFoundException {
//	public void calculateSimilarity2(final Context context, final int templateAudio, final int recordingAudio, final VoiceSimilarityListener listener,
//									 final int fileIdx, final int sampleRates) throws FileNotFoundException {

        this.listener = listener;

        int bufferOverlap = 0;
        int samplesPerFrame = 256;
        int sampleRate = 44100;
        final int amountOfCepstrumCoef = 13;
        int amountOfMelFilters = 26;
        float lowerFilterFreq = 133.3334f;
        float upperFilterFreq = sampleRate / 2.0F;

        final AudioDispatcher dispatcher;
        final MFCC mfcc;
        InputStream inStream;
        temp = new ArrayList<>();

        Log.e("FILE", "FILE " + fileIdx);
        if (fileIdx == 1) {
            inStream = context.getResources().openRawResource(template);
        } else {
            if (recording == null) {
                inStream = context.getResources().openRawResource(testAudio);
            } else {
                inStream = new FileInputStream(recording);
            }
        }
        dispatcher = new AudioDispatcher(new UniversalAudioInputStream(inStream, new TarsosDSPAudioFormat(sampleRate, samplesPerFrame, 1, true, false)), samplesPerFrame, bufferOverlap);
        mfcc = new MFCC(samplesPerFrame, sampleRate, amountOfCepstrumCoef, amountOfMelFilters, 300, upperFilterFreq);

        dispatcher.addAudioProcessor(mfcc);
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public void processingFinished() {

                double[][] tempFeatures = new double[temp.size()][amountOfCepstrumCoef - 1];

                for (int i = 0; i < temp.size(); i++) {
                    for (int j = 0; j < amountOfCepstrumCoef - 1; j++) {
                        tempFeatures[i][j] = temp.get(i)[j];
                    }
                }

                feature.setFeatures(fileIdx, tempFeatures);

                if (fileIdx == 1) {
                    try {
                        calculateSimilarity(context, template, recording, testAudio, listener, 2);
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
                float[] mfcc_output = mfcc.getMFCC();
                mfcc_output = Arrays.copyOfRange(mfcc_output, 1, mfcc_output.length);

                temp.add(mfcc_output);
                return true;
            }
        });
        dispatcher.run();
    }

    private void calculateDTW() {
//		double[] variance = new double[features1[0].length];
//		for (int i = 0; i < variance.length; i++)
//			variance[i] = 1;

        DynamicTimeWrapping2D dtw = new DynamicTimeWrapping2D(feature.getFeatures1(), feature.getFeatures2());

        // calculate the distance
        double distance = dtw.calDistance();
        System.out.println(distance);
        listener.onSimilarityCalculated(distance);
    }
}

class Feature {

    private double[][] features1;
    private double[][] features2;

    public Feature() {
    }

    public double[][] getFeatures1() {
        return features1;
    }

    public double[][] getFeatures2() {
        return features2;
    }

    public void setFeatures(int iteration, double[][] features) {
        if (iteration == 1) {
            features1 = features;
            for (int i = 0; i < features1.length; i++) {
                Log.e("Features " + iteration, Arrays.toString(features1[i]));
            }
        } else {
            features2 = features;
            for (int i = 0; i < features2.length; i++) {
                Log.e("Features " + iteration, Arrays.toString(features2[i]));
            }
        }
    }
}
