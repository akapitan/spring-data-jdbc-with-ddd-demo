package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import java.util.UUID;
import org.springframework.data.repository.Repository;

public interface MinionRepository extends Repository<Minion, UUID> {

  Minion save(Minion minion);

  Minion findById(UUID id);
}
