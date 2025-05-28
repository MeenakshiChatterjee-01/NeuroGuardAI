package com.neuroguardai.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientId;

    private String message;

    private boolean resolved;

    private LocalDateTime timestamp;

    // Getters and Setters
}
