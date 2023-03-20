package com.sequence.sequencetutorial.todo.presentation.dto;

import com.sequence.sequencetutorial.todo.domain.Todo;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class UpdateDto {

  private final String title;

  private final String body;

  private final boolean done;

  public UpdateDto(String title, String body, boolean done) {
    this.title = title;
    this.body = body;
    this.done = done;
  }

  public Todo toEntity() {
    return new Todo(title, body, done);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("title", title)
      .append("body", body)
      .append("done", done)
      .toString();
  }
}
