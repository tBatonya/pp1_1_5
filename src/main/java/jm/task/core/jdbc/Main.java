package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Иван1", "Иванов1", (byte) 10);
        userService.saveUser("Иван2", "Иванов2", (byte) 20);
        userService.saveUser("Иван3", "Иванов3", (byte) 30);
        userService.saveUser("Иван4", "Иванов4", (byte) 50);
        List<User> allUsers = userService.getAllUsers();
        for (User u: allUsers) {
            System.out.println(u);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
