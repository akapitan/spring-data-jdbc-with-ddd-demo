package com.akapitan.demo.springdatajdbcwithddddemo.domain.minion;

import org.springframework.data.annotation.Transient;

class Color {

  private String name;

  @Transient
  private Minion minion;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Minion getMinion() {
    return minion;
  }

  public static ColorBuilder builder() {
    return new ColorBuilder();
  }

  public void setMinion(Minion minion) {
    this.minion = minion;
  }


  public static final class ColorBuilder {
    private String name;

    private Minion minion;

    private ColorBuilder() {
    }

    public ColorBuilder name(String name) {
      this.name = name;
      return this;
    }

    public ColorBuilder minion(Minion minion) {
      this.minion = minion;
      return this;
    }

    public Color build() {
      Color color = new Color();
      color.setName(name);
      color.setMinion(minion);
      return color;
    }
  }
}
