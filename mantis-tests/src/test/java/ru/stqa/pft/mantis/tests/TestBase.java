package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class TestBase {


  protected static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));
  FirefoxDriver wd;

  public static boolean isAlertPresent(FirefoxDriver wd) {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }


  @AfterSuite
  public void tearDown() throws IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }

  public void ensureUserPresent() throws MessagingException, IOException {
    Long now = System.currentTimeMillis();
    UserData newUser = new UserData()
            .withUsername(String.format("user%s", now))
            .withEmail(String.format("user%s@localhost", now))
            .withPasswordMail("password").withPasswordMantis("password1");
    if (app.db().getUserData().size() == 0) {
      app.registration().start(newUser);
      List<MailMessage> mailMessages = app.james().waitForMail(newUser, 60000);
      String confirmationLink = findConfirmationLink(mailMessages, newUser);
      app.registration().finish(confirmationLink, newUser);
      assertTrue(app.newSession().login(newUser));
    }
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, UserData userData) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(userData.getEmail())).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  public boolean isIssueOpen() throws MalformedURLException, ServiceException, RemoteException {
    return isIssueOpen();
  }

  public boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    String adminLogin = app.getProperty("web.adminlogin");
    String adminPassword = app.getProperty("web.adminPassword");
    MantisConnectPortType mc = app.soap().getMantisConnect();
    String status = mc.mc_issue_get(adminLogin, adminPassword, BigInteger.valueOf(issueId)).getStatus().getName();
    if (status.equals("closed")) {
      return false;
    }
    return true;
  }

  public void skipIfNotFixed(int issueId) throws RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpen(issueId) == true) {
      throw new SkipException("Ignored because of issue " + issueId);
    }

  }

}
