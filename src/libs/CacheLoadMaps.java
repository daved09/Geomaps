package libs;

import database.Cachedatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by David on 28.01.16.
 */
public class CacheLoadMaps extends JDialog {
    Cachedatabase data = new Cachedatabase();
    ListeCacheMaps liste = new ListeCacheMaps();

    private String hostname, hostport;
    private String logged = "gast";
    private float lgrad,bgrad;

    public CacheLoadMaps(String hostname, String hostport) {
        setHostname(hostname);
        setHostport(hostport);
        liste.dataUpdate(hostname,hostport);
        fensterelemente();
        fenstereigenschaften();
    }


    public float getLgrad() {
        return lgrad;
    }

    public void setLgrad(float lgrad) {
        this.lgrad = lgrad;
    }

    public float getBgrad() {
        return bgrad;
    }

    public void setBgrad(float bgrad) {
        this.bgrad = bgrad;
    }

    public String getLogged() {
        return logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
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

    public void dataUpdate(String hostname, String hostport, String logged) {
        setHostname(hostname);
        setHostport(hostport);
        setLogged(logged);
    }//Aktuallisiert die Daten

    public void cacheLoad(String name, String hostname, String hostport) {
        try {
            data.loadCache(name, hostname, hostport);
            setLgrad(data.getlGrad());
            setBgrad(data.getbGrad());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Geocache nicht gefunden.");
            e.printStackTrace();
        }
    }//Lädt die Cachedaten

    public void cacheDelete(String name, String hostname, String hostport) throws IOException {
        try {
            data.deleteCache(name, hostname, hostport);//Zugriff auf die Cachedatenbank
            JOptionPane.showMessageDialog(null,"Cache wurde erfolgreich gelöscht.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Geocache konnte nicht gelöscht werden");
            e.printStackTrace();
        }
    }//LÖscht einen Cache
    public void cacheUpdate(String name,String description,String hostname,String hostport){
        try{
            data.updateCache(name,description,hostname,hostport);//Zugriff auf die Cachedatenbank
            JOptionPane.showMessageDialog(null,"Cachedaten erfolgreich aktualisiert.");
        }
        catch (Exception E){
            JOptionPane.showMessageDialog(null,"Fehler beim aktualisieren der Cachedaten.");
            E.printStackTrace();
        }
    }//Aktualisiert die Cachedaten

    public void reset(){
        txtLoadCache.setText(data.getName());
        lblLoadCacheOwner.setText("Owner: ");
        txtLoadCacheDescription.setText(data.getDescription());
        lblLoadCacheLgrad.setText("Längengrad: "+Float.toString(data.getlGrad()));
        lblLoadCacheBgrad.setText("Breitengrad: "+Float.toString(data.getbGrad()));
    }//Setzt alle Felder zurück


    JTextField txtLoadCache = null;
    JLabel lblLoadCacheName = null;
    JLabel lblLoadCacheOwner = null;
    JLabel lblLoadCacheDescription = null;
    JLabel lblLoadCacheLgrad = null;
    JLabel lblLoadCacheBgrad = null;
    JTextArea txtLoadCacheDescription = null;

    public void fenstereigenschaften() {
        this.pack();
        this.setTitle("Lade Cache");
        this.setSize(500, 500);
        this.setResizable(false);
    }

    public void fensterelemente() {
        this.add(panelLabel(), BorderLayout.NORTH);
        this.add(panelDesc(), BorderLayout.CENTER);
        this.add(panelButton(), BorderLayout.SOUTH);
    }

    public JPanel panelLabel() {
        JPanel dummy = new JPanel();
        dummy.setLayout(new GridLayout(4,2));
        dummy.add(lblLoadCacheName());
        dummy.add(txtLoadCache());
        dummy.add(lblLoadCacheOwner());
        dummy.add(lblLoadCacheLgrad());
        dummy.add(new JLabel());
        dummy.add(lblLoadCacheBgrad());
        return dummy;
    }

    public JPanel panelButton() {
        JPanel dummy = new JPanel();
        dummy.setLayout(new GridLayout(2, 1));
        dummy.add(button("btnLoadCache","Cache laden"));
        dummy.add(button("btnListeCache","Cacheliste"));
        dummy.add(button("btnDeleteCache","Lösche Cache"));
        dummy.add(button("btnUpdateCache","Änderung speichern"));
        return dummy;
    }

    public JPanel panelDesc(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy,BoxLayout.Y_AXIS));
        dummy.add(lblLoadCacheDescription());
        dummy.add(txtLoadCacheDescription());
        return dummy;
    }

    public JLabel lblLoadCacheName() {
        if (lblLoadCacheName == null) {
            lblLoadCacheName = new JLabel();
        }
        lblLoadCacheName.setText("Cachename: ");
        lblLoadCacheName.setAlignmentX(RIGHT_ALIGNMENT);
        lblLoadCacheName.setMaximumSize(new Dimension(160, 20));
        return lblLoadCacheName;
    }

    public JTextField txtLoadCache() {
        if (txtLoadCache == null) {
            txtLoadCache = new JTextField();
        }
        txtLoadCache.setMinimumSize(new Dimension(200, 24));
        txtLoadCache.setMaximumSize(new Dimension(400, 32));
        return txtLoadCache;
    }

    public JLabel lblLoadCacheOwner() {
        if (lblLoadCacheOwner == null) {
            lblLoadCacheOwner = new JLabel();
        }
        lblLoadCacheOwner.setText("Owner: ");
        lblLoadCacheOwner.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblLoadCacheName.setMaximumSize(new Dimension(160, 20));
        return lblLoadCacheOwner;
    }

    public JLabel lblLoadCacheDescription() {
        if (lblLoadCacheDescription == null) {
            lblLoadCacheDescription = new JLabel();
        }
        lblLoadCacheDescription.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblLoadCacheDescription.setText("Beschreibung");
        return lblLoadCacheDescription;
    }
    public JLabel lblLoadCacheLgrad(){
        if(lblLoadCacheLgrad == null){
            lblLoadCacheLgrad = new JLabel();
        }
        lblLoadCacheLgrad.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLoadCacheLgrad.setText("Längengrad: ");
        return lblLoadCacheLgrad;
    }
    public JLabel lblLoadCacheBgrad(){
        if(lblLoadCacheBgrad == null){
            lblLoadCacheBgrad = new JLabel();
        }
        lblLoadCacheBgrad.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblLoadCacheBgrad.setText("Breitengrad: ");
        return lblLoadCacheBgrad;
    }

    public JTextArea txtLoadCacheDescription(){
        if(txtLoadCacheDescription == null){
            txtLoadCacheDescription = new JTextArea();
        }
        txtLoadCacheDescription.setMinimumSize(new Dimension(480, 200));
        txtLoadCacheDescription.setMaximumSize(new Dimension(480, 200));
        return txtLoadCacheDescription;
    }

    public JButton button(String name,String text) {
        JButton dummy = new JButton();
        dummy.addActionListener(new Listener());
        dummy.setName(name);
        dummy.setText(text);
        dummy.setMinimumSize(new Dimension(100, 28));
        dummy.setMaximumSize(new Dimension(140, 32));
        return dummy;
    }



    public class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                if (((JButton) e.getSource()).getName().equals("btnLoadCache")) {
                    if(liste.getCachename()!= null){
                        try {
                            cacheLoad(liste.getCachename(), getHostname(), getHostport());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{
                        try {
                            cacheLoad(txtLoadCache.getText(), getHostname(), getHostport());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    txtLoadCache.setText(data.getName());
                    lblLoadCacheOwner.setText("Owner: " + data.getOwner());
                    txtLoadCacheDescription.setText(data.getDescription());
                    lblLoadCacheLgrad.setText("Längengrad: "+Float.toString(data.getlGrad()));
                    lblLoadCacheBgrad.setText("Breitengrad: "+Float.toString(data.getbGrad()));
                }
                if (((JButton) e.getSource()).getName().equals("btnDeleteCache")) {
                    if (getLogged().equals(data.getOwner())) {
                        try {
                            cacheDelete(txtLoadCache.getText(), getHostname(), getHostport());
                            reset();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else if (getLogged().contains("Admin")) {
                        try {
                            cacheDelete(txtLoadCache.getText(), getHostname(), getHostport());
                        } catch (Exception E) {
                            JOptionPane.showMessageDialog(null, "Der Cache konnte nicht gelöscht werden");
                            E.printStackTrace();
                        }
                    } else {
                        reset();
                        JOptionPane.showMessageDialog(null, "Der Cache gehört nicht dir.");
                    }
                    txtLoadCache.setText("Kein Cache");
                    lblLoadCacheOwner.setText("Owner: Kein Owner");
                    txtLoadCacheDescription.setText("Keine Beschreibung");
                }
                if(((JButton) e.getSource()).getName().equals("btnUpdateCache")){
                    if(getLogged().equals(data.getOwner())){
                        cacheUpdate(txtLoadCache.getText(),txtLoadCacheDescription.getText(),getHostname(),getHostport());
                    }
                    else if(getLogged().contains("Admin")){
                        cacheUpdate(txtLoadCache.getText(),txtLoadCacheDescription.getText(),getHostname(),getHostport());
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Du hast keine Rechte dafür.");
                    }

                }
                if(((JButton) e.getSource()).getName().equals("btnListeCache")){
                    liste.update();
                    liste.reset();
                    liste.setVisible(true);
                }

            }
        }
    }
}