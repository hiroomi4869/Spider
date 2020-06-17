package sql;

import java.sql.SQLException;

public class Run {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Sqlup sqlup = new Sqlup();
        sqlup.setup();
    }
}
