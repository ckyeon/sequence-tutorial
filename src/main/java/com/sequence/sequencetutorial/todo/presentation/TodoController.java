package com.sequence.sequencetutorial.todo.presentation;

import com.sequence.sequencetutorial.common.Id;
import com.sequence.sequencetutorial.todo.application.TodoService;
import com.sequence.sequencetutorial.todo.domain.Todo;
import com.sequence.sequencetutorial.todo.presentation.dto.CreateDto;
import com.sequence.sequencetutorial.todo.presentation.dto.UpdateDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

  private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @PostMapping("/todo")
  public ResponseEntity<Void> create(@RequestBody CreateDto dto) {
    todoService.create(dto);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/todo")
  public ResponseEntity<List<Todo>> findAll() {
    List<Todo> findTodos = todoService.findAll();
    return ResponseEntity.ok(findTodos);
  }

  @PutMapping("/todo/{id}")
  public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody UpdateDto dto) {
    Id<Todo, Long> todoId = Id.of(Todo.class, id);
    todoService.update(todoId, dto);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/todo/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    Id<Todo, Long> todoId = Id.of(Todo.class, id);
    todoService.delete(todoId);
    return ResponseEntity.ok(null);
  }
}
