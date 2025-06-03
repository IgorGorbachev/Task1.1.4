package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/*
Вопросы по этому классу:
1. SQL запросы нужно выносить в отдельные переменные?
 */
public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS user(" +
                                                                               "id SERIAL PRIMARY KEY," +
                                                                               "name TEXT NOT NULL," +
                                                                               "lastName TEXT NOT NULL," +
                                                                               "age INT NOT NULL)")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS user")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Connection connection = Util.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, lastName, age)" +
                                                                               "VALUES (?,?,?)")) {

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.executeUpdate();
                connection.commit();
                System.out.println("User с именем " + user.getName() + " добавлен в базу данных");
            }catch (SQLException e){
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id){
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers(){
        List<User> listUser = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                listUser.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(listUser);
        return listUser;
    }

    public void cleanUsersTable(){
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
