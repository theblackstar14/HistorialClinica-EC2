package com.historias.clinicas.repositories;

import com.historias.clinicas.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> { }