package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Color.ColorBuilder;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Toy.ToyBuilder;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.person.Person;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public class Minion extends AggregateRoot {

  private String name;
  private AggregateReference<Person, UUID> evilMaster;
  private final Set<Toy> toys = new HashSet<>();
  private final Set<Color> colors = new HashSet<>();

  public Minion(MinionBuilder minionBuilder) {
    this.setId(minionBuilder.id);
    this.setName(minionBuilder.name);
    this.setEvilMaster(minionBuilder.evilMaster);
    minionBuilder.toys.forEach(this::addToy);
    minionBuilder.colors.forEach(this::addColor);
  }

  @PersistenceConstructor
  public Minion(Collection<Toy> toys, Collection<Color> colors) {
    toys.forEach(this::addToy);
    colors.forEach(this::addColor);
  }

  public Minion(String name, AggregateReference<Person, UUID> evilMaster, Collection<Toy> toys) {
    this.name = name;
    this.evilMaster = evilMaster;

    if (Objects.nonNull(toys)) {
      toys.forEach(this::addToy);
    }
  }

  public static MinionBuilder builder() {
    return new MinionBuilder();
  }

  public void addColor(Color color) {
    color.setMinion(this);
    colors.add(color);
  }

  public void addToy(Toy toy) {
    toy.setMinion(this);
    this.toys.add(toy);
  }

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

  public Set<Toy> getToys() {
    return toys;
  }

  static final class MinionBuilder {

    private final Set<Toy> toys = new HashSet<>();
    public final Set<Color> colors = new HashSet<>();
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

    public MinionBuilder addToy(ToyBuilder toyBuilder) {
      this.toys.add(toyBuilder.build());
      return this;
    }
    public MinionBuilder addToy(ColorBuilder toyBuilder) {
      this.colors.add(toyBuilder.build());
      return this;
    }

    public Minion build() {
      return new Minion(this);
    }
  }
}
