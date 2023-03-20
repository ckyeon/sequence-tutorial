package com.sequence.sequencetutorial.todo.application;

import com.sequence.sequencetutorial.common.Id;
import com.sequence.sequencetutorial.todo.domain.Todo;
import com.sequence.sequencetutorial.todo.domain.repository.TodoRepository;
import com.sequence.sequencetutorial.todo.presentation.dto.CreateDto;
import com.sequence.sequencetutorial.todo.presentation.dto.UpdateDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {

  private final TodoRepository todoRepository;

  public TodoService(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Transactional
  public void create(CreateDto dto) {
    Todo todo = dto.toEntity();
    todoRepository.save(todo);
  }

  @Transactional(readOnly = true)
  public List<Todo> findAll() {
    return todoRepository.findAll();
  }

  @Transactional
  public void update(Id<Todo, Long> id, UpdateDto dto) {
    Long todoId = id.value();
    Todo todo = todoRepository.findById(todoId)
      .orElseThrow(() -> new RuntimeException("NotFoundTodo"));

    Todo updateTodo = dto.toEntity();
    todo.update(updateTodo);
  }

  @Transactional
  public void delete(Id<Todo, Long> id) {
    Long todoId = id.value();
    todoRepository.deleteById(todoId);
  }
}
