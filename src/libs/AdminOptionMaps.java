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

public class AdminOptionMaps extends JDialog{
    public AdminOptionMaps(){
        fenstereigenschaften();
        fensterelemente();
        start();
    }
    public AdminOptionMaps(String database){
        startdb();
    }

    private String osname,username,path;
    private String dbname,dbuser,dbpw;
    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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

    public void setPath(){
        if(getOsname().contains("Windows")){
            this.path = "C:/Users/"+getUsername()+"/.adminoptions/";
        }
        else{
            this.path = "/home/"+getUsername()+"/.adminoptions/";
        }
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }

    public String getDbpw() {
        return dbpw;
    }

    public void setDbpw(String dbpw) {
        this.dbpw = dbpw;
    }

    public void back(){
        testErgebnis.setText("");
    }
    public String getPath(){
        return path;
    }
    public void start() {
        setOsname();
        setUsername();
        setPath();
        makedir();
        try {
            ladeWerte();
            zeigeWerte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startdb(){
        setOsname();
        setUsername();
        setPath();
        makedir();
        try {
            ladeWerte();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void zeigeWerte(){
        try {
            txtDbname.setText(read(getPath()+"dbname.txt"));
            txtDbuser.setText(read(getPath()+"dbuser.txt"));
            txtDbpw.setText(read(getPath()+"dbpw.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String dbname="",dbuser="",dbpw="";
        try{
            dbname = read(getPath()+"dbname.txt");
            dbuser = read(getPath()+"dbuser.txt");
            dbpw = read(getPath()+"dbpw.txt");
        }catch(Exception E){
            standartWerte();
        }
        setDbname(dbname);
        setDbuser(dbuser);
        setDbpw(dbpw);
    }
    public void standartWerte() throws IOException{
        write(getPath()+"dbname.txt","geomaps");
        write(getPath()+"dbuser.txt","geomaps");
        write(getPath()+"dbpw.txt","maps");
    }
    public void speichereWerte() throws IOException {
        write(getPath()+"dbname.txt",txtDbname.getText());
        write(getPath()+"dbuser.txt",txtDbuser.getText());
        write(getPath()+"dbpw.txt",dbpwEinlesen());
    }
    public void pruebeAdmin(){
        if(isAdmin()){
            txtDbname.setEnabled(true);
            txtDbuser.setEnabled(true);
            txtDbpw.setEnabled(true);
        }
        else{
            txtDbname.setEnabled(false);
            txtDbuser.setEnabled(false);
            txtDbpw.setEnabled(false);
            this.add(label("Für diese Einstellungen benötigst du Adminrechte."));
        }
    }

    public void error(){
        JOptionPane.showMessageDialog(null,"Fehler beim Speichern der Optionen!");
    }
    public void loaderror(){
        JOptionPane.showMessageDialog(null, "Fehler beim Laden der Optionen!");
    }


    JTextField txtDbname = null;
    JTextField txtDbuser = null;
    JPasswordField txtDbpw = null;
    JLabel testErgebnis = null;
    public void fensterelemente(){
        this.add(panelOptionen(), BorderLayout.NORTH);
        this.add(panelButton(),BorderLayout.SOUTH);
    }
    public void fenstereigenschaften(){
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setTitle("Adminoptionen");
        this.setSize(400,500);
    }
    public JPanel panelOptionen(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy,BoxLayout.Y_AXIS));
        dummy.add(panelDatabase());
        return dummy;
    }
    public JPanel panelDatabase(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new GridLayout(4,2));
        dummy.add(label("Datenbankname "));
        dummy.add(txtDbname());
        dummy.add(label("Datenbankbenutzer"));
        dummy.add(txtDbuser());
        dummy.add(label("Datenbankpasswort"));
        dummy.add(txtDbpw());
        return dummy;
    }
    public JPanel panelButton(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new GridLayout(2, 4));
        dummy.add(btnDummy("Speichern","btnSave"));
        dummy.add(btnDummy("Abbrechen","btnAbort"));
        dummy.add(btnDummy("Standartwerte","btnDefault"));
        return dummy;
    }

    public JLabel label(String text){
        JLabel dummy = new JLabel();
        dummy.setText(text);
        dummy.setAlignmentX(RIGHT_ALIGNMENT);
        return dummy;
    }
    public JTextField txtDbname(){
        if(txtDbname == null){
            txtDbname = new JTextField();
        }
        txtDbname.setMinimumSize(new Dimension(340,24));
        txtDbname.setMaximumSize(new Dimension(380,28));
        return txtDbname;
    }
    public JTextField txtDbuser(){
        if(txtDbuser == null){
            txtDbuser = new JTextField();
        }
        txtDbuser.setMinimumSize(new Dimension(340,24));
        txtDbuser.setMaximumSize(new Dimension(380,28));
        return txtDbuser;
    }
    public JPasswordField txtDbpw(){
        if(txtDbpw == null){
            txtDbpw = new JPasswordField();
        }
        txtDbpw.setMinimumSize(new Dimension(340,24));
        txtDbpw.setMaximumSize(new Dimension(380,28));
        return txtDbpw;
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


    public class Multilistener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() instanceof JButton){
                if(((JButton) e.getSource()).getName().equals("btnSave")){
                    try {
                        speichereWerte();
                        ladeWerte();
                        zeigeWerte();
                        start();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
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
    public String dbpwEinlesen(){
        char[] zeichen = txtDbpw.getPassword();
        String passwort = new String(zeichen);
        return passwort;
//    	JOptionPane.showMessageDialog(null, "Das Passwort ist: "+passwort);
    }
}


