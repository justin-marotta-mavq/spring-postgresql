package com.justinmarotta.postgresql.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justinmarotta.postgresql.model.TodoItem;
import com.justinmarotta.postgresql.model.User;
import com.justinmarotta.postgresql.repository.ITodoItemRepository;

@Service
public class TodoService implements ITodoService {

    @Autowired
    private ITodoItemRepository todoRepository;

    @Autowired
    private IUserService userService;

    @Override
    public TodoItem save(TodoItem todoItem, String username) {
        User user = userService.findByUsername(username);
        todoItem.setUserId(user.getId());
        todoItem.setDone(false);
        todoItem.setCreatedDate(LocalDateTime.now());
        todoItem.setUpdatedDate(LocalDateTime.now());
        return todoRepository.save(todoItem);
    }

    @Override
    public TodoItem completeTask(Long itemId, String username) {
        TodoItem todoItem = todoRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Todo item not found"));
        if (!todoItem.getUserId().equals(userService.findByUsername(username).getId())) {
            throw new RuntimeException("You do not have permission to complete this task");
        }
        todoItem.setDone(true);
        todoItem.setUpdatedDate(LocalDateTime.now());
        return todoRepository.save(todoItem);
    }

    @Override
    public List<TodoItem> findWaitingList(Long userId) {
        return todoRepository.findByUserIdAndDoneFalse(userId);
    }
}
