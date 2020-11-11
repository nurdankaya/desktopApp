package temperatureproject;

import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author NURDAN
 */
public class DBConnection {

    private static Connection conn;
    private static final String url = "jdbc:mysql://localhost/temperature_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //veritabanımın adı java
    private static final String user = "user"; //kullanıcı adı
    private static final String pass = "passwPhp1.";//şifre

    public static Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException cnfe) {
            System.err.println("Error: " + cnfe.getMessage());
        }

        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (conn != null && !conn.isClosed()) {
            return conn;
        }
        connect();
        return conn;

    }

    public static String machineForPatient(String name) throws SQLException, ClassNotFoundException {

        String[] splited = name.split("\\s+");

        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery("select * from patients");

        while (rs.next()) {
            if (rs.getInt("idpatients") == parseInt(splited[0])) {
                System.out.println(rs.getString("machineName"));
                return rs.getString("machineName");

            }

        }
        return null;
    }

    public static String[] openPorts() throws SQLException, ClassNotFoundException {

        String[] ports = null;
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery("select * from machines where state=1");

        int i = 0;
        while (rs.next()) {

            ports[i] = rs.getString("portName");

        }

        return ports;
    }

    public static void saveDay() throws SQLException, ClassNotFoundException {
        String machine;
        Connection con = DBConnection.getConnection();

        ResultSet rs1 = con.createStatement().executeQuery("select * from machines");  //show just avaliable machines
        int i = 0;
        while (rs1.next()) {  //export tables of all machines
            i++;
            machine = rs1.getString("machineName");
            ResultSet rs2 = con.createStatement().executeQuery("SELECT * FROM " + machine + " INTO OUTFILE '" + machine + "-" + i + ".csv'  FIELDS ENCLOSED BY '\"'  TERMINATED BY ';'  ESCAPED BY '\"'  LINES TERMINATED BY '\\r\\n'");

        }

        System.out.println("exported!!");            // export at  C:\ProgramData\MySQL\MySQL Server 8.0\Data\temperature_db
    }

   

}
