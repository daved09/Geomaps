package database;
import java.sql.*;
public class Userdatabase extends Database{
	private String hostname,hostport;

	private String username,userpw,vorname,nachname,email;
	private String test = "";

	public String getUserpw(){
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getUsername() {
		return username;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public String getEmail() {
		return email;
	}


	
	public String getErgebnis(){
		return test;
	}
	
	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getHostport() {
		return hostport;
	}

	public void setHostport(String hostport) {
		this.hostport = hostport;
	}


	public void deleteUser(String username,String hostname,String hostport){
		try{
			connect(hostname,hostport);
			String sql = "DELETE FROM users WHERE username = '"+username+"'";
			queryUpdate(sql);
		}
		catch(Exception E){
			E.printStackTrace();
		}
		finally {
			disconnect();
		}
	}
	public void registerUser(String username,String password,String vorname,String nachname,String email,String hostname,String hostport) throws SQLException {
		connect(hostname,hostport);
		String sql = "INSERT INTO users VALUES ('"+username+"', '"+password+"', '"+vorname+"', '"+nachname+"', '"+email+"')";
		queryUpdate(sql);
		disconnect();
	}
	public boolean loginUser(String username,String password,String hostname,String hostport) throws SQLException{
		boolean right = false;
		connect(hostname,hostport);
		String sql = "SELECT password FROM users WHERE username = '"+username+"'";
		querySelect(sql);
		rs.next();
		String pw = rs.getString(1);
		setUserpw(pw);
		if(pw.equals(password)){
			right = true;
		}
		else{
			right = false;
		}
		disconnect();
		return right;
		
	}
	public void ladeUser(String username,String hostname,String hostport) throws SQLException {
		connect(hostname,hostport);
		String sql = "SELECT * FROM users WHERE username = '"+username+"'";
		System.out.println(sql);
		querySelect(sql);
		rs.next();
		this.username = rs.getString(1);
		userpw = rs.getString(2);
		vorname = rs.getString(3);
		nachname = rs.getString(4);
		email = rs.getString(5);
		disconnect();
	}

}//Quellen: http://www2.math.uni-wuppertal.de/~schaefer/jv/haupt/node112.html
