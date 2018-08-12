package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends  TestBase {

  @Test
  public void testLogin() throws IOException {
    UserData adminUser = new UserData().withUsername("administrator").withPasswordMantis("root");
    HttpSession session = app.newSession();
    assertTrue(session.login(adminUser));
    assertTrue(session.isLoggedInAs(adminUser));
  }
}
