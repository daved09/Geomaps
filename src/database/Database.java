package database;

import libs.AdminOptionMaps;

import javax.swing.*;
import java.sql.*;

public class Database{
	AdminOptionMaps admin = new AdminOptionMaps("");
	Connection conn;
	Statement stmt;
	ResultSet rs;
	public Database(){
		admin.startdb();
	}
	public void testConnection(String hostname, String hostport){
		admin.startdb();
		try
		{
			String database = "jdbc:mysql://"+hostname+":"+hostport+"/"+admin.getDbname();
			conn = DriverManager.getConnection(database, admin.getDbuser(), admin.getDbpw());
			stmt = conn.createStatement();
			System.out.println("Datenbankverbindung erfolgreich");
			JOptionPane.showMessageDialog(null,"Datenbankverbingung erfolgreich");
			String queri = "SELECT * FROM users";
			ResultSet rs = stmt.executeQuery(queri);

		}
		catch ( SQLException e )
		{
			JOptionPane.showMessageDialog(null,"Datenbankverbingung fehlgeschlagen");
			System.err.println("Verbindung mit der Datenbank fehlgeschlagen");
			e.printStackTrace();
		}
		finally
		{
			if ( conn != null ){
				try { conn.close(); } catch ( SQLException e ) { e.printStackTrace(); }}
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}


	}
	public void disconnect(){
		if(conn != null){
			try{
				conn.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(stmt != null){
			try{
				stmt.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	public void connect(String hostname,String hostport) throws SQLException {
		admin.startdb();
		String database = "jdbc:mysql://"+hostname+":"+hostport+"/"+admin.getDbname();
		conn = DriverManager.getConnection(database,admin.getDbuser(),admin.getDbpw());
	}
	public void queryUpdate(String query){
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Etwas ist schief gelaufen.");
			e.printStackTrace();
		}

	}
	public void querySelect(String query){
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Etwas ist schief gelaufen.");
			e.printStackTrace();
		}

	}
	public void loadDriver(){
		try {
	      	    Class.forName("com.mysql.jdbc.Driver").newInstance(); 
	      	    System.out.println(Class.forName("com.mysql.jdbc.Driver")); 
	      	    System.out.println(Class.forName("com.mysql.jdbc.Driver").newInstance());
				System.out.println("* Treiber wurde erfolgreich geladen");
	        } 
	        catch (Exception e) { 
	            System.err.println("Treiber laden fehlgeschlagen."); 
	            e.printStackTrace(); 
	        }
	}
	

}
