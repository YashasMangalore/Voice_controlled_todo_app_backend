package com.todo.voice.repository;

import com.todo.voice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);//magic methods
    boolean existByEmail(String email);
}
