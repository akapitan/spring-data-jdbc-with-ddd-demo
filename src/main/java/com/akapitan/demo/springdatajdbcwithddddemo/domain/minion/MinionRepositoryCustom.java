package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Minion.Link;
import java.util.Set;
import java.util.UUID;

public interface MinionRepositoryCustom {

  Minion findByIdV2(UUID id, Set<Link> links);
}
