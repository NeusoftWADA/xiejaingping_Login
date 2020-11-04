package Login;

import Userdata.Userdata;
import java.sql.*;

public class Database {
    Connection connection = null;

    public Database(String name, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        this.connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=master", name, password);
    }
     /*
      测试连接是否成功
     */
    public void testConnection() {
        System.out.println(this.connection);
    }
     /*
     关闭数据库连接
     */
    public void close() throws SQLException {
        connection.close();
    }

    /**
     *从数据库获取用户数据   PreparedStatement连接前端调用数据库
     * @param username
     * @return
     * @throws SQLException
     */
    public Userdata getUser(String username) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Jw WHERE username = ?");
        preparedStatement.setString(1, username);
        preparedStatement.executeQuery();
         //executeQuery()方法会把数据库响应的查询结果存放在ResultSet类对象中供我们使用
        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            String name = resultSet.getString("username");//从数据库中获取的username
            String pwd = resultSet.getString("password");//从数据库中获取的password
            return new Userdata(name, pwd);
        } else {
            return null;
        }
    }

    /**
     *前端获取到的密码与数据库中的密码做比对
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */

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
