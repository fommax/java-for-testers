package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.mantis.model.UserData;

public class RegistrationHelper extends BaseHelper {

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(UserData userData) {
    wd.get(app.getProperty("web.baseUrl") + "signup_page.php");
    type(By.name("username"), userData.getUsername());
    type(By.name("email"), userData.getEmail());
    click(By.cssSelector("input[value='Signup']"));
  }

  public void finish(String confirmationLink, UserData userData) {
    wd.get(confirmationLink);
    type(By.name("password"), userData.getPasswordMantis());
    type(By.name("password_confirm"), userData.getPasswordMantis());
    click(By.cssSelector("button[type='submit']"));
  }

  public void finishPasswordChange(String confirmationLink, UserData userData) {
    wd.get(confirmationLink);
    type(By.name("password"), userData.getPasswordMantis());
    type(By.name("password_confirm"), userData.getPasswordMantis());
    click(By.cssSelector("button[type='submit']"));
  }

}
