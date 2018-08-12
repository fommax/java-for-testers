package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class ChangeOfPasswordTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() throws IOException, MessagingException {
    ensureUserPresent();
  }

  @Test
  public void testPasswordChange() throws IOException, MessagingException {
    UserData user = app.db().getUserData().iterator().next();

    UserData userWithNewPassword = new UserData()
            .withUsername(user.getUsername())
            .withEmail(user.getEmail())
            .withPasswordMantis("passwordNew").withPasswordMail("password");
    UserData adminUser = new UserData().withUsername("administrator").withPasswordMantis("root");
    app.james().initTelnetSession();
    app.james().drainEmail(userWithNewPassword);

    app.session().login(adminUser);
    app.user().resetPassword(user.getId());

    List<MailMessage> mailMessages = app.james().waitForMail(userWithNewPassword, 60000);
    String confirmationLink = findConfirmationLink(mailMessages, userWithNewPassword);
    app.registration().finishPasswordChange(confirmationLink, userWithNewPassword);

    HttpSession session = app.newSession();
    assertTrue(session.login(userWithNewPassword));
    assertTrue(session.isLoggedInAs(userWithNewPassword));
  }
}