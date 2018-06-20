package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

public class AddressCreationTests extends TestBase{

    @Test
    public void testAddressCreation() {
        app.goToHomePage();
        app.initAddressCreation();
        app.fillAddressForm(new AddressData("Alexander", "Brooks", "Huebscherstrasse 9", "62-49-58", "89518392390", "cold_soviet_steel@yahoo.com", "asoulyetunborn@gmail.com", "Fellenbergstrasse 5"));
        app.sumbitAddressCreation();
        app.returnToHomePage();
    }

}
