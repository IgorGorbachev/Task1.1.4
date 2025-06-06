package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;



public class Main {
    public static void main(String[] args) {
//        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
//          userDaoJDBC.createUsersTable();
//        userDaoJDBC.saveUser("Ivan", "Ivanov" , (byte) 30);
//        userDaoJDBC.saveUser("Petr", "Petrov" , (byte) 32);
//        userDaoJDBC.saveUser("Sergey", "Sidorov" , (byte) 34);
//        userDaoJDBC.saveUser("Evgeniy", "Zhukov" , (byte) 36);
//        userDaoJDBC.getAllUsers();
//        userDaoJDBC.cleanUsersTable();
//        userDaoJDBC.dropUsersTable();

        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();
        userDaoHibernate.createUsersTable();
        userDaoHibernate.saveUser("Ivan", "Ivanov" , (byte) 30);
        userDaoHibernate.saveUser("Petr", "Petrov" , (byte) 32);
        userDaoHibernate.saveUser("Sergey", "Sidorov" , (byte) 34);
        userDaoHibernate.saveUser("Evgeniy", "Zhukov" , (byte) 36);
        userDaoHibernate.getAllUsers();
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();


    }
}

