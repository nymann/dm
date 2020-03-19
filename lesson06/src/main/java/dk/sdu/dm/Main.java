package dk.sdu.dm;

import java.sql.*;

public class Main {
    static Connection connection = null;

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/dm",
                    "dm_user",
                    "thisisit");
        } catch (SQLException e) {
            System.out.println(":-)");
        }

        // Create user
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO users (name, cpr) VALUES (?, ?);"
            );
            insertStatement.setString(1, "John Doe");
            insertStatement.setString(2, "2204991111");
            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Query for users with CPR x
        try {
            PreparedStatement queryStatement = connection.prepareStatement("SELECT * FROM users WHERE cpr = ?");
            queryStatement.setString(1, "2204991111");
            ResultSet queryResultSet = queryStatement.executeQuery();

            System.out.println("The following users matched the query:");
            while (queryResultSet.next()) {
                var name = queryResultSet.getString("name");
                var cpr = queryResultSet.getString("cpr");
                System.out.println(String.format("The users name was %s and his CPR number was %s", name, cpr));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
