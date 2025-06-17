// src/main/java/com/historias/clinicas/entity/Episode.java
package com.historias.clinicas.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer episodeId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prof_id")
    private Professional professional;

    @Column(nullable = false)
    private LocalDateTime dateIn;

    private LocalDateTime dateOut;

    // Getters y setters
    public Integer getEpisodeId() { return episodeId; }
    public void setEpisodeId(Integer episodeId) { this.episodeId = episodeId; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Professional getProfessional() { return professional; }
    public void setProfessional(Professional professional) { this.professional = professional; }

    public LocalDateTime getDateIn() { return dateIn; }
    public void setDateIn(LocalDateTime dateIn) { this.dateIn = dateIn; }

    public LocalDateTime getDateOut() { return dateOut; }
    public void setDateOut(LocalDateTime dateOut) { this.dateOut = dateOut; }
}
