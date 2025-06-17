// src/main/java/com/historias/clinicas/entity/Professional.java
package com.historias.clinicas.entity;

import jakarta.persistence.*;
import com.historias.clinicas.entity.enums.*;

@Entity
@Table(name = "professional")
public class Professional {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "prof_type", nullable = false)
    private ProfType profType;

    @Column(name = "license_number", nullable = false, unique = true, length = 50)
    private String licenseNumber;

    @Column(length = 120)
    private String specialty;

    // CONSTRUCTORS
    public Professional() {}

    public Professional(UserEntity user, ProfType profType, String licenseNumber, String specialty) {
        this.user = user;
        this.profType = profType;
        this.licenseNumber = licenseNumber;
        this.specialty = specialty;
    }

    // GETTERS & SETTERS
    public Integer getUserId() { return userId; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public ProfType getProfType() { return profType; }
    public void setProfType(ProfType profType) { this.profType = profType; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}
