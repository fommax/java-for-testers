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

  @Test
  public void testAddressAdding() {
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
}
