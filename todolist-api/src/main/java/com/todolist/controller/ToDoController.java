package com.todolist.controller;

import com.todolist.model.ToDo;
import com.todolist.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping
    public List<ToDo> getAllTodos() {
        return toDoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable Long id) {
        Optional<ToDo> todo = toDoService.findById(id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ToDo> createTodo(@RequestBody ToDo toDo) {
        ToDo savedToDo = toDoService.save(toDo);
        return new ResponseEntity<>(savedToDo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDo> updateTodo(@PathVariable Long id, @RequestBody ToDo toDo) {
        Optional<ToDo> existingTodo = toDoService.findById(id);
        if (existingTodo.isPresent()) {
            toDo.setId(id);
            ToDo updatedToDo = toDoService.save(toDo);
            return ResponseEntity.ok(updatedToDo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
        toDoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}