/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;
import java.sql.*;
/**
 *
 * @author Diba15
 */
public interface DBData {
    String DB_URL = "jdbc:mysql://localhost/tubeswarung?serverTimezone=Asia/Jakarta";
    String DB_USER = "Diba15";
    String DB_PASS = "Tunjang1";
    
    public abstract void CUDData(String query);
    public abstract ResultSet readData(String query);
}
