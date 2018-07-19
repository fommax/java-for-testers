package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.*;

public class AddressCreationTests extends TestBase{


    @DataProvider
    public Iterator<Object[]> validAddresses() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/addresses.xml")));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(AddressData.class);
        List<AddressData> addresses = (List<AddressData>) xstream.fromXML(xml);
        return addresses.stream().map((a) -> new Object[] {a}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validAddresses")
    public void testAddressCreation(AddressData address) {
        app.goTo().homePage();
        Addresses before = app.contact().all();
        /*File photo = new File("src/test/resources/che.jpg");
        AddressData address = new AddressData().withFirstname("Alexander").withLastname("Brooks")
                .withPhoto(photo);*/
        app.contact().create(address, true);
        assertEquals(app.contact().count(), before.size() + 1);
        Addresses after = app.contact().all();
        assertThat(after, equalTo(before.withAdded(address.withId(after.stream().mapToInt((a) -> a.getId()).max().getAsInt()))));

    }

}
