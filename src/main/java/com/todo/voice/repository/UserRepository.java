package com.todo.voice.repository;

import com.todo.voice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);//magic methods
    boolean existByEmail(String email);
}
