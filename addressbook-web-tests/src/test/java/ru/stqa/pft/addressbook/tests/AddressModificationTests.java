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
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new AddressData()
              .withFirstname("Alexander").withLastname("Brooks").withAddress("Huebscherstrasse 9")
              .withHomeNumber("62-49-58").withMobilePhoneNumber("89518392390").withWorkPhoneNumber("02")
              .withEmail("cold_soviet_steel@yahoo.com").withSecond_email("asoulyetunborn@gmail.com")
              .withThird_email("aoulyetunborn@gmail.com").withSecond_address("Fellenbergstrasse 5"), true);
    }
  }

  @Test
  public void testAddressModification() {
    Addresses before = app.contact().all();
    AddressData modifiedAddress = before.iterator().next();
    AddressData address = new AddressData().withId(modifiedAddress.getId()).withFirstname("Alexander").withLastname("Brux");
    app.contact().modify(address);
    assertEquals(app.contact().count(), before.size());
    Addresses after = app.contact().all();
    assertThat(after, equalTo(before.without(modifiedAddress).withAdded(address)));
  }

  @Test
  public void testNumbersDataConsistency() {
    app.goTo().homePage();
    AddressData address = app.contact().all().iterator().next();
    AddressData addressInfoFromEditForm = app.contact().infoFromEditForm(address);

    //assertThat(address.getHomeNumber(), equalTo(cleaned(addressInfoFromEditForm.getHomeNumber())));
    //assertThat(address.getMobilePhoneNumber(), equalTo(cleaned(addressInfoFromEditForm.getMobilePhoneNumber())));
    assertThat(address.getAllPhones(), equalTo(mergePhones(addressInfoFromEditForm)));
  }

  @Test
  public void testAddressDataConsistency() {
    app.goTo().homePage();
    AddressData address = app.contact().all().iterator().next();
    AddressData addressInfoFromEditForm = app.contact().infoFromEditForm(address);
    assertThat(address.getAllAddresses(), equalTo(mergeAddresses(addressInfoFromEditForm)));
  }

  @Test
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
