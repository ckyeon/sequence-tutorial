package com.sequence.sequencetutorial.todo.application;

import static com.google.common.base.Preconditions.checkArgument;

import com.sequence.sequencetutorial.common.Id;
import com.sequence.sequencetutorial.todo.domain.Todo;
import com.sequence.sequencetutorial.todo.domain.repository.TodoRepository;
import com.sequence.sequencetutorial.todo.presentation.dto.CreateDto;
import com.sequence.sequencetutorial.todo.presentation.dto.TodoResponse;
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
    checkArgument(dto != null, "create dto must be provided.");

    Todo todo = dto.toEntity();
    todoRepository.save(todo);
  }

  @Transactional(readOnly = true)
  public List<TodoResponse> findAll() {
    return todoRepository.findAll()
      .stream()
      .map(TodoResponse::fromEntity)
      .toList();
  }

  @Transactional
  public void update(Id<Todo, Long> id, UpdateDto dto) {
    checkArgument(id != null, "id must be provided.");
    checkArgument(dto != null, "update dto must be provided.");

    Long todoId = id.value();
    todoRepository.findById(todoId)
      .ifPresentOrElse(
        (todo) -> {
          Todo updateTodo = dto.toEntity();
          todo.update(updateTodo);
        },
        () -> {
          CreateDto createDto = new CreateDto(
            dto.getTitle(),
            dto.getBody(),
            dto.isDone()
          );
          create(createDto);
        }
      );
  }

  @Transactional
  public void delete(Id<Todo, Long> id) {
    checkArgument(id != null, "id must be provided.");

    Long todoId = id.value();
    todoRepository.deleteById(todoId);
  }
}
