/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jaybirdtest;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author student
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        // TODO code application logic here
        Class.forName("org.firebirdsql.jdbc.FBDriver");
        java.sql.Connection con =  DriverManager.getConnection("jdbc:firebirdsql:localhost:F:/40813/Barinov/bd/SUBA.FDB","SYSDBA","masterkey");
        Statement s = con.createStatement();
        ResultSet r = s.executeQuery("select * from \"Clients\"");
        while (r.next()) {
            System.out.println(r.getString("name"));
        }
    } 

}
