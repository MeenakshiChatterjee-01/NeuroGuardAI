package com.neuroguardai.service;

import com.neuroguardai.exception.CustomException;
import com.neuroguardai.model.EEGData;
import com.neuroguardai.repository.EEGRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EEGService {

    @Autowired
    private EEGRepository eegRepository;

    /**
     * Save a single EEG data entry.
     * @param eegData EEGData object containing channel and frequency band values.
     * @return Saved EEGData entity.
     */
    public EEGData saveEEGData(EEGData eegData) {
        if (eegData == null) {
            throw new CustomException("EEG data cannot be null");
        }
        return eegRepository.save(eegData);
    }

    /**
     * Retrieve EEG data by its unique ID.
     * @param id EEG data ID.
     * @return EEGData object.
     */
    public EEGData getEEGDataById(Long id) {
        return eegRepository.findById(id)
                .orElseThrow(() -> new CustomException("EEG data not found with ID: " + id));
    }

    /**
     * Retrieve all EEG data entries.
     * @return List of EEGData objects.
     */
    public List<EEGData> getAllEEGData() {
        List<EEGData> dataList = eegRepository.findAll();
        if (dataList == null || dataList.isEmpty()) {
            throw new CustomException("No EEG data found");
        }
        return dataList;
    }
}
