package ru.stqa.pft.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.pft.mantis.model.UserData;
import java.util.List;

public class DbHelper {

  private final ApplicationManager app;
  private final SessionFactory sessionFactory;

  public DbHelper(ApplicationManager app) {
    this.app = app;

    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // based on hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public List<UserData> getUserData() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData where access_level ='25' ").list();
    session.getTransaction().commit();
    session.close();
    return result;
  }

}