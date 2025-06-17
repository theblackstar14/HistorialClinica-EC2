package com.historias.clinicas.repositories;

import com.historias.clinicas.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    /** Devuelve el único administrador primario (si existe). */
    Optional<Admin> findByPrimaryAdminTrue();

    /** Comprueba si un usuario concreto es admin primario. */
    boolean existsByUserIdAndPrimaryAdminTrue(Integer userId);
}
