package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddressAdditionToGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
      ensureGroupPresent();
      ensureAddressPresent();
      ensureAddressWithNotAllGroupsPresent();
      app.goTo().homePage();
    }

    @Test
    public void testContactAddToGroup() {
      Addresses before = app.db().addresses();
      AddressData satisfyingAddress = findContactNotInEveryGroup();
      Addresses beforeWithoutSatisfying = before.without(satisfyingAddress);
      GroupData satisfyingGroup = findAGroupWithout(satisfyingAddress);
      app.contact().addToGroup(satisfyingAddress, satisfyingGroup);
      AddressData modifiedContact = satisfyingAddress.inGroup(satisfyingGroup);
      Addresses after = app.db().addresses();

      assertThat(after.size(), equalTo(before.size()));
      assertThat(after, equalTo(beforeWithoutSatisfying.withAdded(modifiedContact)));

    }


    private AddressData findContactNotInEveryGroup() {
      Groups groups = app.db().groups();
      Addresses addresses = app.db().addresses();
      AddressData satisfyingAddress = null;
      for (AddressData address : addresses) {
        if (address.getGroups().size() != groups.size()) {
          satisfyingAddress = address;
          break;
        }
      }
      return satisfyingAddress;
    }

    private GroupData findAGroupWithout(AddressData address) {
      Groups groups = app.db().groups();
      GroupData selectedGroup = null;
      for (GroupData group : groups) {
        if (!group.getAddresses().contains(address)) {
          selectedGroup = group;
          break;
        }
      }
      return selectedGroup;
    }
}
