package com.neuroguardai.util;

import java.util.Arrays;

public class SignalProcessingUtil {

    // Normalize EEG signal values to range [0, 1]
    public static double[] normalize(double[] signal) {
        double min = Arrays.stream(signal).min().orElse(0);
        double max = Arrays.stream(signal).max().orElse(1);
        if (max - min == 0) return new double[signal.length];

        double[] normalized = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            normalized[i] = (signal[i] - min) / (max - min);
        }
        return normalized;
    }

    // Light smoothing using moving average with window size 3
    public static double[] movingAverage(double[] signal, int windowSize) {
        if (windowSize <= 1) return signal;

        double[] result = new double[signal.length];
        for (int i = 0; i < signal.length; i++) {
            int start = Math.max(0, i - windowSize + 1);
            double sum = 0;
            for (int j = start; j <= i; j++) {
                sum += signal[j];
            }
            result[i] = sum / (i - start + 1);
        }
        return result;
    }

    // Optional: Compute mean of the signal
    public static double mean(double[] signal) {
        return Arrays.stream(signal).average().orElse(0);
    }

    // Optional: Compute standard deviation of the signal
    public static double standardDeviation(double[] signal) {
        double mean = mean(signal);
        double variance = Arrays.stream(signal)
                                .map(val -> Math.pow(val - mean, 2))
                                .average()
                                .orElse(0);
        return Math.sqrt(variance);
    }
}
