package ru.stqa.pft.addressbook.model;

import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Objects;

public class AddressData {
  private int id = Integer.MAX_VALUE;
  private String firstname;

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

  private String lastname;
  private String address;
  private String homeNumber;
  private String mobilePhoneNumber;
  private String email;
  private String second_email;
  private String second_address;
  private String group;
  private String allPhones;
  private String allEmails;
  private String allAddresses;



  public AddressData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public AddressData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public AddressData withAllAddresses(String allAddresses) {
    this.allAddresses = allAddresses;
    return this;
  }


  public AddressData withId(int id) {
    this.id = id;
    return this;
  }

  public AddressData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public AddressData withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public AddressData withAddress(String address) {
    this.address = address;
    return this;
  }

  public AddressData withHomeNumber(String homeNumber) {
    this.homeNumber = homeNumber;
    return this;
  }

  public AddressData withMobilePhoneNumber(String mobilePhoneNumber) {
    this.mobilePhoneNumber = mobilePhoneNumber;
    return this;
  }

  public AddressData withEmail(String email) {
    this.email = email;
    return this;
  }

  public AddressData withSecond_email(String second_email) {
    this.second_email = second_email;
    return this;
  }

  public AddressData withSecond_address(String second_address) {
    this.second_address = second_address;
    return this;
  }

  public AddressData withGroup(String group) {
    this.group = group;
    return this;
  }



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

  public String getAllPhones() {
    return allPhones;
  }

  public String getAllAddresses() {
    return allAddresses;
  }

  public String getAllEmails() {
    return allEmails;
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

}
