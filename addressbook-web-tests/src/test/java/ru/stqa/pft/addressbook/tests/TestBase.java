package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {


  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));
  FirefoxDriver wd;

  public static boolean isAlertPresent(FirefoxDriver wd) {
      try {
          wd.switchTo().alert();
          return true;
      } catch (NoAlertPresentException e) {
          return false;
      }
  }

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }






  @AfterSuite
  public void tearDown() {
    app.stop();
  }


  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
              .collect(Collectors.toSet())));
    }
  }

  public void verifyAddressListInUI() {
    if (Boolean.getBoolean("verifyUI")) {
      Addresses dbAddresses = app.db().addresses();
      Addresses uiAddresses = app.contact().all();
      assertThat(uiAddresses, equalTo(dbAddresses.stream().map((a) -> new AddressData().withId(a.getId()).withFirstname(a.getFirstname())
                .withLastname(a.getLastname()).withAllAddresses(a.getAllAddresses()).withAllEmails(a.getAllEmails()).withAllPhones(a.getAllPhones()))
                .collect(Collectors.toSet())));
    }
  }
}
