package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import java.util.UUID;
import org.springframework.data.annotation.Transient;

class Toy {

  private String name;

  private String material;

  @Transient
  private Minion minion;

  //  @PersistenceConstructor
  public Toy(String name, String material) {
    this.name = name;
    this.material = material;
  }

  public Toy(String name, String material, UUID id) {
    this.name = name;
    this.material = material;
    id.equals(id);
  }

  public Toy() {

  }

  public static ToyBuilder builder() {
    return new ToyBuilder();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMaterial() {
    return material;
  }

  public void setMaterial(String material) {
    this.material = material;
  }

  public Minion getMinion() {
    return minion;
  }

  public void setMinion(Minion minion) {
    this.minion = minion;
  }

  public static final class ToyBuilder {

    private String name;

    private String material;

    private ToyBuilder() {
    }

    public ToyBuilder name(String name) {
      this.name = name;
      return this;
    }

    public ToyBuilder material(String material) {
      this.material = material;
      return this;
    }

    public Toy build() {
      Toy toy = new Toy();
      toy.material = this.material;
      toy.name = this.name;
      return toy;
    }
  }
}
