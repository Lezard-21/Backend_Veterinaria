package uv.mx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String url = "jdbc:"+getAssignedurl();
    private static String driverName = "com.mysql.cj.jdbc.Driver"; //"com.mysql.jdbc.Driver"; // com.mysql.cj.jdbc.Driver
    private static String username = getAssignedUser();
    private static String password = getAssignedPassword();
    // variable de conexion
    private static Connection connection = null;

    public static Connection getConnection(){
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(" SQL:" + e);
        } catch (ClassNotFoundException e){
            System.out.println("Driver:" + e);
        }
        return connection;
    }
    static String getAssignedUser() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("MYSQLUSER") != null) {
            return processBuilder.environment().get("MYSQLUSER");
        }
        return "root";
    }
    static String getAssignedPassword() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("MYSQL_ROOT_PASSWORD") != null) {
            return processBuilder.environment().get("MYSQL_ROOT_PASSWORD");
        }
        return "Loldavid2111&";
    }
    static String getAssignedurl() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("MYSQL_URL") != null) {
            return processBuilder.environment().get("MYSQL_URL");
        }
        return "mysql://127.0.0.1:3306/veterinaria";
    }
}