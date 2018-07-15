package libs;

import database.Cachedatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by hbfit14bannasch on 29.01.16.
 */
public class CacheListeMaps extends JDialog{
    Cachedatabase data = new Cachedatabase();
    private boolean start = false;
    private String user="gast";
    private String[] caches;
    private String hostname,hostport;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public String getHostport() {
        return hostport;
    }

    public void setHostport(String hostport) {
        this.hostport = hostport;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void dataUpdate2(String user, String hostname, String hostport){
        setHostname(hostname);
        setHostport(hostport);
        listeCaches();
        fensterelemente();
    }

    public void dataUpdate(String user, String hostname, String hostport){
        setHostname(hostname);
        setHostport(hostport);
        listeCaches();
        if(!isStart()){
            this.add(panelUser(), BorderLayout.NORTH);
        }
        setStart(true);
    }
    public CacheListeMaps(String username,String hostname,String hostport){
        setHostname(hostname);
        setHostport(hostport);
        setUser(username);
        fenstereigenschaften();
    }

    public void cacheList(){

        for(int i=0;i<caches.length;i++){
            panelCaches.add(label(caches[i],caches[i]));
        }


    }

    public void destroy(){
        //panelUser.removeAll();
        panelCaches.removeAll();
        //remove(panelUser);
        remove(panelCaches);
        //panelUser.revalidate();
        panelCaches.revalidate();
        //panelUser.repaint();
        panelCaches.repaint();
        this.update(this.getGraphics());
    }//Quelle: Stackoverflow

    public void listeCaches(){
        try {
            data.listCache(getUser(),getHostname(),getHostport());
            caches = new String[data.getEnd()];
            for(int i=0;i< caches.length;i++){
                caches[i] = data.caches(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fenstereigenschaften(){
        this.pack();
        this.setTitle("Meine Caches");
        this.setSize(700,500);
        this.setResizable(false);
    }

    public void fensterelemente(){
        this.add(panelCaches(),BorderLayout.CENTER);
    }
    JTextField txtUsername = null;
    JPanel panelUser = null;
    JPanel panelCaches = null;
    JLabel lblUser = null;
    public JPanel panelUser(){
        if(panelUser == null){
            panelUser = new JPanel();
        }
        panelUser.setLayout(new BoxLayout(panelUser,BoxLayout.X_AXIS));
        //panelUser.add(lblUser(getUser()));
        panelUser.add(txtUser());
        panelUser.add(button("btnUser","suche"));
        return panelUser;
    }

    public JPanel panelCaches(){
        if(panelCaches == null){
            panelCaches = new JPanel();
        }
        panelCaches.setLayout(new BoxLayout(panelCaches,BoxLayout.Y_AXIS));
        for(int i=0;i<caches.length;i++){
            panelCaches.add(label(caches[i],caches[i]));
        }
        return panelCaches;
    }

    public JLabel lblUser(String text){
        lblUser = new JLabel();
        lblUser.setText(text);
        return lblUser;
    }

    public JTextField txtUser(){
        if(txtUsername == null){
            txtUsername = new JTextField();
        }
        txtUsername.setMinimumSize(new Dimension(150,24));
        txtUsername.setMaximumSize(new Dimension(250,32));
        return txtUsername;
    }

    public JLabel label(String text,String name){
        JLabel dummy = new JLabel();
        dummy.setName(name);
        dummy.setText(text);
        return dummy;
    }

    public JButton button(String name,String text){
        JButton dummy = new JButton();
        dummy.setName(name);
        dummy.setText(text);
        dummy.addActionListener(new Listener());
        return dummy;
    }

    public class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() instanceof JButton){
                if(((JButton) e.getSource()).getName().equals("btnUser")){
                    if(panelCaches != null){
                        destroy();
                    }
                    setUser(txtUsername.getText());
                    dataUpdate(getUser(),getHostname(),getHostport());
                    fensterelemente();
                    panelCaches.revalidate();
                }
            }
        }
    }

}
