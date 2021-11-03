package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;


import static org.assertj.core.api.Assertions.assertThat;

import com.akapitan.demo.springdatajdbcwithddddemo.PostgreSqlContainerConfiguration;
import com.akapitan.demo.springdatajdbcwithddddemo.config.JdbcConfiguration;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.person.Person;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.person.PersonRepository;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({PostgreSqlContainerConfiguration.class, JdbcConfiguration.class})
class MinionRepositoryTest {

  @Autowired
  private MinionRepository repository;

  @Autowired
  private PersonRepository personRepository;

  @Test
  void saveMinion() {
    Minion ivo = Minion.builder()
        .name("Ivo")
        .build();
    repository.save(ivo);
    assertThat(ivo).isNotNull();

    Minion minionReloaded = repository.findById(ivo.getId());
    assertThat(minionReloaded).isNotNull();
  }

  @Test
  void minionAggregateReferenceToPerson() {

    Person gru = Person.builder()
        .name("Felonius")
        .lastname("Gru")
        .build();

    personRepository.save(gru);

    Minion ivo = Minion.builder()
        .name("Ivo")
        .evilMaster(gru.getId())
        .build();
    repository.save(ivo);
    assertThat(ivo).isNotNull().extracting(Minion::getEvilMaster).isNotNull().extracting(
        AggregateReference::getId).isEqualTo(gru.getId());

  }

  @Test
  void createMinionWithToys() {
    Person gru = Person.builder()
        .name("Felonius")
        .lastname("Gru")
        .build();

    personRepository.save(gru);

    Minion ivo = Minion.builder()
        .name("Ivo")
        .evilMaster(gru.getId())
        .addToy(Toy.builder().name("Pistol").material("metal"))
        .addToy(Toy.builder().name("Spear").material("wood"))
        .build();

    repository.save(ivo);
    assertThat(ivo).isNotNull().extracting(Minion::getToys).extracting(Set::size).isEqualTo(2);

    //delete toy
    ivo.getToys().removeIf(x -> x.getName().equals("Pistol"));
    Minion minionReloaded = repository.save(ivo);
    System.out.println(minionReloaded);
  }

  @Test
  void createMinionDeleteToys() {
    Person gru = Person.builder()
        .name("Felonius")
        .lastname("Gru")
        .build();

    personRepository.save(gru);

    Minion ivo = Minion.builder()
        .name("Ivo")
        .evilMaster(gru.getId())
        .addToy(Toy.builder().name("Pistol").material("metal"))
        .addToy(Toy.builder().name("Spear").material("wood"))
        .build();

    repository.save(ivo);
    assertThat(ivo).isNotNull().extracting(Minion::getToys).extracting(Set::size).isEqualTo(2);

    ivo.getToys().add(Toy.builder()
        .name("Stick")
        .material("WoodenStick")
        .build());

    repository.save(ivo);
  }

}