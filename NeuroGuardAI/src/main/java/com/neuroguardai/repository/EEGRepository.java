package com.neuroguardai.repository;

import com.neuroguardai.model.EEGData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EEGRepository extends JpaRepository<EEGData, Long> {

    // Find all EEG data for a specific patient
    List<EEGData> findByPatientId(String patientId);

    // Find EEG data for a patient within a specific time range
    List<EEGData> findByPatientIdAndTimestampBetween(String patientId, LocalDateTime start, LocalDateTime end);

    // Custom query to find recent EEG data (e.g., last 24 hours)
    @Query("SELECT e FROM EEGData e WHERE e.timestamp >= :since")
    List<EEGData> findRecentEEGData(LocalDateTime since);

    // Removed: rawData search (field no longer exists in EEGData)
    // @Query("SELECT e FROM EEGData e WHERE e.rawData LIKE %:keyword%")
    // List<EEGData> searchByRawDataKeyword(String keyword);
}
