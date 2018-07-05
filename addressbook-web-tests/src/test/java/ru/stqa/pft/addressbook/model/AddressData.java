package ru.stqa.pft.addressbook.model;

import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Objects;

public class AddressData {
  private int id;
  private final String firstname;
  private final String lastname;
  private final String address;
  private final String homeNumber;
  private final String mobilePhoneNumber;
  private final String email;
  private final String second_email;
  private final String second_address;
  private String group;

  @Override
  public String toString() {
    return "AddressData{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", address='" + address + '\'' +
            ", homeNumber='" + homeNumber + '\'' +
            ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
            ", email='" + email + '\'' +
            ", second_email='" + second_email + '\'' +
            ", second_address='" + second_address + '\'' +
            ", group='" + group + '\'' +
            '}';
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public AddressData(String firstname, String lastname, String Address, String homeNumber, String mobilePhoneNumber, String email, String second_email, String second_address, String group) {
    this.id = Integer.MAX_VALUE;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = Address;
    this.homeNumber = homeNumber;
    this.mobilePhoneNumber = mobilePhoneNumber;
    this.email = email;
    this.second_email = second_email;
    this.second_address = second_address;
    this.group = group;
  }

  public AddressData(int id, String firstname, String lastname, String Address, String homeNumber, String mobilePhoneNumber, String email, String second_email, String second_address, String group) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.address = Address;
    this.homeNumber = homeNumber;
    this.mobilePhoneNumber = mobilePhoneNumber;
    this.email = email;
    this.second_email = second_email;
    this.second_address = second_address;
    this.group = group;
  }
  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getAddress() {
    return address;
  }

  public String getHomeNumber() {
    return homeNumber;
  }

  public String getMobilePhoneNumber() {
    return mobilePhoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getSecond_email() {
    return second_email;
  }

  public String getSecond_address() {
    return second_address;
  }

  public String getGroup() {
    return group;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddressData that = (AddressData) o;
    return id == that.id &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, firstname, lastname);
  }
}
