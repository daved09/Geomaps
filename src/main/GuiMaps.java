package main;
import libs.*;
import database.*;
import maps.MapMaps;
import maps.ZeigeCaches;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

/**
 * Created by David on 17.01.2016.
 */
public class GuiMaps extends JFrame{
    VersionMaps version = new VersionMaps();
    Database data = new Database();
    AdminOptionMaps admin = new AdminOptionMaps();
    OptionMaps options = new OptionMaps();
    RegisterMaps register = new RegisterMaps();
    MapMaps map = new MapMaps();
    ZeigeCaches zeige = new ZeigeCaches();
    Thread th = new Thread(new Waypoints());
    public static void main(String[] args) throws IOException{
        GuiMaps gui = new GuiMaps();
        gui.gui();
        gui.eigenschaften();
    }
    boolean reload = true;
    public void gui() throws IOException{
        th.start();
        this.setJMenuBar(menubar());
        this.add(hauptpanel(),BorderLayout.CENTER);
        this.setVisible(true);
        data.loadDriver();
//        this.databaseConnect();
    }
    public void setCacheWaypoints(){
        zeige.ladeCaches(options.getHostname(),options.getHostport());
        for(int i=0;i<zeige.laenge();i++){
            map.waypointAdd(zeige.zeigel(i),zeige.zeigeb(i));
        }
    }//Setzt beim Start des Programms die Wegpunkte aus der Datenbank
    LoginMaps login = new LoginMaps(options.getHostname(),options.getHostport(),options.getUser(),options.getPassword());
    CacheMaps cache = new CacheMaps(login.getLogged(),login.getPassword(),login.getHostname(),login.getHostport());
    CacheLoadMaps cachel = new CacheLoadMaps(options.getHostname(),options.getHostport());
    SucheUserMaps user = new SucheUserMaps();
    UserDeleteMaps del = new UserDeleteMaps();
    public void eigenschaften(){
        this.pack();
        this.setSize(1000,700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Geomaps");
    }

    JMenuItem oprionenOptionen;
    JMenuItem adminOprionenOptionen;
    JMenuItem dateiLoad;
    JMenuItem dateiNew;
    JMenuItem dateiReload;
    JMenuItem dateiClear;
    JMenuItem dateiClose;
    JMenuItem accountLogin;
    JMenuItem accountRegister;
    JMenuItem accountSuche;
    JMenuItem accoundDelete;
    JMenuItem hilfeHelp;
    JMenuItem hilfeAbout;
    public JMenuBar menubar(){
        JMenuBar menuleiste = new JMenuBar();
        JMenu menuDatei = new JMenu("Datei");
        JMenu menuOptionen = new JMenu("Bearbeiten");
        JMenu menuAccount = new JMenu("Account");
        JMenu menuHilfe = new JMenu("Hilfe");
        menuleiste.add(menuDatei);
        menuleiste.add(menuOptionen);
        menuleiste.add(menuAccount);
        menuleiste.add(menuHilfe);
        //Dateimenü
        menuDatei.add(menuDateiNew());
        menuDatei.add(menuDateiLoad());
        menuDatei.add(menuDateiReload());
        menuDatei.add(menuDateiClose());
        //Optionsmenü
        menuOptionen.add(menuOptionsOptions());
        menuOptionen.add(menuAdminOprionenOptionen());
        //Accountmenü
        menuAccount.add(menuAccountLogin());
        menuAccount.add(menuAccountRegister());
        menuAccount.add(menuAccountSuche());
        menuAccount.add(menuAccoundDelete());
        //Hilfemenü
        menuHilfe.add(menuHilfeHelp());
        menuHilfe.add(menuHilfeAbout());
        return menuleiste;
    }
    public JMenuItem menuDateiClose(){
        if(dateiClose == null){
            dateiClose = new JMenuItem();
        }
        dateiClose.addActionListener(new Menulistener());
        dateiClose.setText("Beenden");
        return dateiClose;
    }
    public JMenuItem menuDateiLoad(){
        if(dateiLoad == null){
            dateiLoad = new JMenuItem();
        }
        dateiLoad.addActionListener(new Menulistener());
        dateiLoad.setText("Laden");
        return dateiLoad;
    }
    public JMenuItem menuDateiReload(){
        if(dateiReload == null){
            dateiReload = new JMenuItem();
        }
        dateiReload.addActionListener(new Menulistener());
        dateiReload.setText("Map neu laden");
        return dateiReload;
    }
    public JMenuItem menuDateiClear(){
        if(dateiClear == null){
            dateiClear = new JMenuItem();
        }
        dateiClear.addActionListener(new Menulistener());
        dateiClear.setText("Clear Map");
        return dateiClear;
    }
    public JMenuItem menuDateiNew(){
        if(dateiNew == null){
            dateiNew = new JMenuItem();
        }
        dateiNew.addActionListener(new Menulistener());
        dateiNew.setText("Neu");
        return dateiNew;
    }
    public JMenuItem menuOptionsOptions(){
        if(oprionenOptionen == null){
            oprionenOptionen = new JMenuItem();
        }
        oprionenOptionen.addActionListener(new Menulistener());
        oprionenOptionen.setText("Optionen");
        return oprionenOptionen;
    }
    public JMenuItem menuAdminOprionenOptionen(){
        if(adminOprionenOptionen == null){
            adminOprionenOptionen = new JMenuItem();
        }
        adminOprionenOptionen.addActionListener(new Menulistener());
        adminOprionenOptionen.setText("Adminoptionen");
        return adminOprionenOptionen;
    }
    public JMenuItem menuAccountLogin(){
        if(accountLogin == null){
            accountLogin = new JMenuItem();
        }
        accountLogin.addActionListener(new Menulistener());
        accountLogin.setText("Login");
        return accountLogin;
    }

    public JMenuItem menuAccountRegister(){
        if(accountRegister == null){
            accountRegister = new JMenuItem();
        }
        accountRegister.addActionListener(new Menulistener());
        accountRegister.setText("Registrieren");
        return accountRegister;
    }

    public JMenuItem menuAccountSuche(){
        if(accountSuche == null){
            accountSuche = new JMenuItem();
        }
        accountSuche.addActionListener(new Menulistener());
        accountSuche.setText("Suche Benutzer");
        return accountSuche;
    }

    public JMenuItem menuAccoundDelete(){
        if(accoundDelete == null){
            accoundDelete = new JMenuItem();
        }
        accoundDelete.addActionListener(new Menulistener());
        accoundDelete.setText("Lösche Benutzer");
        return accoundDelete;
    }

    public JMenuItem menuHilfeHelp(){
        if(hilfeHelp == null){
            hilfeHelp = new JMenuItem();
        }
        hilfeHelp.addActionListener(new Menulistener());
        hilfeHelp.setText("Hilfe");
        return hilfeHelp;
    }

    public JMenuItem menuHilfeAbout(){
        if(hilfeAbout == null){
            hilfeAbout = new JMenuItem();
        }
        hilfeAbout.addActionListener(new Menulistener());
        hilfeAbout.setText("Über");
        return hilfeAbout;
    }


    //Panels
    public JPanel hauptpanel(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy,BoxLayout.Y_AXIS));
        //dummy.add(labelDummy("bild"));
        map.setSize(new Dimension(getWidth(),getHeight()));
        dummy.add(map.panel(getWidth(),getHeight()));
        dummy.setBounds(100,100,400,400);
        return dummy;
    }


    public JLabel labelDummy(String name){
        JLabel dummy = new JLabel();
        dummy.setName(name);
        dummy.setIcon(new ImageIcon(options.getPath()+"maps.png"));
        return dummy;
    }

    //Menübarlistener
    public class Menulistener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() instanceof JMenuItem){
                if(e.getSource().equals(oprionenOptionen)){
                    options.back();
                    options.setVisible(true);
                }
                if(e.getSource().equals(adminOprionenOptionen)){
                    if(login.getLogged().contains("Admin")){
                        admin.setAdmin(true);
                    }
                    else{
                        admin.setAdmin(false);
                    }
                    admin.pruebeAdmin();
                    admin.setVisible(true);
                }
                if(e.getSource().equals(dateiClose)){
                    System.exit(0);
                }
                if(e.getSource().equals(accountLogin)){
                    login.reset();
                    login.dataUpdate();
                    login.setVisible(true);
                }
                if(e.getSource().equals(dateiNew)){
                    if(login.getLogged().equals("gast")){
                        JOptionPane.showMessageDialog(null,"Du musst eingeloggt sein um Geocaches hinzu zu fügen.");
                    }
                    else {
                        cache.reset();
                        cache.dataUpdate(login.getLogged(),login.getPassword(),login.getHostname(),login.getHostport());
                        cache.setVisible(true);
                    }
                }
                if(e.getSource().equals(dateiLoad)){
                    cachel.reset();
                    cachel.dataUpdate(options.getHostname(),options.getHostport(),login.getLogged());
                    cachel.setVisible(true);
                }

                if(e.getSource().equals(accountRegister)){
                    register.reset();
                    register.setHostname(options.getHostname());
                    register.setHostport(options.getHostport());
                    register.setVisible(true);
                }
                if(e.getSource().equals(accountSuche)){
                    user.setHostname(options.getHostname());
                    user.setHostport(options.getHostport());
                    user.zuruecksetzen();
                    user.setVisible(true);
                }
                if(e.getSource().equals(accoundDelete)){
                    if(login.getLogged().equals("Administrator")){
                        del.reset();
                        del.dataUpdate(options.getHostname(),options.getHostport(),login.getPassword());
                        del.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Dieser Bereich ist nur für Administratoren zulässig.");
                    }
                }
                if(e.getSource().equals(dateiReload)){
                    reload = true;
                }
                if(e.getSource().equals(hilfeAbout)){
                    version.setVisible(true);
                }
            }
        }
    }


    public class Waypoints implements Runnable{
        public void run(){
            while (true){
                if(reload){
                    try{
                        map.clearc();
                        setCacheWaypoints();
                        reload = false;
                    }
                    catch (Exception E){
                        E.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Es konnten keine Caches in die Map geladen werden.");
                        reload = false;
                    }
                }
                if (cache.isReload()){
                    reload = true;
                    cache.setReload(false);
                }
                try {
                    Thread.sleep(1000/30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }//Lädt die Map neu, wenn ein neuer Cache erstellt oder gelöscht wird.
}
