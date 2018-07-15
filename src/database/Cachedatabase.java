package database;
import java.io.IOException;
import java.sql.*;

import libs.OptionMaps;

import javax.swing.*;

public class Cachedatabase extends Database{

	
	private String name,description,owner;
	private float lGrad;
	private float bGrad;
	private int end;
	private String[] caches;
	private float[] lgradl;
	private float[] bgradl;

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String caches(int i){
		String cachnum = caches[i];
		return cachnum;
	}
	public float lgradl(int i){
		float lgradln = lgradl[i];
		return lgradln;
	}
	public float bgradl(int i){
		float bgradln = bgradl[i];
		return bgradln;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public float getbGrad() {
		return bGrad;
	}

	public void setbGrad(float bGrad) {
		this.bGrad = bGrad;
	}

	public float getlGrad() {
		return lGrad;
	}

	public void setlGrad(float lGrad) {
		this.lGrad = lGrad;
	}


	public void registerCache(String name,String owner,String description,float lGrad,float bGrad,String hostname,String hostport){
		try {
			connect(hostname,hostport);
			queryUpdate("INSERT INTO caches "
					+ "VALUES ('"+name+"', '"+owner+"', '"+description+"','"+lGrad+"','"+bGrad+"')");
		}
		catch (Exception E){
			E.printStackTrace();
			JOptionPane.showMessageDialog(null,"Etwas ist schief gelaufen.");
		}
		finally{
			disconnect();
		}
	}
	public String[] listCache(String owner,String hostname,String hostport) throws IOException {
		try{
			int value=0;
			connect(hostname,hostport);
			String sql = "SELECT * FROM caches WHERE owner = '"+owner+"'";
			System.out.println(sql);
			querySelect(sql);
			while (rs.next()){
				value ++;
			}
			rs.beforeFirst();
			caches = new String[value];
			setEnd(value);
			for (int i=0;i<caches.length;i++){
				rs.next();
				caches[i] = rs.getString(1);
			}
			return caches;
		}
		catch (Exception E){
			caches = null;
		}
		finally {
			if(conn != null){
				disconnect();
			}
		}
		disconnect();
		return caches;
	}
	public String[] listCaches(String hostname,String hostport) throws IOException {
		try{
			int value=0;
			connect(hostname,hostport);
			String sql = "SELECT name FROM caches";
			System.out.println(sql);
			querySelect(sql);
			while (rs.next()){
				value ++;
			}
			rs.beforeFirst();
			caches = new String[value];
			setEnd(value);
			for (int i=0;i<caches.length;i++){
				rs.next();
				caches[i] = rs.getString(1);
			}
			return caches;
		}
		catch (Exception E){
			caches = null;
		}
		finally {
			if(conn != null){
				disconnect();
			}
		}
		disconnect();
		return caches;
	}
	public String[] listCaches2(String name,String hostname,String hostport) throws IOException {
		try{
			int value=0;
			connect(hostname,hostport);
			String sql = "SELECT name FROM caches WHERE name LIKE '"+name+"%'";
			querySelect(sql);
			while (rs.next()){
				value ++;
			}
			rs.beforeFirst();
			caches = new String[value];
			setEnd(value);
			for (int i=0;i<caches.length;i++){
				rs.next();
				caches[i] = rs.getString(1);
			}
			return caches;
		}
		catch (Exception E){
			caches = null;
		}
		finally {
			if(conn != null){
				disconnect();
			}
		}
		disconnect();
		return caches;
	}
	public float[] listlgrad(String hostname,String hostport){
		try{
			int value=0;
			connect(hostname,hostport);
			String sql="SELECT lGrad FROM caches";
			querySelect(sql);
			while (rs.next()){
				value++;
			}
			rs.beforeFirst();
			lgradl = new float[value];
			setEnd(value);
			for(int i=0;i<lgradl.length;i++){
				rs.next();
				lgradl[i] = rs.getFloat(1);
			}
			return lgradl;
		}
		catch (SQLException E){
			lgradl = null;
			JOptionPane.showMessageDialog(null,"Es ist ein Fehler in der Datenbankverbindung aufgetreten. " +
					"Fehlermedlung: "+E);
		}
		finally {
			if(conn != null){
				disconnect();
			}
		}
		return lgradl;
	}
	public float[] listbgrad(String hostname,String hostport){
		try{
			int value=0;
			connect(hostname,hostport);
			String sql="SELECT bGrad FROM caches";
			querySelect(sql);
			while (rs.next()){
				value++;
			}
			rs.beforeFirst();
			bgradl = new float[value];
			setEnd(value);
			for(int i=0;i<bgradl.length;i++){
				rs.next();
				bgradl[i] = rs.getFloat(1);
			}
			return bgradl;
		}
		catch (Exception E){
			bgradl = null;
		}
		finally {
			if(conn != null){
				disconnect();
			}
		}
		return bgradl;
	}
	public void loadCache(String name,String hostname,String hostport){
		try{
			connect(hostname, hostport);
			String sql ="SELECT * FROM caches WHERE name = '"+name+"'" ;
			System.out.println(sql);
			querySelect(sql);
			rs.next();
			setName(rs.getString(1));
			setOwner(rs.getString(2));
			setDescription(rs.getString(3));
			setlGrad(rs.getFloat(4));
			setbGrad(rs.getFloat(5));
			disconnect();
		}
		catch(Exception e){
			setName("Kein Cache");
			setOwner("Kein Owner");
			setDescription("Keine Beschreibung");
			e.printStackTrace();
		}
	}
	public void deteleAllCaches(String username,String hostname,String hostport){
		try{
			connect(hostname,hostport);
			String sql = "DELETE FROM caches WHERE owner = '"+username+"'";
			queryUpdate(sql);
		}catch (Exception E){
			E.printStackTrace();
		}
		finally {
			disconnect();
		}
	}
	public void deleteCache(String name,String hostname,String hostport) throws IOException, SQLException {
		connect(hostname, hostport);
		String sql = "DELETE FROM caches WHERE name = '"+name+"'";
		System.out.println(sql);
		queryUpdate(sql);
		disconnect();
	}
	public void updateCache(String name,String description,String hostname,String hostport) throws IOException, SQLException {
		connect(hostname,hostport);
		String sql = "UPDATE caches SET description = '"+description+"' WHERE name = '"+name+"'";
		System.out.println(sql);
		queryUpdate(sql);
		disconnect();
	}
	
}
