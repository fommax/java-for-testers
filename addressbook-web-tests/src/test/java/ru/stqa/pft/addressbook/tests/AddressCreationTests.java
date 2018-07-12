package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.*;

public class AddressCreationTests extends TestBase{

    @Test
    public void testAddressCreation() {
        app.goTo().homePage();
        Addresses before = app.contact().all();
        AddressData address = new AddressData().withFirstname("Alexander").withLastname("Brooks");
        app.contact().create(address, true);
        Addresses after = app.contact().all();
        assertEquals(after.size(), before.size() + 1);

        assertThat(after, equalTo(before.withAdded(address.withId(after.stream().mapToInt((a) -> a.getId()).max().getAsInt()))));

    }

}
