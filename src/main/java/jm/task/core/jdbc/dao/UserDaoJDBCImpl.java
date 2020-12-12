package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        String SQL = "CREATE TABLE users (" +
                " id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(20) NOT NULL," +
                " lastName VARCHAR(20) NOT NULL," +
                " age INT NOT NULL);";
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(SQL);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
        }
    }


    public void dropUsersTable() {
        String SQL = "DROP TABLE users;";
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(SQL);
            System.out.println("Таблица удлена");
        } catch (SQLException e) {
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        String SQL = "INSERT INTO users VALUES(NULL, ?, ?, ?);";
        try {
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                System.out.println("User с именем – " + name + "  добавлен в базу данных");
            }
        } catch (SQLException e) {
            System.out.println("Некорректный ввод данных");
            e.printStackTrace();
        }
    }


    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            System.out.println("Пользователь удалён по id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        String SQL = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                userList.add(user);
                System.out.println(userList.get(0));
                System.out.println("Получение пользователей");
                System.out.println("----------------------------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Не получилось отобразить пользователей");
        }
        return userList;
    }


    public void cleanUsersTable() {
        String SQL = "TRUNCATE TABLE users;";
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(SQL);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
