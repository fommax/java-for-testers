package ru.stqa.pft.addressbook.model;

import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Objects;

public class AddressData {
  private final String firstname;
  private final String lastname;
  private final String address;
  private final String homeNumber;
  private final String mobilePhoneNumber;
  private final String email;
  private final String second_email;
  private final String second_address;
  private String group;

  public AddressData(String firstname, String lastname, String Address, String homeNumber, String mobilePhoneNumber, String email, String second_email, String second_address, String group) {
    this.firstname = firstname;
    this.lastname = lastname;
    address = Address;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddressData that = (AddressData) o;
    return Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(address, that.address);
  }

  @Override
  public int hashCode() {

    return Objects.hash(firstname, lastname, address);
  }

  @Override
  public String toString() {
    return "AddressData{" +
            "firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", address='" + address + '\'' +
            '}';
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
}
