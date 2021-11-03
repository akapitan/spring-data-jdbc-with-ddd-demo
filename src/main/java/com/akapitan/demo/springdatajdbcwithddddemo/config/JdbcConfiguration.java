package com.akapitan.demo.springdatajdbcwithddddemo.config;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

  @Bean
  BeforeSaveCallback<AggregateRoot> beforeSaveCallback() {
    return (entity, mutableAggregateChange) -> {
      if (entity.getId() == null) {
        entity.setId(UUID.randomUUID());
      }
      return entity;
    };
  }
}
