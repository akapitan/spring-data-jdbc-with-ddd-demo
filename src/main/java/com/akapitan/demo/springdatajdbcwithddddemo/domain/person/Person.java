package com.akapitan.demo.springdatajdbcwithddddemo.domain.person;

import com.akapitan.demo.springdatajdbcwithddddemo.domain.shared.AggregateRoot;
import java.util.UUID;
import org.springframework.data.annotation.PersistenceConstructor;

public class Person extends AggregateRoot {

  private String name;
  private String lastname;

  private final Address address;

  @PersistenceConstructor
  public Person(UUID id, String name, String lastname,
      Address address) {
    setId(id);
    this.address = address;
    this.name = name;
    this.lastname = lastname;
  }

  public Person(PersonBuilder personBuilder) {
    this.address = personBuilder.address;
    this.setId(personBuilder.id);
    this.setName(personBuilder.name);
    this.setLastname(personBuilder.lastname);
  }

  public static PersonBuilder builder() {
    return new PersonBuilder();
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

  public Address getAddress() {
    return address;
  }

  public static final class PersonBuilder {

    public UUID id;
    public Address address;
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

    public PersonBuilder address(String street, String streetNumber, String city,
        String postalNumber) {
      this.address = new Address(street, streetNumber, city, postalNumber);
      return this;
    }

    public Person build() {
      return new Person(this);
    }
  }
}
