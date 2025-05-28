package com.neuroguardai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.neuroguardai.model.Alert;
import com.neuroguardai.repository.AlertRepository;

import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    public Alert saveAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public Alert getAlertById(Long id) {
        return alertRepository.findById(id).orElse(null);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}
