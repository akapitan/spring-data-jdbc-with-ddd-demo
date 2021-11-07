package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Description.StringToDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class MinionRowMapper implements RowMapper<Minion> {

  @Override
  public Minion mapRow(ResultSet rs, int rowNum) throws SQLException {

    List<Color> colors = null;
    List<Toy> toys = null;
    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, false);
    try {
      objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
      toys = objectMapper.readValue(rs.getString("toys"), new TypeReference<>() {
      });
      colors = objectMapper.readValue(rs.getString("colors"), new TypeReference<>() {
      });
      toys.remove(null);
      colors.remove(null);
    } catch (JsonProcessingException e) {
      throw new UnsupportedOperationException(e);
    }

    Minion minion = Minion.builder()
        .id(UUID.fromString(rs.getString("id")))
        .evilMaster(UUID.fromString(rs.getString("evil_master")))
        .name(rs.getString("name"))
        .build();

    String description1 = rs.getString("description");
    if (description1 != null) {
      PGobject pGobject = new PGobject();
      pGobject.setValue(description1);
      Description description = StringToDescription.INSTANCE.convert(pGobject);
      minion.setDescription(description);
    }
    toys.forEach(minion::addToy);
    colors.forEach(minion::addColor);

    return minion;
  }
}
