package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

public interface MinionRepository extends Repository<Minion, UUID> {

  Minion save(Minion minion);

  Minion findById(UUID id);

  @Query(value =
      """
          select m.*, jsonb_agg(distinct t.*) as toys, json_agg(distinct c.*) as colors
          from minion m 
           left join toy t on t.minion = m.id
           left join color c on m.id = c.minion
          where evil_master = :evilMasterRef
          group by m.id
          """)
  List<Minion> findAllByEvilMaster(UUID evilMasterRef);
}
