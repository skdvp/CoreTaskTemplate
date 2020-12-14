package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        Connection connection = new Util().getConnection();
        connection.setAutoCommit(false);

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        connection.commit();

        userService.saveUser("Bob", "Robson", (byte) 50);
        userService.saveUser("Sam", "Jackson", (byte) 40);
        userService.saveUser("Paul", "Smith", (byte) 30);
        userService.saveUser("Jack", "London", (byte) 20);
        connection.commit();

        userService.getAllUsers();

        userService.removeUserById(2L);
        connection.commit();

        userService.cleanUsersTable();
        connection.commit();

        userService.dropUsersTable();
        connection.commit();

        connection.close();


    }
}

/*
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        Util util = new Util();
        Connection connection = util.getConnection();
        connection.setAutoCommit(false);
        Statement statement;


        statement = connection.createStatement();
        try {
            try {
                statement.executeUpdate("CREATE TABLE users (" +
                        " id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        " name VARCHAR(20) NOT NULL," +
                        " lastName VARCHAR(20) NOT NULL," +
                        " age INT NOT NULL);");
            } catch (SQLSyntaxErrorException syntaxErrorException) {
                statement.executeUpdate("DROP TABLE users;");
                connection.commit();
            }
            connection.commit();

            statement.executeUpdate("INSERT INTO users VALUES(NULL, 'Bob', 'Robson', 50);");
            try (ResultSet rs1 = statement.executeQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1;")) {
                while (rs1.next()) {
                    String name = rs1.getObject(2).toString();
                    System.out.println("User с именем – " + name + " добавлен в базу данных");
                }
            }
            connection.commit();

            statement.executeUpdate("INSERT INTO users VALUES(NULL, 'Sam', 'Jackson', 40);");
            try (ResultSet rs2 = statement.executeQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1;")) {
                while (rs2.next()) {
                    String name = rs2.getObject(2).toString();
                    System.out.println("User с именем – " + name + " добавлен в базу данных");
                }
            }
            connection.commit();

            statement.executeUpdate("INSERT INTO users VALUES(NULL, 'Paul', 'Smith', 30);");
            try (ResultSet rs3 = statement.executeQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1;")) {
                while (rs3.next()) {
                    String name = rs3.getObject(2).toString();
                    System.out.println("User с именем – " + name + " добавлен в базу данных");
                }
            }
            connection.commit();

            statement.executeUpdate("INSERT INTO users VALUES(NULL, 'Jack', 'London', 20);");
            try (ResultSet rs4 = statement.executeQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1;")) {
                while (rs4.next()) {
                    String name = rs4.getObject(2).toString();
                    System.out.println("User с именем – " + name + " добавлен в базу данных");
                }
            }
            connection.commit();

            try (ResultSet rs5 = statement.executeQuery("SELECT * FROM users")) {
                while (rs5.next()) {
                    int id = rs5.getInt("id");
                    String name = rs5.getString("name");
                    String lastName = rs5.getString("lastName");
                    int age = rs5.getInt("age");
                    System.out.println(id + " " + " " + name + " " + lastName + " " + age);
                }

                System.out.println("------------------------------------------------");

                System.out.println(" DELETE FROM users WHERE id = 2");
                PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?");
                st.setInt(1, 2);
                st.executeUpdate();
                connection.commit();

                System.out.println("------------------------------------------------");

                try (ResultSet rs6 = statement.executeQuery("SELECT * FROM users;")) {
                    while (rs6.next()) {
                        int id = rs6.getInt("id");
                        String name = rs6.getString("name");
                        String lastName = rs6.getString("lastName");
                        int age = rs6.getInt("age");
                        System.out.println(id + " " + " " + name + " " + lastName + " " + age);
                    }
                }
            }


            statement.executeUpdate("TRUNCATE TABLE users;");
            try {
                statement.executeUpdate("DROP TABLE users;");
            } catch (SQLSyntaxErrorException syntaxErrorException) {

            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }

        }

    }
*/
