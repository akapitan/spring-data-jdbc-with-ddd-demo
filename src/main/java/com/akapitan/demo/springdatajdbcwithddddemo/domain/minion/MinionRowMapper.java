package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Description.StringToDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

    try {
      objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
      if (hasColumn(rs, "toys")) {
        toys = objectMapper.readValue(rs.getString("toys"), new TypeReference<>() {
        });
        toys.remove(null);
        toys.forEach(minion::addToy);
      }
      if (hasColumn(rs, "colors")) {
        colors = objectMapper.readValue(rs.getString("colors"), new TypeReference<>() {
        });
        colors.remove(null);
        colors.forEach(minion::addColor);
      }
    } catch (JsonProcessingException e) {
      throw new UnsupportedOperationException(e);
    }

    return minion;
  }

  private static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
    ResultSetMetaData rsmd = rs.getMetaData();
    int columns = rsmd.getColumnCount();
    for (int x = 1; x <= columns; x++) {
      if (columnName.equals(rsmd.getColumnName(x))) {
        return true;
      }
    }
    return false;
  }
}
