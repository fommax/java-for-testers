package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;

import java.io.File;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.*;

public class AddressCreationTests extends TestBase{

    @Test
    public void testAddressCreation() {
        app.goTo().homePage();
        Addresses before = app.contact().all();
        File photo = new File("src/test/resources/che.jpg");
        AddressData address = new AddressData().withFirstname("Alexander").withLastname("Brooks")
                .withPhoto(photo);
        app.contact().create(address, true);
        assertEquals(app.contact().count(), before.size() + 1);
        Addresses after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(address.withId(after.stream().mapToInt((a) -> a.getId()).max().getAsInt()))));

    }

}
