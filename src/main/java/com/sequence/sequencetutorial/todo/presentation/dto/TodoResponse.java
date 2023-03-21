package com.sequence.sequencetutorial.todo.presentation.dto;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import com.sequence.sequencetutorial.todo.domain.Todo;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TodoResponse {

  private final Long id;

  private final String title;

  private final String body;

  private final boolean done;

  private TodoResponse(Long id, String title, String body, boolean done) {
    checkArgument(title != null, "title must be provided.");
    checkArgument(body != null, "body must be provided.");

    this.id = id;
    this.title = title;
    this.body = body;
    this.done = defaultIfNull(done, false);
  }

  public static TodoResponse fromEntity(Todo todo) {
    return new TodoResponse(
      todo.getId(),
      todo.getTitle(),
      todo.getBody(),
      todo.isDone()
    );
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getBody() {
    return body;
  }

  public boolean isDone() {
    return done;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("title", title)
      .append("body", body)
      .append("done", done)
      .toString();
  }
}
