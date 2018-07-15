package libs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hbfit14bannasch on 24.02.16.
 */
public class VersionMaps extends JDialog{
    public VersionMaps(){
        fensterelemente();
        fenstereigenschaften();
    }

    private String version="Prerelease-1.0";
    private int build = 265;
    public void fenstereigenschaften(){
        setSize(250,100);
        setTitle("Version");
    }
    public void fensterelemente(){
        add(panel(), BorderLayout.CENTER);
    }

    public JPanel panel(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy,BoxLayout.Y_AXIS));
        dummy.add(label("Geomaps Version: "+version));
        dummy.add(label("Build: "+build));
        dummy.add(button("btnClose","Schließen"));
        return dummy;
    }
    public JLabel label(String text){
        JLabel dummy = new JLabel();
        dummy.setText(text);
        return dummy;
    }
    public JButton button(String name, String text){
        JButton dummy = new JButton();
        dummy.addActionListener(new Listener());
        dummy.setName(name);
        dummy.setText(text);
        return dummy;
    }

    public class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() instanceof JButton){
                if(((JButton) e.getSource()).getName().equals("btnClose")){
                    setVisible(false);
                }
            }
        }
    }
    
}