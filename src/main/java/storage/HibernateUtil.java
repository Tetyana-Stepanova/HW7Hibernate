package storage;

import lombok.Getter;
import models.company.Company;
import models.customer.Customer;
import models.developer.Developer;
import models.project.Project;
import models.skill.Skill;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final HibernateUtil INSTANCE;

    @Getter
    private final SessionFactory sessionFactory;

    static {
        INSTANCE = new HibernateUtil();
    }

    private HibernateUtil() {
        sessionFactory = new Configuration()
                .setProperty("hibernate.connection.url", StorageConstants.CONNECTION_URL)
                .setProperty("hibernate.connection.username", StorageConstants.USERNAME)
                .setProperty("hibernate.connection.password", StorageConstants.PASSWORD)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Skill.class)
                .addAnnotatedClass(Developer.class)
                .addAnnotatedClass(Project.class)
                .buildSessionFactory();
    }

    public static HibernateUtil getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }
}
