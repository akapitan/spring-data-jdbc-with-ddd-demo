package com.akapitan.demo.springdatajdbcwithddddemo.domain.shared;

import java.util.Objects;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;

public abstract class AggregateRoot implements Persistable<UUID> {

  @Id
  private UUID id;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  @Override
  public boolean isNew() {
    return Objects.isNull(id);
  }
}
