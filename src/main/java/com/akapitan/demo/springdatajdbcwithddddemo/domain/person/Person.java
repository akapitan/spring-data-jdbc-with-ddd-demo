package com.akapitan.demo.springdatajdbcwithddddemo.domain.person;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.UUID;

public class Person extends AggregateRoot {

  private String name;
  private String lastname;

  public Person(PersonBuilder personBuilder) {
    this.setId(personBuilder.id);
    this.setName(personBuilder.name);
    this.setLastname(personBuilder.lastname);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public static PersonBuilder builder() {
    return new PersonBuilder();
  }

  public static final class PersonBuilder {

    public UUID id;
    private String name;

    private String lastname;

    private PersonBuilder() {
    }

    public PersonBuilder name(String name) {
      this.name = name;
      return this;
    }

    public PersonBuilder lastname(String lastname) {
      this.lastname = lastname;
      return this;
    }

    public PersonBuilder id(UUID id) {
      this.id = id;
      return this;
    }

    public Person build() {
      return new Person(this);
    }
  }
}
