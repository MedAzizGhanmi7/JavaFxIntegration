package CoursifyApp.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final String user = "root";
    private final String pwd = "";
    private final String URL = "jdbc:mysql://localhost:3306/" + "coursifydb";
    private Connection cnx;
    private DatabaseConnection()
    {

    }
    public static DatabaseConnection getInstance()
    {
        if(instance == null) instance = new DatabaseConnection();
        return instance;
    }
    public Connection getCnx()
    {
        if(cnx != null) return cnx;
        try {
            cnx = DriverManager.getConnection(URL, user, pwd);
            System.out.println("DatabaseConnection ("+"coursifydb"+") created successfully.");
        }catch (SQLException ex)
        {
            System.err.println("DatabaseConnection Error: " + ex.getMessage());
        }
        return cnx;
    }
}
