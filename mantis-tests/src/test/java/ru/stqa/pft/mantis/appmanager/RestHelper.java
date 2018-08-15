package ru.stqa.pft.mantis.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.stqa.pft.mantis.model.Issue;
import java.io.IOException;
import java.util.Set;

public class RestHelper {
  private ApplicationManager app;
  public RestHelper(ApplicationManager app) {
    this.app = app;
  }
  public Set<Issue> getIssues() throws IOException {
    String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues.json?limit=1000"))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  public int createIssue(Issue newIssue) throws IOException {
    String json = getExecutor().execute(Request.Post("http://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()), new BasicNameValuePair("description", newIssue.getDescription()), new BasicNameValuePair("state_name", newIssue.getState_name())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public Set<Issue> closeIssue(Issue issue) throws IOException {
    String json = app.rest().getExecutor().execute(Request.Post("http://bugify.stqa.ru/api/issues/" + issue.getId() + ".json")
            .bodyForm(new BasicNameValuePair("method", "update"), new BasicNameValuePair("subject", issue.getSubject()), new BasicNameValuePair("description", issue.getDescription()), new BasicNameValuePair("state_name", "Closed")))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issuesJson = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issuesJson, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  public Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }
}