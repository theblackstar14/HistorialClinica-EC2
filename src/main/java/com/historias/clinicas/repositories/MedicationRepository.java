package com.historias.clinicas.repositories;

import com.historias.clinicas.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Integer> { }