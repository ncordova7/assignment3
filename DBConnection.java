
import java.sql.*;
import java.lang.*;

public class DBConnection {

    //private Connection mySqlConnection = null;

    public static Connection getMySqlConnection(){
        Connection mysqlConnection = null;
        try{
            String driverLoc = "com.mysql.jdbc.Driver";
            String connectionUrl = "jdbc:mysql://35.185.209.10:3306/assign3?useSSL=false";
            String uName = "root";
            String pass = "kDr3CHPes1mcBBbB";


            Class.forName(driverLoc);
            mysqlConnection = DriverManager.getConnection(connectionUrl, uName, pass);
            System.out.println("Successful Connection!\n");

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return mysqlConnection;
    }

}
