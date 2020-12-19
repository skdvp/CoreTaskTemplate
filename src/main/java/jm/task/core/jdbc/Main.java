package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Bob", "Robson", (byte) 50);
        userService.saveUser("Sam", "Jackson", (byte) 40);
        userService.saveUser("Paul", "Smith", (byte) 30);
        userService.saveUser("Jack", "London", (byte) 20);

        userService.getAllUsers();
        userService.removeUserById(2L);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}