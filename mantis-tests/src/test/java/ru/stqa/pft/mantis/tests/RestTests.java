package ru.stqa.pft.mantis.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import java.io.IOException;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase {

  @BeforeMethod
  public void checkIfBugFixed() throws IOException {
    int issueId = 144;
    skipIfNotFixedRest(issueId);
  }

    @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = app.rest().getIssues();
    Issue newIssue = new Issue()
            .withSubject("Test issue")
            .withDescription("New test issue");
    int issueId = app.rest().createIssue(newIssue);
    Set<Issue> newIssues = app.rest().getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }


  @Test (enabled = false)
  public void testCloseIssue() throws IOException {
    Set<Issue> oldIssues = app.rest().getIssues();
    Issue selectedIssue = oldIssues.iterator().next();
    oldIssues.remove(selectedIssue);
    oldIssues.add((Issue) app.rest().closeIssue(selectedIssue));
    Set<Issue> newIssues = app.rest().getIssues();
    assertEquals(newIssues, oldIssues);
  }


}