package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Util {
    private static volatile Util instance;
    private static final String DBURL = "db.url";
    private static final String DBUSER = "db.username";
    private static final String DBPASSWORD = "db.password";

    private static final Properties PROPERTIES = new Properties();

    private Util() {
    }

    static {
        loadProperti();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Configuration hibernateConfiguration(){
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        return configuration;
    }

    private static void loadProperti() {
        try (InputStream inputStream = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getForProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    public Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    Util.getForProperties(DBURL),
                    Util.getForProperties(DBUSER),
                    Util.getForProperties(DBPASSWORD));
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
