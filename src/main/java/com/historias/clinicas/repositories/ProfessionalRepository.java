package com.historias.clinicas.repositories;

import com.historias.clinicas.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {}
