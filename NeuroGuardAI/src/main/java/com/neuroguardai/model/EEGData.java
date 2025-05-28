package com.neuroguardai.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eeg_data")
public class EEGData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String patientId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // EEG Channel Data
    @Column(nullable = false)
    private double channel1;

    @Column(nullable = false)
    private double channel2;

    @Column(nullable = false)
    private double channel3;

    @Column(nullable = false)
    private double channel4;

    // Frequency Band Features
    @Column(nullable = false)
    private double delta;

    @Column(nullable = false)
    private double theta;

    @Column(nullable = false)
    private double alpha;

    @Column(nullable = false)
    private double beta;

    @Column(nullable = false)
    private double gamma;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getChannel1() {
        return channel1;
    }

    public void setChannel1(double channel1) {
        this.channel1 = channel1;
    }

    public double getChannel2() {
        return channel2;
    }

    public void setChannel2(double channel2) {
        this.channel2 = channel2;
    }

    public double getChannel3() {
        return channel3;
    }

    public void setChannel3(double channel3) {
        this.channel3 = channel3;
    }

    public double getChannel4() {
        return channel4;
    }

    public void setChannel4(double channel4) {
        this.channel4 = channel4;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
       this.gamma = gamma;
    }
}
