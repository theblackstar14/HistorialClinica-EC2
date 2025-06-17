package com.historias.clinicas.repositories;

import com.historias.clinicas.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {}
