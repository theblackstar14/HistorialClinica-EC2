package com.historias.clinicas.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prescriptionId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "episode_id")
    private Episode episode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @Column(nullable = false)
    private String dose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Route route;

    private String frequency;
    private LocalDateTime startDt;
    private LocalDateTime endDt;

    public enum Route { VO, IV, IM, SC, TOP }

    public Prescription() {
    }

    // --- Getters y setters ---

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalDateTime getStartDt() {
        return startDt;
    }

    public void setStartDt(LocalDateTime startDt) {
        this.startDt = startDt;
    }

    public LocalDateTime getEndDt() {
        return endDt;
    }

    public void setEndDt(LocalDateTime endDt) {
        this.endDt = endDt;
    }
}
