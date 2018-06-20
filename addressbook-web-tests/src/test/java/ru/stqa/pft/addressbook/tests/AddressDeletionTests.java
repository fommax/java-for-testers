package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class AddressDeletionTests extends TestBase {
    

    
    @Test
    public void testAddressDeletion() {
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().selectAddress();
        app.getContactHelper().deleteSelectedAddress();
        app.getContactHelper().returnToHomePage();
    }

}
