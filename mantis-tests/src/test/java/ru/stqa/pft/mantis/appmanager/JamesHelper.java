package ru.stqa.pft.mantis.appmanager;


import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class JamesHelper {

  private ApplicationManager app;
  private TelnetClient telnet;
  private InputStream in;
  private PrintStream out;
  private Session mailSession;
  private Store store;
  private String mailServer;

  public JamesHelper(ApplicationManager app) {
    this.app = app;
    telnet = new TelnetClient();
    mailSession = Session.getDefaultInstance(System.getProperties());
  }

  public static MailMessage toModelMail(Message m) {
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean doesUserExist(String name) {
    initTelnetSession();
    write("verify" + name);
    String result = readUntil("exist");
    closeTelnetSession();
    return result.trim().equals("User " + name + " exist");
  }

  public void createUser(UserData userData) {
    initTelnetSession();
    write("adduser " + userData.getUsername() + " " + userData.getPasswordMail());
    write("");
    String result = readUntil("User " + userData.getUsername() + " added");
    closeTelnetSession();
  }

  public void deleteUser(String name) {
    initTelnetSession();
    write("deluser " + name);
    String result = readUntil("User " + name + " deleted");
    closeTelnetSession();
  }

  public void initTelnetSession() {
    mailServer = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.adminlogin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailServer, port);
      in = telnet.getInputStream();
      out = new PrintStream(telnet.getOutputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

    readUntil("Login id:");
    write("");
    readUntil("Password:");
    write("");

    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    readUntil("Welcome " + login + ". HELP for a list of commands");
  }

  private String readUntil(String pattern) {
    try {
      char lastChar = pattern.charAt(pattern.length() - 1);
      StringBuffer sb = new StringBuffer();
      char ch = (char) in.read();
      while (true) {
        System.out.println(ch);
        sb.append(ch);
        if (ch == lastChar) {
          if (sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void closeTelnetSession() {
    write("quit");
  }

  public void drainEmail(UserData userData) throws MessagingException {
    Folder inbox = openInbox(userData);
    for (Message message : inbox.getMessages()) {
      message.setFlag(Flags.Flag.DELETED, true);
    }
    closeFolder(inbox);
  }

  private void closeFolder(Folder folder) throws MessagingException {
    folder.close(true);
    store.close();
  }

  private Folder openInbox(UserData userData) throws MessagingException {
    store = mailSession.getStore("pop3");
    store.connect(mailServer, String.valueOf(userData.getUsername()), userData.getPasswordMail());
    Folder folder = store.getDefaultFolder().getFolder("INBOX");
    folder.open(Folder.READ_WRITE);
    return folder;
  }

  public List<MailMessage> waitForMail(UserData userData, long timeout) throws MessagingException {
    long now = System.currentTimeMillis();
    while (System.currentTimeMillis() < now + timeout) {
      List<MailMessage> allMail = getAllMail(userData);
      if (allMail.size() > 0) {
        return allMail;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  public List<MailMessage> getAllMail(UserData userData) throws MessagingException {
    Folder inbox = openInbox(userData);
    List<MailMessage> messages = Arrays.asList(inbox.getMessages())
            .stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
    closeFolder(inbox);
    return messages;
  }
}