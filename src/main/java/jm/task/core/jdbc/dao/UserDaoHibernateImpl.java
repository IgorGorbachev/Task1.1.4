package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (SessionFactory sessionFactory = Util.hibernateConfiguration().buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user(" +
                                   "id SERIAL PRIMARY KEY," +
                                   "name TEXT NOT NULL," +
                                   "lastName TEXT NOT NULL," +
                                   "age INT NOT NULL)").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (SessionFactory sessionFactory = Util.hibernateConfiguration().buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.hibernateConfiguration().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("INSERT INTO user (name,lastName,age) VALUES (?,?,?)")
                    .setParameter(1, name)
                    .setParameter(2, lastName)
                    .setParameter(3, age)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (SessionFactory sessionFactory = Util.hibernateConfiguration().buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM user WHERE id = ?")
                    .setParameter(1, id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (SessionFactory sessionFactory = Util.hibernateConfiguration().buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List listUsers = session.createSQLQuery("SELECT * FROM user")
                    .addEntity(User.class)
                    .list();
            session.getTransaction().commit();
            System.out.println(listUsers);
            return listUsers;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory sessionFactory = Util.hibernateConfiguration().buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
