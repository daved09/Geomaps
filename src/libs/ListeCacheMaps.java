package libs;

import database.Cachedatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hbfit14bannasch on 18.02.16.
 */
public class ListeCacheMaps extends JDialog{
    Cachedatabase data = new Cachedatabase();

    Thread th = new Thread(new Load());
    private String hostname,hostport;
    private String cachename;
    private String[] caches;

    public ListeCacheMaps(){
        fensterelemente();
        fenstereigenschaften();
        th.start();
    }

    public String getCachename() {
        return cachename;
    }

    public void setCachename(String cachename) {
        this.cachename = cachename;
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

    public void dataUpdate(String hostname, String hostport){
        setHostname(hostname);
        setHostport(hostport);
    }
    public void update(){
        cacheElemente();
        listen.removeAllElements();
        addCaches();
    }
    public void update2(){
        listen.removeAllElements();
        addCaches2();
    }
    public void destroy(){
        cachePanel.removeAll();
        remove(cachePanel);
        repaint();
        revalidate();
        update();
    }
    public void reset(){
        txtCache.setText("");
    }

    public void addCaches(){
        int laenge;
        try{
            data.listCaches(hostname,hostport);
            laenge = data.getEnd();
            caches = new String[laenge];
            for (int i=0;i<laenge;i++){
                caches[i] = data.caches(i);
            }
            for (int i=0;i<caches.length;i++){
                listen.addElement(caches[i]);
            }
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    public void addCaches2(){
        int laenge;
        try{
            data.listCaches2(txtCache.getText(),hostname,hostport);
            laenge = data.getEnd();
            caches = new String[laenge];
            for (int i=0;i<laenge;i++){
                caches[i] = data.caches(i);
            }
            for (int i=0;i<caches.length;i++){
                listen.addElement(caches[i]);
            }
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }

    JScrollPane scroll;
    JList liste;
    DefaultListModel listen;
    JTextField txtCache;
    JPanel cachePanel;
    public void fenstereigenschaften(){
        pack();
        setSize(200,500);
        setTitle("Cacheliste");
        setResizable(false);
    }

    public void fensterelemente(){
        add(panel(), BorderLayout.NORTH);
        add(buttonPanel(),BorderLayout.SOUTH);
    }
    public void cacheElemente(){
        add(cachePanel());
    }

    public JPanel panel(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new GridLayout(4,2));
        dummy.add(txtCache());
        dummy.add(label("Cacheliste"));
        return dummy;
    }

    public JPanel cachePanel(){
        if(cachePanel == null){
            cachePanel = new JPanel();
        }
        cachePanel.setLayout(new BoxLayout(cachePanel,BoxLayout.Y_AXIS));
        cachePanel.add(liste());
        return cachePanel;
    }
    public JPanel buttonPanel(){
        JPanel dummy = new JPanel();
        dummy.setLayout(new BoxLayout(dummy,BoxLayout.X_AXIS));
        dummy.add(button("btnLog","Auswählen"));
        return dummy;
    }
    public JList liste(){
        if(liste == null){
            liste = new JList(listen());
        }
        liste.add(scroll());
        return liste;
    }

    public DefaultListModel listen(){
        if(listen == null){
            listen = new DefaultListModel();
        }
        return listen;
    }

    public JScrollPane scroll(){
        if(scroll == null){
            scroll = new JScrollPane();
        }
        scroll.setVisible(true);
        return scroll;
    }

    public JTextField txtCache(){
        if(txtCache == null){
            txtCache = new JTextField();
        }
        txtCache.setMinimumSize(new Dimension(100,24));
        txtCache.setMaximumSize(new Dimension(150,32));
        return txtCache;
    }

    public JLabel label(String text){
        JLabel dummy = new JLabel();
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
                if(((JButton) e.getSource()).getName().equals("btnLog")){
                    int index = liste.getSelectedIndex();
                    cachename = caches[index];
                    setVisible(false);
                }
            }
        }
    }

    public class Load implements Runnable{
        public void run(){
            String txt = "";
            while (true){
                if(!txt.equals(txtCache.getText())){
                    update2();
                }
                txt = txtCache.getText();
                try {
                    Thread.sleep(1000/30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
