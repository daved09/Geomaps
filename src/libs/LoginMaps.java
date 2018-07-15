package libs;

import javax.swing.*;

import database.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by dalub on 17.01.2016.
 */
public class LoginMaps extends JDialog{
	Userdatabase user = new Userdatabase();
	private String password,username,email,path,osname,sysuser;
	private String logged = "gast";
	private String hostname,hostport;

	public String getHostport() {
		try {
			setHostport();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hostport;
	}

	public void setHostport() throws IOException {
		this.hostport = read(getPath()+"hostport.txt");
	}

	public String getHostname() {
		try {
			setHostname();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hostname;
	}

	public void setHostname() throws IOException {
		this.hostname = read(getPath()+"hostname.txt");
	}

	public String getOsname(){
		osname = System.getProperty("os.name");
		return osname;
	}
	public String getSysuser(){
		sysuser = System.getProperty("user.name");
		return sysuser;
	}
	public LoginMaps(String hostname, String hostport, String autousername, String autopassword){
		setPath();
		this.hostname = hostname;
		this.hostport = hostport;
		fensterelemente();
		fenstereigenschaften();
	}
	public String testConnection(String hostname,String hostport){
		user.testConnection(hostname,hostport);
		String ergebniss = user.getErgebnis();
		return ergebniss;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogged() {
		return logged;
	}
	public void setLogged(String logged) {
		this.logged = logged;
	}

	public void login(String username,String password,String hostname,String hostport){
		user.setHostname(hostname);
		user.setHostport(hostport);
		try{
			if(user.loginUser(username,password,getHostname(),getHostport())==true){
				logged = username;
				this.setVisible(false);
				JOptionPane.showMessageDialog(null,"Willkommen "+username);
				setPassword(password);
			}
			else{
				logged = "gast";
				JOptionPane.showMessageDialog(null, "Falsche Logindaten.");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Falsche Logindaten.");
			e.printStackTrace();
		}
	}
	public void write(String name,String text) throws IOException{
		FileWriter fw = new FileWriter(name);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text);
		bw.close();
		fw.close();
	}
	public String read(String name) throws IOException{
		FileReader fr = new FileReader(name);
		BufferedReader br = new BufferedReader(fr);
		String text = br.readLine();
		br.close();
		fr.close();
		return text;
	}
	public void setPath(){
		if(getOsname().contains("Windows")){
			this.path = "C:/Users/"+getSysuser()+"/.options/";
		}
		else{
			this.path = "/home/"+getSysuser()+"/.options/";
		}
	}
	public String getPath(){
		setPath();
		return path;
	}
	public void speichereLogin(String user,String password) throws IOException{
		write(getPath()+"username.txt",user);
		write(getPath()+"password.txt",password);
	}

	public void dataUpdate(){
		getHostname();
		getHostport();
		lblUser.setText(getLogged());
	}

	public void reset(){
		txtUsername.setText("");
		txtPassword.setText("");
	}

	JTextField txtUsername = null;
	JPasswordField txtPassword = null;
	JLabel lblUser = null;

	public void fensterelemente(){
		this.add(textPanel(), BorderLayout.NORTH);
		this.add(buttonPanel(),BorderLayout.SOUTH);
	}
	public void fenstereigenschaften(){
		this.pack();
		this.setResizable(false);
		this.setSize(400,250);
		this.setTitle("Login");
	}

	public JPanel textPanel(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new GridLayout(4,2));
		dummy.add(label("Benutzername"));
		dummy.add(txtUsername());
		dummy.add(label("Passwort"));
		dummy.add(txtPassword());
		return dummy;
	}
	public JPanel buttonPanel(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new BoxLayout(dummy,BoxLayout.Y_AXIS));
		dummy.add(labelPanel(),BorderLayout.EAST);
		dummy.add(btnDummy("btnLogin","Login"));
		return dummy;
	}
	public JPanel labelPanel(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new BoxLayout(dummy, BoxLayout.Y_AXIS));
		dummy.add(lblUser());
		return dummy;
	}
	public JTextField txtUsername(){
		if(txtUsername == null){
			txtUsername = new JTextField();
		}
		txtUsername.setText("Benutzername");
		txtUsername.setMinimumSize(new Dimension(220,24));
		txtUsername.setMaximumSize(new Dimension(250,28));
		return txtUsername;
	}
	public JPasswordField txtPassword(){
		if(txtPassword == null){
			txtPassword = new JPasswordField();
		}
		txtPassword.setColumns(20);
		txtPassword.setEchoChar('*');
		txtPassword.setMinimumSize(new Dimension(220,24));
		txtPassword.setMaximumSize(new Dimension(250,28));
		return txtPassword;
	}
	public JButton btnDummy(String name,String text){
		JButton btnDummy = new JButton();
		btnDummy.addActionListener(new Multilistener());
		btnDummy.setText(text);
		btnDummy.setName(name);
		btnDummy.setMinimumSize(new Dimension(100,24));
		btnDummy.setMaximumSize(new Dimension(150,28));
		return btnDummy;
	}
	public JLabel lblUser(){
		if(lblUser == null){
			lblUser = new JLabel();
		}
		lblUser.setText("Gast");
		lblUser.setMinimumSize(new Dimension(180,24));
		lblUser.setMaximumSize(new Dimension(200,24));
		return lblUser;
	}
	public JLabel label(String text){
		JLabel dummy = new JLabel();
		dummy.setText(text);
		return dummy;
	}


	public class Multilistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() instanceof JButton) {
				if (((JButton) e.getSource()).getName().equals("btnLogin")) {
					login(txtUsername.getText(), passwortEinlesen(), hostname, hostport);
					lblUser.setText(getLogged());
					setUsername(getLogged());
				}
			}
		}
	}
	public String passwortEinlesen(){
		char[] zeichen = txtPassword.getPassword();
		String passwort = new String(zeichen);
		return passwort;
//    	JOptionPane.showMessageDialog(null, "Das Passwort ist: "+passwort);
	}

}
	



