package com.neuroguardai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.neuroguardai.service.AlertService;
import com.neuroguardai.model.Alert;

import java.util.List;

@RestController
public class AlertController {

    @Autowired
    private AlertService alertService;

    @PostMapping("/alert")
    public Alert saveAlert(@RequestBody Alert alert) {
        return alertService.saveAlert(alert);
    }

 @GetMapping("/alert")
    public List<Alert> getAlerts(@RequestParam(required = false) Long id) {
        if (id != null) {
            //return List.of(alertService.getAlertById(id));
            return java.util.Arrays.asList(alertService.getAlertById(id));

        } else {
            return alertService.getAllAlerts();
        }
    }
}
