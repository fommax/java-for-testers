package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.*;

public class AddressInGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    if (app.db().addresses().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new AddressData()
              .withFirstname("Alexander").withLastname("Brooks").withAddress("Huebscherstrasse 9")
              .withHomeNumber("62-49-58").withMobilePhoneNumber("89518392390").withWorkPhoneNumber("02")
              .withEmail("cold_soviet_steel@yahoo.com").withSecond_email("asoulyetunborn@gmail.com")
              .withThird_email("aoulyetunborn@gmail.com").withSecond_address("Fellenbergstrasse 5"), true);
    }
  }

  @Test
  public void testAddressAddition() {
    app.goTo().homePage();
    Addresses before = app.db().addresses();
    AddressData addressToAdd = before.iterator().next();
    app.contact().addToGroup(addressToAdd);
    assertThat(app.contact().count(), equalTo(before.size()));
    Addresses after = app.db().addresses();
    assertThat(after, equalTo(before));
    verifyAddressListInUI();
  }

  @Test
  public void testAddressRemoval() {
    app.goTo().homePage();
    Addresses before = app.db().addresses();
    app.contact().groupByGroups();
    AddressData addressToRemove = before.iterator().next();
    app.contact().removeFromGroup(addressToRemove);
    //assertThat(app.contact().count(), equalTo(before.size()));
    Addresses after = app.db().addresses();
    assertThat(after, equalTo(before));
    verifyAddressListInUI();
  }
}
