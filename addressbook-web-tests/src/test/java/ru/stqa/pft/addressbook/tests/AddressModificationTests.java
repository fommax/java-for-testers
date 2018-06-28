package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

public class AddressModificationTests extends TestBase {

  @Test
  public void testAddressModification() {
    app.getNavigationHelper().goToHomePage();
    app.getContactHelper().selectAddress();
    app.getContactHelper().initAddressModification();
    app.getContactHelper().fillAddressForm(new AddressData("Alexander", "Brux", "Huebscherstrasse 12", "62-49-58", "89518392390", "cold_soviet_steel@yahoo.com", "asoulyetunborn@gmail.com", "Fellenbergstrasse 5", null), false);
    app.getContactHelper().submitAddressModification();
    app.getContactHelper().returnToHomePage();
  }
}
