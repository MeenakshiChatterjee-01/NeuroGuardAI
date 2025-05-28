package com.neuroguardai.repository;

import com.neuroguardai.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Find all alerts for a specific patient
    List<Alert> findByPatientId(String patientId);

    // Find all unresolved alerts
    List<Alert> findByResolvedFalse();

    // Find all resolved alerts
    List<Alert> findByResolvedTrue();

    // Find alerts for a patient that are unresolved
    List<Alert> findByPatientIdAndResolvedFalse(String patientId);

    // Find alerts within a specific time range
    List<Alert> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Find alerts for a patient within a specific time range
    List<Alert> findByPatientIdAndTimestampBetween(String patientId, LocalDateTime start, LocalDateTime end);

    // Custom query: Find recent alerts (e.g., last 24 hours)
    @Query("SELECT a FROM Alert a WHERE a.timestamp >= :since")
    List<Alert> findRecentAlerts(LocalDateTime since);

    // Custom query: Search alerts by keyword in message
    @Query("SELECT a FROM Alert a WHERE LOWER(a.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Alert> searchByMessageKeyword(String keyword);
}
