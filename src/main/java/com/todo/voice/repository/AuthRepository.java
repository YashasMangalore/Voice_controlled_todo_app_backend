package com.todo.voice.repository;

import com.todo.voice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<User,Long> {
}
