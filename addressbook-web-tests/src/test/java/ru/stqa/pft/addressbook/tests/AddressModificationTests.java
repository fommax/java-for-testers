package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

public class AddressModificationTests extends TestBase {

  @Test
  public void testAddressModification() {
    app.getNavigationHelper().goToHomePage();
    int before = app.getContactHelper().getAddressCount();
    if (! app.getContactHelper().isThereAnAddress()) {
      app.getContactHelper().createAddress(new AddressData("Alexander", "Brooks", "Huebscherstrasse 9", "62-49-58", "89518392390", "cold_soviet_steel@yahoo.com", "asoulyetunborn@gmail.com", "Fellenbergstrasse 5", null), true);
    }
    app.getContactHelper().selectAddress(0);
    app.getContactHelper().initAddressModification();
    app.getContactHelper().fillAddressForm(new AddressData("Alexander", "Brux", "Huebscherstrasse 12", "62-49-58", "89518392390", "cold_soviet_steel@yahoo.com", "asoulyetunborn@gmail.com", "Fellenbergstrasse 5", null), false);
    app.getContactHelper().submitAddressModification();
    app.getContactHelper().returnToHomePage();
    int after = app.getContactHelper().getAddressCount();
    Assert.assertEquals(after, before);
  }
}
