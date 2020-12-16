package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String SQL = "CREATE TABLE users (" +
                " id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(20) NOT NULL," +
                " lastName VARCHAR(20) NOT NULL," +
                " age INT NOT NULL);";
        try {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(SQL);
                System.out.println("Таблица создана (первая попытка)");
            }
        } catch (Exception e) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE users");
                System.out.println("Удаление предыдущей таблицы");
            }
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(SQL);
                System.out.println("Таблица создана (вторая попытка)");
            }
        }
    }


    public void dropUsersTable() throws SQLException {
        try {
            try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("DROP TABLE users;");
                    System.out.println("Таблица удалена");
            }
        } catch (Exception e) {
            System.out.println("Таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        connection.setAutoCommit(false);
        try {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES(NULL, ?, ?, ?);")) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                connection.commit();
                System.out.println("User с именем – " + name + "  добавлен в базу данных");
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            System.out.println("Некорректный ввод данных");
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public void removeUserById(long id) throws SQLException {
        connection.setAutoCommit(false);
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
            connection.commit();
            System.out.println("Пользователь удалён по id");
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            System.out.println("Пользователь не удалён по id");
        }
    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
                System.out.println(userList.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не получилось отобразить пользователей");
        }
        return userList;
    }


    public void cleanUsersTable() throws SQLException {
        connection.setAutoCommit(false);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users;");
            connection.commit();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            System.out.println("Таблица не очищена");
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
