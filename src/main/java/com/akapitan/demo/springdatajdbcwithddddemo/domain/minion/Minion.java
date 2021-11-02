package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.person.Person;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.UUID;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public class Minion extends AggregateRoot {

  private String name;

  private AggregateReference<Person, UUID> evilMaster;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AggregateReference<Person, UUID> getEvilMaster() {
    return evilMaster;
  }

  public void setEvilMaster(
      AggregateReference<Person, UUID> evilMaster) {
    this.evilMaster = evilMaster;
  }

  public static MinionBuilder builder() {
    return new MinionBuilder();
  }

  static final class MinionBuilder {
    private UUID id;

    private String name;

    private AggregateReference<Person, UUID> evilMaster;

    private MinionBuilder() {
    }

    public MinionBuilder id(UUID id) {
      this.id = id;
      return this;
    }

    public MinionBuilder name(String name) {
      this.name = name;
      return this;
    }

    public MinionBuilder evilMaster(UUID evilMasterRef) {
      this.evilMaster = AggregateReference.to(evilMasterRef);
      return this;
    }

    public Minion build() {
      Minion minion = new Minion();
      minion.setId( this.id);
      minion.name = this.name;
      minion.evilMaster = this.evilMaster;
      return minion;
    }
  }
}
