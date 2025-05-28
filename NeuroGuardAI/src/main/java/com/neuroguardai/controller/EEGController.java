package com.neuroguardai.controller;

import com.neuroguardai.model.EEGData;
import com.neuroguardai.service.EEGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EEGController {

    @Autowired
    private EEGService eegService;

    @PostMapping("/eeg")
    public EEGData saveEEGData(@RequestBody EEGData eegData) {
        return eegService.saveEEGData(eegData);
    }

    @GetMapping("/eeg")
    public List<EEGData> getEEGData(@RequestParam(required = false) Long id) {
        if (id != null) {
            return List.of(eegService.getEEGDataById(id));
        } else {
            return eegService.getAllEEGData();
        }
    }
}
