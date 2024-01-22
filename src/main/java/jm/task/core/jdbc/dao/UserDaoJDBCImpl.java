package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(45)," +
                "lastName VARCHAR(45)," +
                "age INT);";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка в БД!");
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Таблице уничтожена!!!");
        } catch (SQLException e) {
            System.out.println("Ошибка в БД!");
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name, lastName, age) VALUES(?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

//            System.out.println("Метод запущен, соединение закрыто?: " + connection.isClosed());
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %S добавлен в базу данных\n", name);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Паренёк с id " + id + " был удалён!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM users;";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                usersList.add(user);
            }

            System.out.println("Список сформирван!!");
        } catch (SQLException e) {
            System.out.println("Ошибка в БД!");
            throw new RuntimeException(e);
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users;";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("ТаблицA очищена!");
        } catch (SQLException e) {
            System.out.println("Ошибка в БД!");
            throw new RuntimeException(e);
        }
    }
}
