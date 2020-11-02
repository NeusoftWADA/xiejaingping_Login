package Login;

import Userdata.Userdata;
import java.sql.*;

public class Database {
    Connection connection = null;

    public Database(String name, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=master", name, password);
    }

    public void testConnection() {
        System.out.println(this.connection);
    }

    public void close() throws SQLException {
        connection.close();
    }

    public Userdata getUser(String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Jw WHERE username = ?");
        preparedStatement.setString(1, username);
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            String name = resultSet.getString("username");//从数据库中获取的username
            String pwd = resultSet.getString("password");//从数据库中获取的password
            return new Userdata(name, pwd);
        } else {
            return null;
        }
    }

    public Userdata check(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM Jw WHERE username = ?");
        preparedStatement.setString(1, username);
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            String pwd = resultSet.getString("password");
            if (pwd.equals(password)) {
                return getUser(username);
            } else {
                return null;
            }
        } else {
            return null;
        }

    }
}
