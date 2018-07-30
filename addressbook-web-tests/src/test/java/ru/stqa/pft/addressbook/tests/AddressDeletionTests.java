package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class AddressDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().addresses().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new AddressData()
                    .withFirstname("Alexander").withLastname("Brooks").withAddress("Huebscherstrasse 9")
                    .withHomeNumber("62-49-58").withMobilePhoneNumber("89518392390").withWorkPhoneNumber("02")
                    .withEmail("cold_soviet_steel@yahoo.com").withSecond_email("asoulyetunborn@gmail.com")
                    .withThird_email("aoulyetunborn@gmail.com").withSecond_address("Fellenbergstrasse 5"), true);
        }
    }



    @Test
    public void testAddressDeletion() {
        app.goTo().homePage();
        Addresses before = app.db().addresses();
        AddressData deletedAddress = before.iterator().next();
        app.contact().delete(deletedAddress);
        assertEquals(app.contact().count(), before.size() - 1);
        Addresses after = app.db().addresses();
        assertThat(after, equalTo(before.without(deletedAddress)));
        verifyAddressListInUI();
    }



}
