package com.historias.clinicas.dao;

import com.historias.clinicas.entity.UserEntity;
import java.util.Optional;

public interface UserDao {
    Optional<UserEntity> findByUsername(String username);
    UserEntity save(UserEntity user);
}
