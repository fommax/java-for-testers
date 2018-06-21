package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.AddressData;

public class ContactHelper extends BaseHelper {

  public ContactHelper(FirefoxDriver wd) {
    super(wd);
  }

  public void initAddressCreation() {
    click(By.linkText("add new"));
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public void sumbitAddressCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillAddressForm(AddressData addressData) {
    type(By.name("firstname"), addressData.getFirstname());
    type(By.name("lastname"), addressData.getLastname());
    type(By.name("address"), addressData.getAddress());
    type(By.name("home"), addressData.getHomeNumber());
    type(By.name("mobile"), addressData.getMobilePhoneNumber());
    type(By.name("email"), addressData.getEmail());
    type(By.name("email2"), addressData.getSecond_email());
    type(By.name("address2"), addressData.getSecond_address());
  }

  public void deleteSelectedAddress() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void selectAddress() {
    click(By.name("selected[]"));
  }

  public void initAddressModification() {
    click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void submitAddressModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }
}
