package com.neuroguardai.config;

import com.neuroguardai.util.ModelUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void initModel() {
        try {
            // Load and train the model from the correct CSV file path
            ModelUtil.trainModelFromCSV("data/NeuroguardAIdatasets.csv");
            System.out.println("✅ EEG model trained successfully.");
        } catch (Exception e) {
            System.err.println("❌ Failed to train EEG model: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
