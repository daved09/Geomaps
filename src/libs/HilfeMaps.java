package libs;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hbfit14bannasch on 01.02.16.
 */
public class HilfeMaps extends JDialog {


    public HilfeMaps(){
        fenstereigenschaften();
    }

    public void fenstereigenschaften(){
        this.pack();
        this.setTitle("Hilfe");
        this.setSize(400,400);
        this.setResizable(false);
    }
    public void fensterelemente(){
    }



    public JPanel panelText(){
        JPanel dummy = new JPanel();
        return dummy;
    }

    public class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){

        }
    }
}
