package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AddressCreationTests extends TestBase{

    @Test
    public void testAddressCreation() {
        app.getNavigationHelper().goToHomePage();
        List<AddressData> before = app.getContactHelper().getAddressList();
        AddressData address = new AddressData("Alexander", "Brooks", null, null, null, null, null, null, null);
        app.getContactHelper().createAddress(address, true);
        List<AddressData> after = app.getContactHelper().getAddressList();
        Assert.assertEquals(after.size(), before.size() + 1);

        /*int max = 0;
        for (AddressData a : after) {
            if (a.getId() > max) {
                max = a.getId();
            }
        }*/
        int max1 = after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
        address.setId(max1);
        before.add(address);
        Comparator<? super AddressData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);

        /*address.setId(max);
        before.add(address);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after));*/

    }

}
