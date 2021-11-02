package com.akapitan.demo.springdatajdbcwithddddemo.domain.shared;

import java.util.UUID;
import org.springframework.data.annotation.Id;

public class AggregateRoot {

  @Id
  private UUID id;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }
}
