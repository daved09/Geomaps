package libs;

import database.Cachedatabase;
import database.Userdatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by hbfit14bannasch on 02.02.16.
 */
public class UserDeleteMaps extends JDialog{
    Userdatabase data = new Userdatabase();
    Cachedatabase cache = new Cachedatabase();

    private String hostname,hostport,logged;
    private String username,password;


    public String getLogged() {
        return logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void dataUpdate(String hostname,String hostport,String password){
        setHostname(hostname);
        setHostport(hostport);
        setPassword(password);
    }

    public boolean pruefePassword(String password){
        if(password.equals(getPassword())){
            return true;
        }
        else{
            return false;
        }
    }

    public void reset(){
        txtUser.setText("");
    }

    public void testPw() throws SQLException {
        String pw = JOptionPane.showInputDialog(null,"Passwort zum bestätigen eingeben.");
        boolean right = pruefePassword(pw);
        if(right){
            deleteUser();
            JOptionPane.showMessageDialog(null,"Benutzer erfolgreich gelöscht.");
        }
        else {
            JOptionPane.showMessageDialog(null,"Du darfst keine Benutzer löschen.");
        }
    }

    public void deleteUser(){
        try{
            data.deleteUser(txtUser.getText(),getHostname(),getHostport());
            cache.deteleAllCaches(txtUser.getText(),getHostname(),getHostport());
        }
        catch(Exception E){
            JOptionPane.showMessageDialog(null,"Benutzer konnte nicht gelöscht werden.");
            E.printStackTrace();
        }
    }

    public UserDeleteMaps(){
        fensterelemente();
        fenstereigenschaften();
    }

    public void fenstereigenschaften(){
        this.pack();
        this.setTitle("Benutzer löschen");
        this.setSize(200,80);
        this.setResizable(false);
    }

    JTextField txtUser = null;
    public void fensterelemente(){
        this.add(panel(), BorderLayout.CENTER);
    }

    public JPanel panel(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy,BoxLayout.Y_AXIS));
        dummy.add(txtUser());
        dummy.add(button("btnDelete","löschen"));
        return dummy;
    }

    public JTextField txtUser(){
        if(txtUser == null){
            txtUser = new JTextField();
        }
        txtUser.setMinimumSize(new Dimension(150,24));
        txtUser.setMaximumSize(new Dimension(180,32));
        return txtUser;
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
                if(((JButton) e.getSource()).getName().equals("btnDelete")){
                    try {
                        testPw();
                    } catch (Exception E) {
                        JOptionPane.showMessageDialog(null,"Fehler beim löschen des Benutzers.");
                        E.printStackTrace();
                    }
                }
            }
        }
    }
}
