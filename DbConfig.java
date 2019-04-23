import java.sql.Connection;
import java.sql.DriverManager;

public class DbConfig {

    private Connection getMySqlConnection()
    {
        Connection sqlConnection = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            string connectionUrl = "jdbc:mysql://35.320.67.189:3306/cpsc408";

        }
    }
}
