package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

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
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/addresses.xml")))) {
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
    }

    @BeforeMethod  public void ensurePreconditionsGroups() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }


    @Test(dataProvider = "validAddresses")
    public void testAddressCreation(AddressData address) {
        Groups groups = app.db().groups();
        app.goTo().homePage();

        Addresses before = app.db().addresses();
        //File photo = new File("src/test/resources/che.jpg");.withPhoto(photo)

        AddressData newAddress = address.inGroup(groups.iterator().next());

        app.contact().create(newAddress, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Addresses after = app.db().addresses();
        assertThat(after, equalTo(before.withAdded(newAddress.withId(after.stream().mapToInt((a) -> a.getId()).max().getAsInt()))));
        verifyAddressListInUI();

    }

}
