package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

public class AddressDeletionTests extends TestBase {
    

    
    @Test
    public void testAddressDeletion() {
        app.getNavigationHelper().goToHomePage();
        if (! app.getContactHelper().isThereAnAddress()) {
            app.getContactHelper().createAddress(new AddressData("Alexander", "Brooks", "Huebscherstrasse 9", "62-49-58", "89518392390", "cold_soviet_steel@yahoo.com", "asoulyetunborn@gmail.com", "Fellenbergstrasse 5", null), true);
        }
        int before = app.getContactHelper().getAddressCount();
        app.getContactHelper().selectAddress(before - 1);
        app.getContactHelper().deleteSelectedAddress();
        app.getContactHelper().returnToHomePage();
        int after = app.getContactHelper().getAddressCount();
        Assert.assertEquals(after, before - 1);
    }

}
