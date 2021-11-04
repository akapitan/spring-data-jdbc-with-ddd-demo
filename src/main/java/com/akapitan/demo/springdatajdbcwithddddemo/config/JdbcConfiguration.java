package com.akapitan.demo.springdatajdbcwithddddemo.config;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Minion;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.MinionRowMapper;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.DefaultQueryMappingConfiguration;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

  @Bean
  BeforeSaveCallback<AggregateRoot> beforeSaveCallback() {
    return (root, mutableAggregateChange) -> {
      if (root.getId() == null) {
        root.setId(UUID.randomUUID());
      }
      return root;
    };
  }

  @Bean
  QueryMappingConfiguration setRowMappers() {
    return new DefaultQueryMappingConfiguration()
        .registerRowMapper(Minion.class, new MinionRowMapper())
        ;
  }
}
