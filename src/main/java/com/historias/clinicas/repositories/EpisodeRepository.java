// src/main/java/com/historias/clinicas/repositories/EpisodeRepository.java
package com.historias.clinicas.repositories;

import com.historias.clinicas.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    List<Episode> findByProfessionalUserUserIdAndDateOutIsNull(Integer profId);
    long countByDateOutIsNull();
}
