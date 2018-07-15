package libs;

import database.Userdatabase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class OptionMaps extends JDialog{
	Userdatabase data = new Userdatabase();
	ProxyMaps proxy = new ProxyMaps();
//	private static OptionMaps options= null;
//	public static OptionMaps getOptionMaps() {
//		if (options == null) {
//			options = new OptionMaps();
//		}
//		return options;
//	}
	public OptionMaps(){
		fenstereigenschaften();
		fensterelemente();
		start();
	}

	private String osname,username,path;
	private String hostname,hostport,user,password,proxyhost,proxyport,proxyuser,proxypassword;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOsname() {
		return osname;
	}
	public void setOsname() {
		this.osname = System.getProperty("os.name");
	}
	public String getUsername() {
		return username;
	}
	public void setUsername() {
		this.username = System.getProperty("user.name");
	}
	public String getHostname(){
		return hostname;
	}
	public String getHostport(){
		return hostport;
	}

	public String getProxyport() {
		return proxyport;
	}

	public void setProxyport(String proxyport) {
		this.proxyport = proxyport;
	}

	public String getProxypassword() {
		return proxypassword;
	}

	public void setProxypassword(String proxypassword) {
		this.proxypassword = proxypassword;
	}

	public String getProxyuser() {
		return proxyuser;
	}

	public void setProxyuser(String proxyuser) {
		this.proxyuser = proxyuser;
	}

	public String getProxyhost() {
		return proxyhost;
	}

	public void setProxyhost(String proxyhost) {
		this.proxyhost = proxyhost;
	}

	public void setPath(){
		if(getOsname().contains("Windows")){
			this.path = "C:/Users/"+getUsername()+"/.options/";
		}
		else{
			this.path = "/home/"+getUsername()+"/.options/";
		}
	}
	public void back(){
		testErgebnis.setText("");
	}
	public String getPath(){
		return path;
	}
	public void start(){
		setOsname();
		setUsername();
		setPath();
		makedir();
		try {
			ladeWerte();
			zeigeWerte();
			proxy.setProxy(getProxyhost(),getProxyport(),getProxyuser(),getProxypassword());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void zeigeWerte(){
		txtHostname.setText(getHostname());
		txtHostport.setText(getHostport());
		txtProxyip.setText(getProxyhost());
		txtProxyport.setText(getProxyport());
		txtProxyuser.setText(getProxyuser());
		txtProxypw.setText(getProxypassword());
	}
	public void makedir(){
		File dir = new File(getPath());
		dir.mkdir();
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
	public void ladeWerte() throws IOException{
		String hostname = "";
		String hostport = "";
		String proxyhost = "";
		String proxyport = "";
		String proxyuser = "";
		String proxypw = "";
		try{
			hostname = read(getPath()+"hostname.txt");
			hostport = read(getPath()+"hostport.txt");
			proxyhost = read(getPath()+"proxyhost.txt");
			proxyport = read(getPath()+"proxyport.txt");
			proxyuser = read(getPath()+"proxyuser.txt");
			proxypw = read(getPath()+"proxypw.txt");
		}catch(Exception E){
			standartWerte();
			if(JOptionPane.showConfirmDialog(null,"Hast du das Programm zum ersten mal gestartet?")==0){
				setVisible(true);
			}
		}
		this.hostname = hostname;
		this.hostport = hostport;
		this.proxyhost = proxyhost;
		this.proxyport = proxyport;
		this.proxyuser = proxyuser;
		this.proxypassword = proxypw;
	}
	public void standartWerte() throws IOException{
		write(getPath()+"hostname.txt", "projbannasch");
		write(getPath()+"hostport.txt","3306");
		write(getPath()+"proxyhost.txt","");
		write(getPath()+"proxyport.txt","");
		write(getPath()+"proxyuser.txt","");
		write(getPath()+"proxypw.txt","");
	}
	public void speichereWerte(String hostname,String hostport,String proxyhost,String proxyport,String proxyuser,String proxypassword) throws IOException {
		write(getPath()+"hostname.txt",hostname);
		write(getPath()+"hostport.txt",hostport);
		write(getPath()+"proxyhost.txt",proxyhost);
		write(getPath()+"proxyport.txt",proxyport);
		write(getPath()+"proxyuser.txt",proxyuser);
		write(getPath()+"proxypw.txt",proxypassword);
	}

	public void error(){
		JOptionPane.showMessageDialog(null,"Fehler beim Speichern der Optionen!");
	}
	public void loaderror(){
		JOptionPane.showMessageDialog(null, "Fehler beim Laden der Optionen!");
	}

	JTextField txtHostname = null;
	JTextField txtHostport = null;
	JTextField txtProxyip = null;
	JTextField txtProxyport = null;
	JTextField txtProxyuser = null;
	JPasswordField txtProxypw = null;
	JLabel testErgebnis = null;
	public void fensterelemente(){
		this.add(panelOptionen(), BorderLayout.NORTH);
		this.add(panelButton(),BorderLayout.SOUTH);
		this.add(panelLabel(), BorderLayout.WEST);
	}
	public void fenstereigenschaften(){
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setTitle("Optionen");
		this.setSize(400,500);
	}
	public JPanel panelOptionen(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new BoxLayout(dummy,BoxLayout.Y_AXIS));
		dummy.add(panelHost());
		dummy.add(panelProxy());
		return dummy;
	}
	public JPanel panelHost(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new BoxLayout(dummy, BoxLayout.X_AXIS));
		dummy.add(label("Hostname"));
		dummy.add(txtHostname());
		dummy.add(txtHostport());
		return dummy;
	}
	public JPanel panelProxy(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new GridLayout(4,2));
		dummy.add(label("Proxy IP"));
		dummy.add(txtProxyip());
		dummy.add(label("Proxyport"));
		dummy.add(txtProxyport());
		dummy.add(label("Proxyuser"));
		dummy.add(txtProxyuser());
		dummy.add(label("Proxypasswort"));
		dummy.add(txtProxypw());
		return dummy;
	}
	public JPanel panelButton(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new GridLayout(2, 4));
		dummy.add(btnDummy("Speichern","btnSave"));
		dummy.add(btnDummy("Testverbindung","btnTest"));
		dummy.add(btnDummy("Abbrechen","btnAbort"));
		dummy.add(btnDummy("Standartwerte","btnDefault"));
		return dummy;
	}
	public JPanel panelLabel(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new BoxLayout(dummy, BoxLayout.Y_AXIS));
		dummy.add(testErgebnis());
		return dummy;
	}
	public JTextField txtHostname(){
		if(txtHostname == null){
			txtHostname = new JTextField();
		}
		txtHostname.setMinimumSize(new Dimension(340,24));
		txtHostname.setMaximumSize(new Dimension(380,28));
		return txtHostname;
	}
	public JTextField txtHostport(){
		if(txtHostport == null){
			txtHostport = new JTextField();
		}
		txtHostport.setMinimumSize(new Dimension(30,24));
		txtHostport.setMaximumSize(new Dimension(40,28));
		return txtHostport;
	}
	public JTextField txtProxyip(){
		if(txtProxyip == null){
			txtProxyip = new JTextField();
		}
		txtProxyip.setMinimumSize(new Dimension(340,24));
		txtProxyip.setMaximumSize(new Dimension(380,28));
		return txtProxyip;
	}
	public JTextField txtProxyport(){
		if(txtProxyport == null){
			txtProxyport = new JTextField();
		}
		txtProxyport.setMinimumSize(new Dimension(340,24));
		txtProxyport.setMaximumSize(new Dimension(380,28));
		return txtProxyport;
	}
	public JTextField txtProxyuser(){
		if(txtProxyuser == null){
			txtProxyuser = new JTextField();
		}
		txtProxyuser.setMinimumSize(new Dimension(340,24));
		txtProxyuser.setMaximumSize(new Dimension(380,28));
		return txtProxyuser;
	}
	public JPasswordField txtProxypw(){
		if(txtProxypw == null){
			txtProxypw = new JPasswordField();
		}
		txtProxypw.setEchoChar('-');
		txtProxypw.setMinimumSize(new Dimension(340,24));
		txtProxypw.setMaximumSize(new Dimension(380,28));
		return txtProxypw;
	}
	public JLabel label(String text){
		JLabel dummy = new JLabel();
		dummy.setText(text);
		dummy.setAlignmentX(RIGHT_ALIGNMENT);
		return dummy;
	}

	public JButton btnDummy(String text, String name){
	JButton dummy = new JButton();
	dummy.setText(text);
	dummy.setName(name);
	dummy.addActionListener(new Multilistener());
	dummy.setMinimumSize(new Dimension(100,24));
	dummy.setMaximumSize(new Dimension(150,28));
	return dummy;
	}
	public JLabel testErgebnis(){
		if(testErgebnis == null){
			testErgebnis = new JLabel();
		}
		return testErgebnis;
	}

	public class Multilistener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() instanceof JButton){
				if(((JButton) e.getSource()).getName().equals("btnSave")){
					try {
						speichereWerte(txtHostname.getText(),txtHostport.getText(),txtProxyip.getText(),txtProxyport.getText(),txtProxyuser.getText(),proxypwEinlesen());
						ladeWerte();
						txtHostname().setText(getHostname());
						start();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if(((JButton) e.getSource()).getName().equals("btnTest")){
					start();
					//log.testeVerbindung(mapoptions.getHostname(),mapoptions.getHostport());
//					log.testConnection(mapoptions.getHostname(), mapoptions.getHostport());
					data.testConnection(getHostname(),getHostport());
					testErgebnis.setText(data.getErgebnis());
				}
				if(((JButton) e.getSource()).getName().equals("btnAbort")){
					try {
						ladeWerte();
						zeigeWerte();
						setVisible(false);
					} catch (IOException e1) {
						loaderror();
					}

				}
				if(((JButton) e.getSource()).getName().equals("btnDefault")){
					try {
						standartWerte();
						ladeWerte();
						zeigeWerte();
					} catch (IOException e1) {
						error();
					}
				}
			}
		}
	}
	public String proxypwEinlesen(){
		char[] zeichen = txtProxypw.getPassword();
		String passwort = new String(zeichen);
		return passwort;
//    	JOptionPane.showMessageDialog(null, "Das Passwort ist: "+passwort);
	}
}


