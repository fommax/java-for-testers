package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase{

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();
    UserData userData = new UserData()
            .withUsername(String.format("user%s", now))
            .withEmail(String.format("user%s@localhost", now))
            .withPasswordMail("password").withPasswordMantis("password1");
    app.james().createUser(userData);
    app.registration().start(userData);

    List<MailMessage> mailMessages = app.james().waitForMail(userData, 60000);
    String confirmationLink = findConfirmationLink(mailMessages, userData);
    app.registration().finish(confirmationLink, userData);
    assertTrue(app.newSession().login(userData));
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
