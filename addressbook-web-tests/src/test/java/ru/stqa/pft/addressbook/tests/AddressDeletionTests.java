package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

import java.util.Set;

public class AddressDeletionTests extends TestBase {

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
    public void testAddressDeletion() {

        Set<AddressData> before = app.contact().all();
        AddressData deletedAddress = before.iterator().next();
        int index = before.size() - 1;
        app.contact().delete(deletedAddress);
        Set<AddressData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(deletedAddress);
        Assert.assertEquals(before, after);
    }



}
