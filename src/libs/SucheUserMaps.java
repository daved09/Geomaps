package libs;

import database.Cachedatabase;
import database.Userdatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by hbfit14bannasch on 30.01.16.
 */
public class SucheUserMaps extends JDialog{
    Userdatabase data = new Userdatabase();
    Cachedatabase chacheData = new Cachedatabase();
    private String hostname,hostport;
    private boolean start = false;
    private String user="gast";
    private String[] caches;

    public SucheUserMaps(){
        fensterelemente();
        fenstereigenschaften();
    }

    public void zuruecksetzen(){
        destroy();
        txtUser.setText("");
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

    public void ladeUser(String hostname,String hostport){
        data.setHostname(hostname);
        data.setHostport(hostport);
        try {
            data.ladeUser(txtUser.getText(),getHostname(),getHostport());
            labelUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Benutzer nicht gefunden");
            e.printStackTrace();
        }
    }

    public void listeCaches(){
        try {
            chacheData.listCache(txtUser.getText(),getHostname(),getHostport());
            caches = new String[chacheData.getEnd()];
            for(int i=0;i< caches.length;i++){
                caches[i] = chacheData.caches(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void labelUpdate(){
        lblVorname.setText("Vorname: "+data.getVorname());
        lblnachname.setText("Nachname: "+data.getNachname());
        lblEmail.setText("Email: "+data.getEmail());
        listeCaches();

    }

    public void reset(){
    }

    public void destroy(){
        panel.removeAll();
        remove(panel);
        panel.revalidate();
        panel.repaint();
        update(getGraphics());
        labels();
    }

    public void destroyCaches(){
        panelCaches.removeAll();
        remove(panelCaches);
        panelCaches.revalidate();
        panelCaches.repaint();
        update(getGraphics());
    }


    JPanel panel = null;
    JPanel panelCaches = null;
    JTextField txtUser = null;
    JLabel lblVorname = null;
    JLabel lblnachname = null;
    JLabel lblEmail = null;
    public void fensterelemente(){
        this.add(panelText(),BorderLayout.NORTH);
        labels();
    }
    public void labels(){
        this.add(panel(),BorderLayout.CENTER);
    }

    public void zeigeCaches(){
        this.add(panelCaches(),BorderLayout.SOUTH);
    }

    public void fenstereigenschaften(){
        this.pack();
        this.setSize(400,400);
        this.setTitle("Suche Benutzer");
        this.setResizable(false);
    }
    public JPanel panelText(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy,BoxLayout.X_AXIS));
        dummy.add(txtUser());
        dummy.add(button("btnSuche","Suche"));
        return dummy;
    }

    public JPanel panel(){
        if(panel == null){
            panel = new JPanel();
        }
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(lblVorname());
        panel.add(lblnachname());
        panel.add(lblEmail());
        panel.add(label("Geocaches:"));
        return panel;
    }

    public JPanel panelCaches(){
        if(panelCaches == null){
            panelCaches = new JPanel();
        }
        panelCaches.setLayout(new BoxLayout(panelCaches,BoxLayout.Y_AXIS));
        for(int i=0;i<caches.length;i++){
            panel.add(label(caches[i]));
        }
        return panelCaches;
    }


    public JTextField txtUser(){
        if(txtUser == null){
            txtUser = new JTextField();
        }
        txtUser.setMinimumSize(new Dimension(100,24));
        txtUser.setMaximumSize(new Dimension(150,32));
        return txtUser;
    }

    public JButton button(String name,String text){
        JButton dummy = new JButton();
        dummy.setName(name);
        dummy.setText(text);
        dummy.addActionListener(new Listener());
        return dummy;
    }

    public JLabel label(String text){
        JLabel dummy = new JLabel();
        dummy.setText(text);
        return dummy;
    }

    public JLabel lblVorname(){
        if(lblVorname == null){
            lblVorname = new JLabel();
        }
        lblVorname.setText("Vorname: ");
        return lblVorname;
    }
    public JLabel lblnachname(){
        if(lblnachname == null){
            lblnachname = new JLabel();
        }
        lblnachname.setText("Nachname: ");
        return lblnachname;
    }
    public JLabel lblEmail(){
        if(lblEmail == null){
            lblEmail = new JLabel();
        }
        lblEmail.setText("Email: ");
        return lblEmail;
    }

    public void frameUpdate(){
        this.update(this.getGraphics());
    }

    public class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() instanceof JButton){
                if(((JButton) e.getSource()).getName().equals("btnSuche")){
                    if(txtUser.getText().contains("Admin")){
                        JOptionPane.showMessageDialog(null,"Der Administrator kann nicht gesucht werden.");
                    }
                    else if (txtUser.getText().contains("admin")){
                        JOptionPane.showMessageDialog(null,"Der administrator kann nicht gesucht werden.");
                    }
                    else{
                        try{
                            if(panelCaches != null){
                                destroyCaches();
                            }
                            if(panel != null){
                                destroy();
                            }
                            ladeUser(getHostname(),getHostport());
                            zeigeCaches();
                            panel.revalidate();
                            panelCaches.revalidate();
                        }
                        catch(Exception E){
                            destroy();
                            destroyCaches();
                            labelUpdate();
                            labels();
                            panelCaches.revalidate();
                            frameUpdate();
                            E.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
