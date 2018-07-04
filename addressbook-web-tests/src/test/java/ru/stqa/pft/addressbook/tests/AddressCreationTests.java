package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

import java.util.List;

public class AddressCreationTests extends TestBase{

    @Test
    public void testAddressCreation() {
        app.getNavigationHelper().goToHomePage();
        List<AddressData> before = app.getContactHelper().getAddressList();
        app.getContactHelper().createAddress(new AddressData("Alexander", "Brooks", "Huebscherstrasse 9", "62-49-58", "89518392390", "cold_soviet_steel@yahoo.com", "asoulyetunborn@gmail.com", "Fellenbergstrasse 5", "test1"), true);
        List<AddressData> after = app.getContactHelper().getAddressList();
        Assert.assertEquals(after.size(), before.size() + 1);
    }

}
