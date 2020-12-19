package jm.task.core.jdbc.util;

import com.mysql.cj.jdbc.MysqlDataSource;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private final static String url = "jdbc:mysql://localhost:3306/preDB1130?useSSL=false&serverTimezone=Europe/Moscow";
    private final static String user = "root";
    private final static String password = "SKdvp!#2020";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Соединение установлено");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Соединение не установлено");
        }
        return connection;
    }

    /* ================ Hibernate methods ================ */

    public SessionFactory getFactory() {

        SessionFactory factory = new Configuration()
                .addProperties(hibernateProperties())
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        System.out.println("Фабрика создана!");
        return factory;
    }

    public Properties hibernateProperties() {
        final Properties properties = new Properties();

        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", true);
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.connection.datasource", getMysqlDataSource());

        return properties;
    }

    protected DataSource getMysqlDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(url);
        mysqlDataSource.setUser(user);
        mysqlDataSource.setPassword(password);
        return mysqlDataSource;
    }
}