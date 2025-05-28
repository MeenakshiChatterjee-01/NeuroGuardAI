package com.neuroguardai.service;

import com.neuroguardai.model.EEGData;
import com.neuroguardai.model.Prediction;
import com.neuroguardai.repository.PredictionRepository;
import com.neuroguardai.util.ModelUtil;
import com.neuroguardai.util.SignalProcessingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    public Prediction savePrediction(Prediction prediction) {
        return predictionRepository.save(prediction);
    }

    public Prediction getPredictionById(Long id) {
        return predictionRepository.findById(id).orElse(null);
    }

    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll();
    }

    /**
     * Predict mental state from structured EEGData using KMeans clustering.
     */
    public Prediction predictFromEEGData(EEGData eegData) {
        try {
            // Combine EEG features into a vector
            double[] features = new double[] {
                eegData.getChannel1(),
                eegData.getChannel2(),
                eegData.getChannel3(),
                eegData.getChannel4(),
                eegData.getDelta(),
                eegData.getTheta(),
                eegData.getAlpha(),
                eegData.getBeta(),
                eegData.getGamma()
            };

            // Normalize and smooth the signal
            double[] normalized = SignalProcessingUtil.normalize(features);
            double[] smoothed = SignalProcessingUtil.movingAverage(normalized, 3);

            // Predict using KMeans or ML model
            String result = ModelUtil.predictCondition(smoothed);
            double confidence = 1.0; // Placeholder for confidence score

            // Create and save prediction
            Prediction prediction = new Prediction();
            prediction.setPatientId(eegData.getPatientId());
            prediction.setResult(result);
            prediction.setConfidence(confidence);
            prediction.setTimestamp(eegData.getTimestamp());

            return predictionRepository.save(prediction);

        } catch (Exception e) {
            throw new RuntimeException("Prediction failed: " + e.getMessage(), e);
        }
    }
}
