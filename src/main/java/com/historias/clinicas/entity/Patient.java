// src/main/java/com/historias/clinicas/entity/Patient.java
package com.historias.clinicas.entity;
import com.historias.clinicas.entity.enums.*;

import jakarta.persistence.*;

@Entity
@Table(name = "patient",
       uniqueConstraints = @UniqueConstraint(columnNames = {"doc_type","doc_number"}))
public class Patient {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type", nullable = false)
    private DocType docType;

    @Column(name = "doc_number", nullable = false, length = 20)
    private String docNumber;

    @Column(length = 200)
    private String address;

    // CONSTRUCTORS
    public Patient() {}

    public Patient(UserEntity user, DocType docType, String docNumber, String address) {
        this.user = user;
        this.docType = docType;
        this.docNumber = docNumber;
        this.address = address;
    }

    // GETTERS & SETTERS
    public Integer getUserId() { return userId; }

    public UserEntity getUser() { return user; }
    public void setUser(UserEntity user) { this.user = user; }

    public DocType getDocType() { return docType; }
    public void setDocType(DocType docType) { this.docType = docType; }

    public String getDocNumber() { return docNumber; }
    public void setDocNumber(String docNumber) { this.docNumber = docNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
