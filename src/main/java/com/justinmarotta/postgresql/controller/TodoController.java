package com.justinmarotta.postgresql.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justinmarotta.postgresql.model.TodoItem;
import com.justinmarotta.postgresql.service.ITodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    
    @Autowired
    private ITodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(Principal principal, @RequestBody TodoItem todoItem) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        String username = principal.getName();
        TodoItem createdTodo = todoService.save(todoItem, username);
        return ResponseEntity.ok(createdTodo);
    }

    @PutMapping("{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable("todoId") Long todoId, Principal principal) {
        return ResponseEntity.ok(todoService.completeTask(todoId, principal.getName()));
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getWaitingTasks(@PathVariable("userId") Long userId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok(todoService.findWaitingList(userId));
    }
}
