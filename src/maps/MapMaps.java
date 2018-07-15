package maps;


import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.mapviewer.*;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.wms.WMSService;
import org.jdesktop.swingx.mapviewer.wms.WMSTileFactory;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * Created by hbfit14bannasch on 10.02.16.
 */
public class MapMaps extends JFrame{
    Thread th = new Thread(new Size());
    JXMapKit map = new JXMapKit();
    HashSet set = new HashSet();
    WaypointPainter waypoint = new WaypointPainter();
    JXMapViewer mapViewer = new JXMapViewer();
    JXPanel panel = new JXPanel();


    public MapMaps(){
        th.start();
    }
    public JXPanel panel(int lange,int breite){
        if(panel == null){
            panel = new JXPanel();
        }
        panel.setPreferredSize(new Dimension(lange,breite));
        panel.add(map());
        return panel;
    }

    public void waypointAdd(float lgrad, float bgrad){
        waypoint.setAntialiasing(true);
        waypoint.setWaypoints(set);
        set.add(new Waypoint(lgrad,bgrad));
        neuemap();
    }//Setzt neue Wegpunkte

    public void neuemap(){
        remove(map);
        panel.add(map());
        repaint();
        revalidate();
        update(getGraphics());
    }

    public void clearc(){
        set.clear();
        neuemap();
    }



    public JXMapKit map(){
        map.setDefaultProvider(org.jdesktop.swingx.JXMapKit.DefaultProviders.OpenStreetMaps);
        map.setName("Map");
        map.setPreferredSize(new Dimension(panel.getWidth(),panel.getHeight()));
        map.setZoom(13);
        map.getMainMap().setOverlayPainter(waypoint);
        map.setMiniMapVisible(false);
        map.revalidate();
        return map;
    }//Erstellt die map

    public class Size implements Runnable{
        public void run(){
            while(true){
                map.setPreferredSize(new Dimension(panel.getWidth(),panel.getHeight()));
                map.revalidate();
                try {
                    Thread.sleep(1000/30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }//Thread für die dynamische Größe der Map

}
