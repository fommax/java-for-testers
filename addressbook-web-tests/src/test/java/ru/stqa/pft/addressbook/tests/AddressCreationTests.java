package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;

import java.util.Set;

public class AddressCreationTests extends TestBase{

    @Test
    public void testAddressCreation() {
        app.goTo().homePage();
        Set<AddressData> before = app.contact().all();
        AddressData address = new AddressData().withFirstname("Alexander").withLastname("Brooks");
        app.contact().create(address, true);
        Set<AddressData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size() + 1);

        /*int max = 0;
        for (AddressData a : after) {
            if (a.getId() > max) {
                max = a.getId();
            }
        }*/
        int max1 = after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
        address.withId(after.stream().mapToInt((a) -> a.getId()).max().getAsInt());
        before.add(address);
        /*Comparator<? super AddressData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);*/
        Assert.assertEquals(before, after);

        /*address.setId(max);
        before.add(address);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after));*/

    }

}
