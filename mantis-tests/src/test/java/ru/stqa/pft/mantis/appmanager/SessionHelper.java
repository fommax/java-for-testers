package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

public class SessionHelper extends BaseHelper {

  public SessionHelper(ApplicationManager app) {
    super(app);
  }

  public void login(UserData userData) {
    type(By.name("username"), userData.getUsername());
    click(By.cssSelector("input[type='submit']"));
    type(By.name("password"), userData.getPasswordMantis());
    click(By.cssSelector("input[type='submit']"));
  }

}