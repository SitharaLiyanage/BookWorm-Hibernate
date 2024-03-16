package lk.ijse.config;

import lk.ijse.entity.Book;
import lk.ijse.entity.Transactions;
import lk.ijse.entity.User;
import lk.ijse.entity.Branch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration().configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Branch.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Transactions.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
