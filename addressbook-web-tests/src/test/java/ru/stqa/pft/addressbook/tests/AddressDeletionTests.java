package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

import java.util.List;

public class AddressDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new AddressData()
                    .withFirstname("Alexander").withLastname("Brooks").withAddress("Huebscherstrasse 9")
                    .withHomeNumber("62-49-58").withMobilePhoneNumber("89518392390")
                    .withEmail("cold_soviet_steel@yahoo.com").withSecond_email("asoulyetunborn@gmail.com")
                    .withSecond_address("Fellenbergstrasse 5"), true);
        }
    }



    @Test
    public void testAddressDeletion() {

        List<AddressData> before = app.contact().list();
        int index = before.size() - 1;
        app.contact().delete(index);
        List<AddressData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }



}
