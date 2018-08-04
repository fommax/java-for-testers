package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddressRemovalFromGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
      ensureGroupPresent();
      ensureAddressPresent();
      ensureAddressPresentInGroup();
      app.goTo().homePage();
    }

    @Test
    public void testAddressRemovalFromGroup() {
      Addresses before = app.db().addresses();
      AddressData satisfyingAddress = findAnAddressWithAGroup();
      Addresses beforeWithoutSatisfying = before.without(satisfyingAddress);
      GroupData groupToRemoveFrom = satisfyingAddress.getGroups().iterator().next();
      app.contact().removeFromGroup(satisfyingAddress, groupToRemoveFrom);
      AddressData modifiedAddress = satisfyingAddress.fromGroup(groupToRemoveFrom);
      Addresses after = app.db().addresses();

      assertThat(after.size(), equalTo(before.size()));
      assertThat(after, equalTo(beforeWithoutSatisfying.withAdded(modifiedAddress)));
    }

    public AddressData findAnAddressWithAGroup() {
      Addresses addresses = app.db().addresses();
      AddressData satisfyingAddress = null;
      for (AddressData address : addresses) {
        if (address.getGroups().size() > 0) {
          satisfyingAddress = address;
          break;
        }
      }
      return satisfyingAddress;
    }


}
