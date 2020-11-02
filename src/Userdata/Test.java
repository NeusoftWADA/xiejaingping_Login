package Userdata;

import Login.Database;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database database = new Database("sa","123456");

        database.testConnection();
    }
}
