package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AddressModificationTests extends TestBase {

  @Test
  public void testAddressModification() {
    app.getNavigationHelper().goToHomePage();
    if (! app.getContactHelper().isThereAnAddress()) {
      app.getContactHelper().createAddress(new AddressData("Alexander", "Brooks", "Huebscherstrasse 9", "62-49-58", "89518392390", "cold_soviet_steel@yahoo.com", "asoulyetunborn@gmail.com", "Fellenbergstrasse 5", null), true);
    }
    List<AddressData> before = app.getContactHelper().getAddressList();
    app.getContactHelper().selectAddress();
    app.getContactHelper().initAddressModification(before.size() - 1);
    AddressData address = new AddressData(before.get(before.size() - 1).getId(), "Alexander", "Brux", null, null, null, null, null, null, null);
    app.getContactHelper().fillAddressForm(address, false);
    app.getContactHelper().submitAddressModification();
    app.getContactHelper().returnToHomePage();
    List<AddressData> after = app.getContactHelper().getAddressList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1);
    before.add(address);
    Comparator<? super AddressData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);

  }
}
