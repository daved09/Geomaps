package libs;

import database.Userdatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hbfit14bannasch on 28.01.16.
 */
public class RegisterMaps extends JDialog {
    Userdatabase data = new Userdatabase();

    private String hostname,hostport;

    public RegisterMaps(){
        fensterelemente();
        fenstereigenschaften();
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

    public void register(String username, String password,String vorname,String nachname, String email){
        data.setHostname(getHostname());
        data.setHostport(getHostport());
        if(passwordEinlesen().equals(password2Einlesen())){
            if(passwordEinlesen().toCharArray().length<8){
                JOptionPane.showMessageDialog(null,"Das Passwort muss mindestens 8 Zeichen lang sein.");
            }
            else{
                try{
                    data.registerUser(username, password,vorname,nachname, email,getHostname(),getHostport());
                    this.setVisible(false);
                    JOptionPane.showMessageDialog(null,"Registrierung von "+username+" erfolgreich.");
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

        }
        else{
            JOptionPane.showMessageDialog(null,"Das Passwort muss zwei mal richtig eingegeben werden.");
        }
    }

    public void reset(){
        txtVorname.setText("");
        txtNachname.setText("");
        txtUsername.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtPassword2.setText("");
    }


    JTextField txtUsername = null;
    JTextField txtVorname = null;
    JTextField txtNachname = null;
    JPasswordField txtPassword = null;
    JPasswordField txtPassword2 = null;
    JTextField txtEmail = null;
    JButton btnSubmit = null;

        public void fenstereigenschaften(){
            this.pack();
            this.setTitle("Registrieren");
            this.setSize(350,400);
            this.setResizable(false);

        }
        public void fensterelemente(){
            this.add(panelTxt(), BorderLayout.NORTH);
            this.add(panelBtn(),BorderLayout.SOUTH);
        }
        public JPanel panelTxt(){
            JPanel dummy = new JPanel();
            dummy.setLayout(new GridLayout(6,2));
            dummy.add(label("Vorname"));
            dummy.add(txtVorname());
            dummy.add(label("Nachname"));
            dummy.add(txtNachname());
            dummy.add(label("Benutzername"));
            dummy.add(txtUsername());
            dummy.add(label("Passwort"));
            dummy.add(txtPassword());
            dummy.add(label("Passwort wiederholen"));
            dummy.add(txtPassword2());
            dummy.add(label("Email"));
            dummy.add(txtEmail());
            return dummy;
        }
        public JPanel panelBtn(){
            JPanel dummy = new JPanel();
            dummy.setLayout(new BoxLayout(dummy, BoxLayout.Y_AXIS));
            dummy.add(btnSubmit());
            return dummy;
        }
        public JTextField txtUsername(){
            if(txtUsername == null){
                txtUsername = new JTextField();
            }
            txtUsername.setMinimumSize(new Dimension(180,28));
            txtUsername.setMaximumSize(new Dimension(220,32));
            return txtUsername;
        }
        public JTextField txtVorname(){
            if(txtVorname == null){
                txtVorname = new JTextField();
            }
            txtVorname.setMinimumSize(new Dimension(180,28));
            txtVorname.setMaximumSize(new Dimension(220,32));
            return txtVorname;
        }
        public JTextField txtNachname(){
            if(txtNachname == null){
                txtNachname = new JTextField();
            }
            txtNachname.setMinimumSize(new Dimension(180,28));
            txtNachname.setMaximumSize(new Dimension(220,32));
            return txtNachname;
        }
        public JPasswordField txtPassword(){
            if(txtPassword == null){
                txtPassword = new JPasswordField();
            }
            txtPassword.setColumns(20);
            txtPassword.setEchoChar('*');
            txtPassword.setMinimumSize(new Dimension(180,28));
            txtPassword.setMaximumSize(new Dimension(220,32));
            return txtPassword;
        }
    public JPasswordField txtPassword2(){
        if(txtPassword2 == null){
            txtPassword2 = new JPasswordField();
        }
        txtPassword2.setColumns(20);
        txtPassword2.setEchoChar('*');
        txtPassword2.setMinimumSize(new Dimension(180,28));
        txtPassword2.setMaximumSize(new Dimension(220,32));
        return txtPassword2;
    }
        public JTextField txtEmail(){
            if(txtEmail == null){
                txtEmail = new JTextField();
            }
            txtEmail.setMinimumSize(new Dimension(180,28));
            txtEmail.setMaximumSize(new Dimension(220,32));
            return txtEmail;
        }
        public JButton btnSubmit(){
            if(btnSubmit == null){
                btnSubmit = new JButton();
            }
            btnSubmit.setText("Registrieren");
            btnSubmit.addActionListener(new Listener());
            return btnSubmit;
        }
    public JLabel label(String text){
        JLabel dummy = new JLabel();
        dummy.setText(text);
        return dummy;
    }

    public class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource().equals(btnSubmit)){
                register(txtUsername.getText(), passwordEinlesen(),txtVorname.getText(),txtNachname.getText(), txtEmail.getText());
            }
        }
    }


    public String passwordEinlesen(){
        char[] zeichen = txtPassword.getPassword();
        String passwort = new String(zeichen);
        return passwort;
    }//Quelle: http://www.it-academy.cc/article/1575/Java+Swing:+Passwortfelder.html
    public String password2Einlesen(){
        char[] zeichen = txtPassword2.getPassword();
        String passwort = new String(zeichen);
        return passwort;
    }
}
