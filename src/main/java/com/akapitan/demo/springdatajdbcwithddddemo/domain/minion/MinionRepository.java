package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

public interface MinionRepository extends Repository<Minion, UUID> {

  Minion save(Minion minion);

  @Query(value =
      """
          select m.*,
            (select coalesce(json_agg(t.*), '[]'::json) from toy t where t.minion = m.id)   as toys,
            (select coalesce(json_agg(c.*), '[]'::json) from color c where c.minion = m.id) as colors
          from minion m
            where m.id = :id;
          """)
  Minion findById(UUID id);

  @Query(value =
      """
          select m.*,
            (select coalesce(json_agg(t.*), '[]'::json) from toy t where t.minion = m.id)   as toys,
            (select coalesce(json_agg(c.*), '[]'::json) from color c where c.minion = m.id) as colors
          from minion m
            where evil_master = :evilMasterRef
          """)
  List<Minion> findAllByEvilMaster(UUID evilMasterRef);
}
