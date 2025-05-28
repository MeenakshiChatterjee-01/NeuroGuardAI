package com.neuroguardai;

import com.neuroguardai.util.ModelUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class NeuroGuardAIApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeuroGuardAIApplication.class, args);
    }

    @PostConstruct
    public void initModel() {
        try {
            String csvPath = "data/sample_eeg.csv"; // Updated path for classpath resource
            ModelUtil.trainModelFromCSV(csvPath);
            System.out.println("Model trained successfully from: " + csvPath);
        } catch (Exception e) {
            System.err.println("Failed to train model: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
