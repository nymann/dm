package dk.sdu.dm;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    static Connection connection = null;

    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("");
        }
        catch (Exception e) {
            System.out.println(":-)");
        }
    }
}
