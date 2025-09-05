package com.justinmarotta.postgresql.service;

import java.util.List;

import com.justinmarotta.postgresql.model.TodoItem;

public interface ITodoService {
    
    TodoItem save(TodoItem todoItem, String username);

    TodoItem completeTask(Long itemId, String username);

    List<TodoItem> findWaitingList(Long userId);
}
