package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

import java.util.Set;

public class AddressModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new AddressData()
              .withFirstname("Alexander").withLastname("Brooks").withAddress("Huebscherstrasse 9")
              .withHomeNumber("62-49-58").withMobilePhoneNumber("89518392390")
              .withEmail("cold_soviet_steel@yahoo.com").withSecond_email("asoulyetunborn@gmail.com")
              .withSecond_address("Fellenbergstrasse 5"), true);
    }
  }

  @Test
  public void testAddressModification() {
    Set<AddressData> before = app.contact().all();
    AddressData modifiedAddress = before.iterator().next();
    AddressData address = new AddressData().withId(modifiedAddress.getId()).withFirstname("Alexander").withLastname("Brux");
    int index = before.size() - 1;
    app.contact().modify(address, index);
    Set<AddressData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedAddress);
    before.add(address);
    /*Comparator<? super AddressData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);*/
    Assert.assertEquals(before, after);

  }


}
