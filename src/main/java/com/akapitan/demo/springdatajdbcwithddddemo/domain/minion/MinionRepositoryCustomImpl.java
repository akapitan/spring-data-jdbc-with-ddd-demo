package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Minion.Link;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MinionRepositoryCustomImpl implements
    MinionRepositoryCustom {

  private final JdbcTemplate jdbc;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public MinionRepositoryCustomImpl(JdbcTemplate jdbc,
      NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    this.jdbc = jdbc;
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

  }

  @Override
  public Minion findByIdV2(UUID id, final Set<Link> links) {

    String select = "select m.* ";
    String from = " from minion m";
    String where = " where m.id = :id ";

    select = Link.setSelect(links, select);

    return namedParameterJdbcTemplate.queryForObject(select + from + where,
        Map.of("id", id), new MinionRowMapper());

  }
}
