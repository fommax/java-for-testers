package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class UserHelper extends BaseHelper {
  public UserHelper(ApplicationManager app) {
    super(app);
  }
  public void resetPassword(int id) {
    app.goTo().managePage();
    app.goTo().manageUsersPage();
    wd.findElement(By.cssSelector(String.format("a[href = 'manage_user_edit_page.php?user_id=%s']", id))).click();
    wd.findElement(By.cssSelector("input[value='Reset Password']")).click();
  }
}