// src/main/java/com/historias/clinicas/entity/Admin.java
package com.historias.clinicas.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "is_primary", nullable = false)
    private boolean primaryAdmin = false;

    // CONSTRUCTORS
    public Admin() {}

    public Admin(UserEntity user, boolean primaryAdmin) {
        this.user = user;
        this.primaryAdmin = primaryAdmin;
    }

    // GETTERS & SETTERS
    public Integer getUserId() { return userId; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public boolean isPrimaryAdmin() { return primaryAdmin; }
    public void setPrimaryAdmin(boolean primaryAdmin) { this.primaryAdmin = primaryAdmin; }
}
