package com.sequence.sequencetutorial.todo.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Todo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String body;

  private boolean done;

  protected Todo() {
  }

  public Todo(Long id, String title, String body, boolean done) {
    checkArgument(title != null, "title must be provided.");
    checkArgument(body != null, "body must be provided.");

    this.id = id;
    this.title = title;
    this.body = body;
    this.done = defaultIfNull(done, false);
  }

  public Todo(String title, String body, boolean done) {
    this(null, title, body, done);
  }

  public void update(Todo updateTodo) {
    this.title = updateTodo.title;
    this.body = updateTodo.body;
    this.done = updateTodo.done;
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
