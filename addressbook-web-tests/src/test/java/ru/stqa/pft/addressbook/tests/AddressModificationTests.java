package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class AddressModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
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
  public void testAddressModification() {
    app.goTo().homePage();
    Addresses before = app.db().addresses();
    AddressData modifiedAddress = before.iterator().next();
    AddressData address = new AddressData().withId(modifiedAddress.getId()).withFirstname("Alexander").withLastname("Brux")
            .withAddress("modHuebscherstrasse 9").withHomeNumber("mod62-49-58").withMobilePhoneNumber("mod89518392390")
            .withWorkPhoneNumber("mod02").withEmail("modcold_soviet_steel@yahoo.com").withSecond_email("modasoulyetunborn@gmail.com")
            .withThird_email("modaoulyetunborn@gmail.com").withSecond_address("modFellenbergstrasse 5");
    app.contact().modify(address);
    assertThat(app.contact().count(), equalTo(before.size()));
    Addresses after = app.db().addresses();
    assertThat(after, equalTo(before.without(modifiedAddress).withAdded(address)));
    verifyAddressListInUI();
  }

  @Test (enabled = false)
  public void testNumbersDataConsistency() {
    app.goTo().homePage();
    AddressData address = app.contact().all().iterator().next();
    AddressData addressInfoFromEditForm = app.contact().infoFromEditForm(address);

    //assertThat(address.getHomeNumber(), equalTo(cleaned(addressInfoFromEditForm.getHomeNumber())));
    //assertThat(address.getMobilePhoneNumber(), equalTo(cleaned(addressInfoFromEditForm.getMobilePhoneNumber())));
    assertThat(address.getAllPhones(), equalTo(mergePhones(addressInfoFromEditForm)));
  }

  @Test (enabled = false)
  public void testAddressDataConsistency() {
    app.goTo().homePage();
    AddressData address = app.contact().all().iterator().next();
    AddressData addressInfoFromEditForm = app.contact().infoFromEditForm(address);
    assertThat(address.getAllAddresses(), equalTo(mergeAddresses(addressInfoFromEditForm)));
  }

  @Test (enabled = false)
  public void testEmailDataConsistency() {
    app.goTo().homePage();
    AddressData address = app.contact().all().iterator().next();
    AddressData addressInfoFromEditForm = app.contact().infoFromEditForm(address);
    assertThat(address.getAllEmails(), equalTo(mergeEmails(addressInfoFromEditForm)));
  }

  private String mergePhones(AddressData address) {
    return Arrays.asList(address.getHomeNumber(), address.getMobilePhoneNumber(), address.getWorkPhoneNumber())
            .stream().filter((s) -> ! s.equals(""))
            .map(AddressModificationTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeAddresses(AddressData address) {
    return Arrays.asList(address.getAddress())
            .stream().filter((s) -> ! s.equals(""))
            .map(AddressModificationTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeEmails(AddressData address) {
    return Arrays.asList(address.getEmail(), address.getSecond_email(), address.getThird_email())
            .stream().filter((s) -> ! s.equals(""))
            .map(AddressModificationTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String object) {
    return object.replaceAll("\\s+", "").replaceAll("[-)/(]", "");
  }

}
