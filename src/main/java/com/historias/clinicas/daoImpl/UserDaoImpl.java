package com.historias.clinicas.daoImpl;

import com.historias.clinicas.dao.UserDao;
import com.historias.clinicas.entity.UserEntity;
import com.historias.clinicas.repositories.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository @Transactional
public class UserDaoImpl implements UserDao {

    private final UserRepository repo;
    public UserDaoImpl(UserRepository repo) { this.repo = repo; }

    public Optional<UserEntity> findByUsername(String username) {
        return repo.findByUsername(username);
    }
    public UserEntity save(UserEntity u) { return repo.save(u); }
}
