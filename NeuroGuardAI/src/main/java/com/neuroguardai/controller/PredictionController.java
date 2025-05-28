package com.neuroguardai.controller;

import com.neuroguardai.model.EEGData;
import com.neuroguardai.model.Prediction;
import com.neuroguardai.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/prediction")
    public Prediction savePrediction(@RequestBody Prediction prediction) {
        return predictionService.savePrediction(prediction);
    }

    @GetMapping("/prediction")
    public List<Prediction> getPredictions(@RequestParam(required = false) Long id) {
        if (id != null) {
            return List.of(predictionService.getPredictionById(id));
        } else {
            return predictionService.getAllPredictions();
        }
    }

    @PostMapping("/predict")
    public Prediction predictFromEEG(@RequestBody EEGData eegData) {
        return predictionService.predictFromEEGData(eegData);
    }
}
