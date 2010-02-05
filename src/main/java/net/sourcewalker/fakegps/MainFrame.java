package net.sourcewalker.fakegps;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXMapKit;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 4381691372731768163L;
    
    private JXMapKit mapKit;

    public MainFrame() {
        super("FakeGPS for Android");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 400));

        setContentPane(createMapView());
    }

    private JXMapKit createMapView() {
        if (mapKit == null) {
            mapKit = new JXMapKit();
            mapKit.setName("mapKit");
            mapKit.setPreferredSize(new java.awt.Dimension(413, 218));
            mapKit.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
        }
        return mapKit;
    }

}
