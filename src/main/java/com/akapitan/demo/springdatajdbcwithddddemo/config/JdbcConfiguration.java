package com.akapitan.demo.springdatajdbcwithddddemo.config;

import static java.util.Arrays.asList;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Description.DescriptionToString;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Description.StringToDescription;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Minion;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.MinionRowMapper;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.DefaultQueryMappingConfiguration;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

  @Override
  public DataAccessStrategy dataAccessStrategyBean(NamedParameterJdbcOperations operations,
      JdbcConverter jdbcConverter, JdbcMappingContext context, Dialect dialect) {
    return super.dataAccessStrategyBean(operations, jdbcConverter, context, dialect);
  }

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

  @Override
  @Bean
  public JdbcCustomConversions jdbcCustomConversions() {
    return new JdbcCustomConversions(
        asList(DescriptionToString.INSTANCE, StringToDescription.INSTANCE));
  }

}
