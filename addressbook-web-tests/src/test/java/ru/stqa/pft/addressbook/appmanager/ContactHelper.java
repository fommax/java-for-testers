package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;

import java.util.List;

public class ContactHelper extends BaseHelper {

  public ContactHelper(WebDriver wd) {
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

  public void fillAddressForm(AddressData addressData, boolean creation) {
    type(By.name("firstname"), addressData.getFirstname());
    type(By.name("lastname"), addressData.getLastname());
    attach(By.name("photo"), addressData.getPhoto());
    type(By.name("address"), addressData.getAddress());
    type(By.name("home"), addressData.getHomeNumber());
    type(By.name("mobile"), addressData.getMobilePhoneNumber());
    type(By.name("work"), addressData.getWorkPhoneNumber());
    type(By.name("email"), addressData.getEmail());
    type(By.name("email2"), addressData.getSecond_email());
    type(By.name("email3"), addressData.getThird_email());
    type(By.name("address2"), addressData.getSecond_address());

    if (! (addressData.getGroup() == null)) {
      if (creation) {
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(addressData.getGroup());
      } else {
        Assert.assertFalse(isElementPresent(By.name("new_group")));
      }
    }
  }

  public void deleteSelectedAddress() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    wd.switchTo().alert().accept();
  }

  public void selectAddress() {
    click(By.name("selected[]"));
  }

  public void selectAddress(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectAddressById(int id) {
    wd.findElement(By.cssSelector("input[value = '" + id + "']")).click();
  }

  public void initAddressModification(int id) {
    /* WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();*/

    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void submitAddressModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public void create(AddressData address, boolean creation) {
    initAddressCreation();
    fillAddressForm(address, true);
    sumbitAddressCreation();
    addressCache = null;
    returnToHomePage();
  }

  public void modify(AddressData address) {
    selectAddress();
    initAddressModification(address.getId());
    fillAddressForm(address, false);
    submitAddressModification();
    addressCache = null;
    returnToHomePage();
  }

  public boolean isThereAnAddress() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void delete(int index) {
    selectAddress(index);
    deleteSelectedAddress();
    returnToHomePage();
  }
  public void delete(AddressData address) {
    selectAddressById(address.getId());
    deleteSelectedAddress();
    addressCache = null;
    returnToHomePage();
  }

  private Addresses addressCache = null;

  public Addresses all() {
    if (addressCache != null) {
      return new Addresses(addressCache);
    }
    addressCache = new Addresses();
    List<WebElement> rows = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      //String[] phones = cells.get(5).getText().split("\n"); разрез строк
      String allPhones = cells.get(5).getText(); //склейка строк
      String allEmails = cells.get(4).getText();
      String allAddresses = cells.get(3).getText().replaceAll("\\s+", "");

      /*AddressData address = new AddressData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withHomeNumber(phones[0]).withMobilePhoneNumber(phones[1]);*/
      AddressData address = new AddressData().withId(id).withFirstname(firstname).withLastname(lastname)
              .withAllPhones(allPhones).withAllEmails(allEmails).withAllAddresses(allAddresses);
      addressCache.add(address);
    }
    return new Addresses(addressCache);
  }

  public AddressData infoFromEditForm(AddressData address) {
    initAddressModification(address.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String thisaddress = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new AddressData().withId(address.getId()).withFirstname(firstname)
            .withLastname(lastname).withHomeNumber(home).withMobilePhoneNumber(mobile).withWorkPhoneNumber(work)
            .withEmail(email1).withSecond_email(email2).withThird_email(email3).withAddress(thisaddress);
  }

}
