
package Model;

import java.sql.*;
 
public class DBCon {
    public static Connection getConnection()throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://mysql.railway.internal:3306/railway", "root", "OMGJcFwOFLdQedfGBDrrZeQRvXMqjjXv");
        
        return con;
    }
}