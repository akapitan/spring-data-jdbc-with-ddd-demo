package com.akapitan.demo.springdatajdbcwithddddemo.domain.person;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Minion;
import java.util.UUID;
import org.springframework.data.repository.Repository;

public interface PersonRepository extends Repository<Person, UUID> {

  void save(Person minion);

  Person findById(UUID id);
}
