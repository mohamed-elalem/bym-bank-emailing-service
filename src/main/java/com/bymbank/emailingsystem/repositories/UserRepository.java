package com.bymbank.emailingsystem.repositories;

import com.bymbank.emailingsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReadOnlyRepository<User, Long> {
}
