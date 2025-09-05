package com.justinmarotta.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justinmarotta.postgresql.model.TodoItem;

public interface ITodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> findByUserIdAndDoneFalse(Long userId);
}
