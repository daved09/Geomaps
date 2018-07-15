package libs;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

import database.*;
public class CacheMaps extends JDialog{
	Cachedatabase data = new Cachedatabase();

	private String user,password,hostname,hostport;
	private int lGrad,bGrad;
	private boolean reload = false;
	public CacheMaps(String user,String password,String hostname,String hostport){
		setUser(user);
		setPassword(password);
		setHostname(hostname);
		setHostport(hostport);
		fensterelemente();
		fenstereigenschaften();
	}

	public void dataUpdate(String user,String password,String hostname,String hostport){
		setUser(user);
		setPassword(password);
		setHostname(hostname);
		setHostport(hostport);
	}

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

	public boolean isReload() {
		return reload;
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public void createCache(String name, String owner, String description, String lGrad, String bGrad, String hostname, String hostport){
		try{
			float lGradz = Float.parseFloat(lGrad);
			float bGradz = Float.parseFloat(bGrad);
			data.registerCache(name,owner,description,lGradz,bGradz,hostname,hostport);
			JOptionPane.showMessageDialog(null,"Cache erfolgreich erstellt.");
		}
		catch(Exception E){
			JOptionPane.showMessageDialog(null,"Fehler beim erstellen von dem Geocache.");
			E.printStackTrace();
		}
	}

	public void reset(){
		txtCacheName.setText("");
		txtDescription.setText("");
		txtCacheBgrad.setText("");
		txtCacheLgrad.setText("");
	}

	public int testeAnzahl(){
		int zahl=0;
		String text = txtDescription.getText();
		char[] zeichen = new char[text.length()];
		zeichen = text.toCharArray();
		for(int i=0;i<zeichen.length;i++){
			zahl++;
		}
		return zahl;
	}


	JTextField txtCacheName = null;
	JTextArea txtDescription = null;
	JTextField txtCacheLgrad = null;
	JTextField txtCacheBgrad = null;
	JButton btnCreateCache = null;
	public void fenstereigenschaften(){
		this.pack();
		this.setSize(500, 500);
		this.setResizable(false);
		this.setTitle("Erstelle Cache");
	}
	public void fensterelemente(){
		this.add(panelTextfelder(), BorderLayout.NORTH);
		this.add(panelDesc(),BorderLayout.CENTER);
		this.add(panelButton(), BorderLayout.SOUTH);
	}
	public JPanel panelTextfelder(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new GridLayout(4,2));
		dummy.add(label("Cachename: "));
		dummy.add(txtCacheName());
		dummy.add(label("Längengrad: "));
		dummy.add(txtCacheLgrad());
		dummy.add(label("Breitengrad: "));
		dummy.add(txtCacheBgrad());
		return dummy;
	}
	public JPanel panelButton(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new BoxLayout(dummy, BoxLayout.Y_AXIS));
		dummy.add(btnCreateCache());
		return dummy;
	}
	public JPanel panelDesc(){
		JPanel dummy = new JPanel();
		dummy.setLayout(new BoxLayout(dummy, BoxLayout.Y_AXIS));
		dummy.add(label("Cachebeschreibung"));
		dummy.add(txtDescription());
		return dummy;
	}
	public JLabel label(String text){
		JLabel dummy = new JLabel();
		dummy.setText(text);
		dummy.setMaximumSize(new Dimension(160,20));
		dummy.setAlignmentX(Component.RIGHT_ALIGNMENT);
		return dummy;
	}
	public JTextField txtCacheName(){
		if(txtCacheName == null){
			txtCacheName = new JTextField();
		}
		txtCacheName.setMinimumSize(new Dimension(200,24));
		txtCacheName.setMaximumSize(new Dimension(480,32));
		return txtCacheName;
	}
	public JTextArea txtDescription(){
		if(txtDescription == null){
			txtDescription = new JTextArea();
		}
		txtDescription.setMinimumSize(new Dimension(480, 200));
		txtDescription.setMaximumSize(new Dimension(480, 200));
		return txtDescription;
	}
	public JTextField txtCacheLgrad(){
		if(txtCacheLgrad == null){
			txtCacheLgrad = new JTextField();
		}
		txtCacheLgrad.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtCacheLgrad.setMinimumSize(new Dimension(200,24));
		txtCacheLgrad.setMaximumSize(new Dimension(250,32));
		return txtCacheLgrad;
	}
	public JTextField txtCacheBgrad(){
		if(txtCacheBgrad == null){
			txtCacheBgrad = new JTextField();
		}
		txtCacheBgrad.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtCacheBgrad.setMinimumSize(new Dimension(200,24));
		txtCacheBgrad.setMaximumSize(new Dimension(250,32));
		return txtCacheBgrad;
	}
	public JButton btnCreateCache(){
		if(btnCreateCache == null){
			btnCreateCache = new JButton();
		}
		btnCreateCache.addActionListener(new Listener());
		btnCreateCache.setText("Erstelle Cache");
		btnCreateCache.setMinimumSize(new Dimension(120,28));
		btnCreateCache.setMaximumSize(new Dimension(200,32));
		return btnCreateCache;
	}


	public class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource() instanceof JButton){
				if(e.getSource().equals(btnCreateCache)){
					if(testeAnzahl()>500){
						JOptionPane.showMessageDialog(null,"Fehler: Zu viele Zeichen in der Beschreibung.");
					}
					else {
						createCache(txtCacheName.getText(),getUser(),txtDescription.getText(),
								txtCacheLgrad.getText(),txtCacheBgrad.getText(),getHostname(),getHostport());
						setVisible(false);
						setReload(true);
					}
					reset();

				}
			}

		}
	}

}
