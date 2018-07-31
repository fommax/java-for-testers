package ru.stqa.pft.addressbook.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;
import ru.stqa.pft.addressbook.tests.TestBase;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("address")
@Entity
@Table(name = "addressbook")
public class AddressData {



  @XStreamOmitField
  @Id
  @Column (name = "id")
  private int id = Integer.MAX_VALUE;

  @Column (name = "firstname")
  private String firstname;

  @Column (name = "lastname")
  private String lastname;

  @Column (name = "address")
  @Type (type = "text")
  private String address;

  @Column (name = "home")
  @Type (type = "text")
  private String homeNumber;

  @Column (name = "mobile")
  @Type (type = "text")
  private String mobilePhoneNumber;

  @Column (name = "work")
  @Type (type = "text")
  private String workPhoneNumber;

  @Column (name = "email")
  @Type (type = "text")
  private String email;

  @Column (name = "email2")
  @Type (type = "text")
  private String second_email;

  @Column (name = "email3")
  @Type (type = "text")
  private String third_email;

  @Column (name = "address2")
  @Type (type = "text")
  private String second_address;


  @ManyToMany (fetch = FetchType.EAGER)
  @JoinTable (name = "address_in_groups", joinColumns = @JoinColumn (name = "id"), inverseJoinColumns = @JoinColumn (name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();

  @Transient
  private String allPhones;

  @Transient
  private String allEmails;

  @Transient
  private String allAddresses;

  @Column (name = "photo")
  @Type (type = "text")
  private String photo;

  public File getPhoto() {
    return new File(photo);
  }

  public AddressData withPhoto(File photo) {
    this.photo = photo.getPath();
    return this;
  }


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

  public AddressData withWorkPhoneNumber(String workPhoneNumber) {
    this.workPhoneNumber = workPhoneNumber;
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

  public AddressData withThird_email(String third_email) {
    this.third_email = third_email;
    return this;
  }

  public AddressData withSecond_address(String second_address) {
    this.second_address = second_address;
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
            ", workPhoneNumber='" + workPhoneNumber + '\'' +
            ", email='" + email + '\'' +
            ", second_email='" + second_email + '\'' +
            ", third_email='" + third_email + '\'' +
            ", second_address='" + second_address + '\'' +
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

  public String getWorkPhoneNumber() {
    return workPhoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public String getSecond_email() {
    return second_email;
  }

  public String getThird_email() {
    return third_email;
  }

  public String getSecond_address() {
    return second_address;
  }

  public Groups getGroups() {
    return new Groups(groups);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddressData that = (AddressData) o;
    return id == that.id &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(address, that.address) &&
            Objects.equals(homeNumber, that.homeNumber) &&
            Objects.equals(mobilePhoneNumber, that.mobilePhoneNumber) &&
            Objects.equals(workPhoneNumber, that.workPhoneNumber) &&
            Objects.equals(email, that.email) &&
            Objects.equals(second_email, that.second_email) &&
            Objects.equals(third_email, that.third_email);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, firstname, lastname, address, homeNumber, mobilePhoneNumber, workPhoneNumber, email, second_email, third_email);
  }

  public String getPhotoPath() {
    return photo;

  }

  public AddressData inGroup(GroupData group) {
    groups.add(group);
    return this;
  }
}
