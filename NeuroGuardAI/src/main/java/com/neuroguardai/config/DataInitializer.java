package com.neuroguardai.config;

import com.neuroguardai.model.EEGData;
import com.neuroguardai.model.Prediction;
import com.neuroguardai.repository.EEGRepository;
import com.neuroguardai.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringTokenizer;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EEGRepository eegRepository;

    @Autowired
    private PredictionService predictionService;

    @Override
    public void run(String... args) {
        if (eegRepository.count() == 0) {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new ClassPathResource("data/NeuroguardAIdatasets.csv").getInputStream(),
                            StandardCharsets.UTF_8))) {

                String line = reader.readLine(); // Skip header

                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");

                    if (fields.length >= 11) {
                        EEGData data = new EEGData();

                        // Parse timestamp in mm:ss.S format and attach today's date
                        String[] timeParts = fields[0].split(":");
                        int minutes = Integer.parseInt(timeParts[0]);
                        double seconds = Double.parseDouble(timeParts[1]);
                        int wholeSeconds = (int) seconds;
                        int nanos = (int) ((seconds - wholeSeconds) * 1_000_000_000);

                        LocalDateTime timestamp = LocalDate.now().atStartOfDay()
                                .plusMinutes(minutes)
                                .plusSeconds(wholeSeconds)
                                .plusNanos(nanos);

                        data.setTimestamp(timestamp);
                        data.setPatientId(fields[1]);
                        data.setChannel1(Double.parseDouble(fields[2]));
                        data.setChannel2(Double.parseDouble(fields[3]));
                        data.setChannel3(Double.parseDouble(fields[4]));
                        data.setChannel4(Double.parseDouble(fields[5]));
                        data.setDelta(Double.parseDouble(fields[6]));
                        data.setTheta(Double.parseDouble(fields[7]));
                        data.setAlpha(Double.parseDouble(fields[8]));
                        data.setBeta(Double.parseDouble(fields[9]));
                        data.setGamma(Double.parseDouble(fields[10]));

                        eegRepository.save(data);

                        // Trigger prediction
                        Prediction prediction = predictionService.predictFromEEGData(data);

                        // Print prediction to console
                        System.out.println("Prediction for " + data.getPatientId() + " at " + data.getTimestamp() + ":");
                        System.out.println("  ➤ Result: " + prediction.getResult());
                        System.out.println("  ➤ Confidence: " + prediction.getConfidence());
                        System.out.println("--------------------------------------------------");
                    }
                }

                System.out.println("✅ EEG data loaded and predictions printed to console.");
            } catch (Exception e) {
                System.err.println("❌ Error loading EEG data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
