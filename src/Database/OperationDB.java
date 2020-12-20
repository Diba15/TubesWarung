/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

/**
 *
 * @author Diba15
 */
public class OperationDB implements DBData {
    private final MysqlDataSource myData = new MysqlDataSource();
    private Connection conn;
    private ResultSet rs;
    private PreparedStatement ps;
            
    @Override
    public void CUDData(String query) {
        myData.setURL(DB_URL);
        myData.setUser(DB_USER);
        myData.setPassword(DB_PASS);
        try {
            conn = myData.getConnection();
            ps = conn.prepareStatement(query);
            ps.executeUpdate(query);
        } catch(SQLException e) {
            System.out.println("Get Message : " + e);
        } finally {
            try {
                conn.close();
                ps.close();
            } catch(SQLException e) {
                
            }
        }
    }

    @Override
    public ResultSet readData(String query) {
        myData.setURL(DB_URL);
        myData.setUser(DB_USER);
        myData.setPassword(DB_PASS);
        try {
            conn = myData.getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery(query);
        } catch(SQLException e) {
            System.out.println("Get Message : " + e);
        }
        return rs;
    }
    
}
