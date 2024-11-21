package org.example.smartjob.domain.repositories.user;

import org.example.smartjob.domain.entities.User;

public interface IUserRepository {
    boolean existsByEmail(String email);

    User save(User user);
}
