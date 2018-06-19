package ru.stqa.pft.addressbook;

public class AddressData {
  private final String firstname;
  private final String lastname;
  private final String address;
  private final String homeNumber;
  private final String mobilePhoneNumber;
  private final String email;
  private final String second_email;
  private final String second_address;

  public AddressData(String firstname, String lastname, String Address, String homeNumber, String mobilePhoneNumber, String email, String second_email, String second_address) {
    this.firstname = firstname;
    this.lastname = lastname;
    address = Address;
    this.homeNumber = homeNumber;
    this.mobilePhoneNumber = mobilePhoneNumber;
    this.email = email;
    this.second_email = second_email;
    this.second_address = second_address;
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
}
