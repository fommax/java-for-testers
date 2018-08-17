package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.AddressData;
import ru.stqa.pft.addressbook.model.Addresses;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

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
      Set<GroupData> groupsBefore = satisfyingAddress.getGroups(); //получаем группы контакта до удаления контакта из одной из групп
      GroupData groupToRemoveFrom = satisfyingAddress.getGroups().iterator().next();
      app.contact().removeFromGroup(satisfyingAddress, groupToRemoveFrom);
      AddressData modifiedAddress = satisfyingAddress.fromGroup(groupToRemoveFrom);
      Addresses after = app.db().addresses();

      Set<GroupData> expectedGroupsAfter = ((Groups) groupsBefore).without(groupToRemoveFrom); //инициализируем желаемый (не обязательно действительный) набор групп контакта
      assertThat(satisfyingAddress.getGroups(), equalTo(expectedGroupsAfter)); //сравниваем желаемый и действительный наборы групп контакта

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
