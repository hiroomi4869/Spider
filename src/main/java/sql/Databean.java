package sql;

import java.sql.*;

public class Databean {
    private Connection con;
    public Databean(){
        String CLASSFROENAME = "com.mysql.jdbc.Driver";
        String SERVANDDB = "jdbc:mysql://localhost:3306/jsp";
        String USERNAME = "root";
        String PASSWORD = "486999";
        try {
            Class.forName(CLASSFROENAME);
            con = DriverManager.getConnection(SERVANDDB,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {

            System.out.println("ClassNotFoundException!!!");
            e.printStackTrace();
        } catch (SQLException e) {

            System.out.println("SQLException!!!");
            e.printStackTrace();

        }
    }
    public ResultSet getData(){
        try {
            Statement stm = con.createStatement();
            ResultSet result = stm.executeQuery("SELECT * FROM list");
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
