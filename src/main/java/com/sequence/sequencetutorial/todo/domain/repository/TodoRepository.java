package com.sequence.sequencetutorial.todo.domain.repository;

import com.sequence.sequencetutorial.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}
