package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
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
    type(By.name("address"), addressData.getAddress());
    type(By.name("home"), addressData.getHomeNumber());
    type(By.name("mobile"), addressData.getMobilePhoneNumber());
    type(By.name("email"), addressData.getEmail());
    type(By.name("email2"), addressData.getSecond_email());
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

  public void initAddressModification(int index) {
    click(By.xpath("//table[@id='maintable']/tbody/tr[" + (index + 2) + "]/td[8]/a/img"));
  }

  public void submitAddressModification() {
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }

  public void create(AddressData address, boolean creation) {
    initAddressCreation();
    fillAddressForm(address, true);
    sumbitAddressCreation();
    returnToHomePage();
  }

  public void modify(AddressData address, int index) {
    selectAddress();
    initAddressModification(index);
    fillAddressForm(address, false);
    submitAddressModification();
    returnToHomePage();
  }

  public boolean isThereAnAddress() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getAddressCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void delete(int index) {
    selectAddress(index);
    deleteSelectedAddress();
    returnToHomePage();
  }

  public List<AddressData> list() {
    List<AddressData> addresses = new ArrayList<AddressData>();
    List<WebElement> rows = wd.findElements(By.cssSelector("tr[name=\"entry\"]"));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      AddressData address = new AddressData().withId(id).withFirstname(firstname).withLastname(lastname);
      addresses.add(address);
    }
    return addresses;
  }

}
