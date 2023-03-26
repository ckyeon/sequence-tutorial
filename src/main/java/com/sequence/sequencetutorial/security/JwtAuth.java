package com.sequence.sequencetutorial.security;

import static com.google.common.base.Preconditions.checkArgument;

import com.sequence.sequencetutorial.auth.domain.Role;
import com.sequence.sequencetutorial.common.Id;
import com.sequence.sequencetutorial.user.domain.User;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JwtAuth {

  private final Id<User, Long> id;

  private final Role role;

  public JwtAuth(Long id, Role role) {
    checkArgument(id != null, "id must be provided.");
    checkArgument(role != null, "role must be provided.");

    this.id = Id.of(User.class, id);
    this.role = role;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
      .append("id", id)
      .append("role", role)
      .toString();
  }
}
