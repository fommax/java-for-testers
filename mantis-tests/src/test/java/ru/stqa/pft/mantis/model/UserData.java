package ru.stqa.pft.mantis.model;

import javax.persistence.*;

@Entity
@Table(name = "mantis_user_table")
public class UserData {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "username", columnDefinition = "LONGTEXT")
  private String username;

  @Column(name = "email")
  private String email;

  @Transient
  private String passwordMail;

  @Transient
  private String passwordMantis;

  public UserData(int id, String username, String email, String passwordMail, String passwordMantis) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.passwordMail = passwordMail;
    this.passwordMantis = passwordMantis;
  }

  public UserData(){}
  public int getId() {
    return id;
  }

  public UserData withId(int id) {
    this.id = id;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public UserData withUsername(String username) {
    this.username = username;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public UserData withEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPasswordMail() {
    return passwordMail;
  }

  public UserData withPasswordMail(String password) {
    this.passwordMail = password;
    return this;
  }

  public String getPasswordMantis() {
    return passwordMantis;
  }

  public UserData withPasswordMantis(String passwordMantis) {
    this.passwordMantis = passwordMantis;
    return this;
  }

}