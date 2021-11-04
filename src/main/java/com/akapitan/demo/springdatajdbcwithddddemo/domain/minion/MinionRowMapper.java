package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.jdbc.core.RowMapper;

public class MinionRowMapper implements RowMapper<Minion> {

  @Override
  public Minion mapRow(ResultSet rs, int rowNum) throws SQLException {

    List<Color> colors = null;
    List<Toy> toys = null;
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
    try {
      toys = objectMapper.readValue(rs.getString("toys"), new TypeReference<List<Toy>>() {
      });
      colors = objectMapper.readValue(rs.getString("colors"), new TypeReference<List<Color>>() {
      });
      toys.remove(null);
      colors.remove(null);
      System.out.println(toys);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    Minion minion = Minion.builder()
        .id(UUID.fromString(rs.getString("id")))
        .evilMaster(UUID.fromString(rs.getString("evil_master")))
        .name(rs.getString("name"))
        .build();

    if (Objects.nonNull(toys)) {
      toys.forEach(minion::addToy);
    }
    if (Objects.nonNull(colors)) {
      colors.forEach(minion::addColor);
    }

    return minion;
  }
}
