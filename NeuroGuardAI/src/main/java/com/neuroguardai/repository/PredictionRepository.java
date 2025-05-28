package com.neuroguardai.repository;

import com.neuroguardai.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    // Find predictions by patient ID
    List<Prediction> findByPatientId(String patientId);

    // Find predictions with confidence above a threshold
    List<Prediction> findByConfidenceGreaterThan(double threshold);
}
