package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private Util() {
        throw new IllegalStateException("Utility class");}
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";

    private static final String PASSWORD = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/115db";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(AvailableSettings.DRIVER, DRIVER);
        settings.put(AvailableSettings.URL, URL + "?useSSL=false");
        settings.put(AvailableSettings.USER, USER_NAME);
        settings.put(AvailableSettings.PASS, PASSWORD);
        settings.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        settings.put(AvailableSettings.SHOW_SQL, "true"); //Hibernate будет дублировать в консоль все запросы, которые выполняет
        settings.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        settings.put(AvailableSettings.HBM2DDL_AUTO, "update");
        configuration.setProperties(settings);
        configuration.addAnnotatedClass(User.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();


        return configuration.buildSessionFactory(serviceRegistry);
    }
}
