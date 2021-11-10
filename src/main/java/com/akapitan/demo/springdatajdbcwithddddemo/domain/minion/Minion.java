package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.minion.Toy.ToyBuilder;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.person.Person;
import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public class Minion extends AggregateRoot {

  private String name;
  private Description description = new Description();
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

  public void addAppearance(String key, String value) {
    this.description.appearance.put(key, value);
  }

  public void addPersonality(String key, String value) {
    this.description.personality.put(key, value);
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

  public void setDescription(Description description) {
    this.description = description;
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

    public Minion build() {
      return new Minion(this);
    }
  }

  public enum Link {
    TOYS("(select coalesce(json_agg(t.*), '[]'::json) from toy t where t.minion = m.id) as toys"),
    COLORS(
        "(select coalesce(json_agg(c.*), '[]'::json) from color c where c.minion = m.id) as colors"),
    DESCRIPTION("description");

    public String select;

    Link(String select) {
      this.select = select;
    }

    public String getSelect() {
      return select;
    }

    public static String setSelect(Set<Link> links, String select) {
      return fun.apply(links, select);
    }

    private static final BiFunction<Set<Link>, String, String> fun = (link, select) -> {

      String result = select;

      for (Link link1 : link) {
        result = result.concat(", ").concat(link1.getSelect());
      }
      return result;
    };


  }
}
