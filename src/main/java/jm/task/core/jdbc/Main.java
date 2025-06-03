package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args)  {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
        userDaoJDBC.saveUser("Ivan", "Ivanov" , (byte) 30);
        userDaoJDBC.saveUser("Petr", "Petrov" , (byte) 32);
        userDaoJDBC.saveUser("Sergey", "Sidorov" , (byte) 34);
        userDaoJDBC.saveUser("Evgeniy", "Zhukov" , (byte) 36);
        userDaoJDBC.getAllUsers();
        userDaoJDBC.cleanUsersTable();
        userDaoJDBC.dropUsersTable();
    }
}

