package com.historias.clinicas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicationId;

    @Column(nullable = false)
    private String name;

    private String form;
    private String concentration;

    // Getters y Setters
    public Integer getMedicationId() { return medicationId; }
    public void setMedicationId(Integer medicationId) { this.medicationId = medicationId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getForm() { return form; }
    public void setForm(String form) { this.form = form; }

    public String getConcentration() { return concentration; }
    public void setConcentration(String concentration) { this.concentration = concentration; }
}