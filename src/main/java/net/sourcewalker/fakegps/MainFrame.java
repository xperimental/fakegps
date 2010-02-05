package net.sourcewalker.fakegps;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXMapKit;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 4381691372731768163L;

    private JXMapKit mapKit;

    private JPanel contentPane;

    private JPanel sidePanel;

    private JMenuBar menuBar;

    private Set<GpsWaypoint> waypointSet;

    private ResourceMap resourceMap;

    public MainFrame() {
        super();
        
        setName("mainFrame");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        setContentPane(createContentPane());
        setJMenuBar(createMenuBar());
        
        createResourceMap();
    }

    private void createResourceMap() {
        resourceMap = Application.getInstance().getContext().getResourceMap(
                MainFrame.class);
        resourceMap.injectComponents(this);
    }

    private JMenuBar createMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
        }
        return menuBar;
    }

    private JPanel createContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(getMapView(), BorderLayout.CENTER);
            contentPane.add(getSidePanel(), BorderLayout.EAST);
        }
        return contentPane;
    }

    private JPanel getSidePanel() {
        if (sidePanel == null) {
            sidePanel = new SidePanel(waypointSet);
        }
        return sidePanel;
    }

    private JXMapKit getMapView() {
        if (mapKit == null) {
            mapKit = new JXMapKit();
            mapKit.setName("mapKit");
            mapKit.setPreferredSize(new java.awt.Dimension(413, 218));

            mapKit.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
            mapKit.setAddressLocationShown(false);
            mapKit.getMainMap().setOverlayPainter(
                    new GpsPainter(getWaypointSet()));
        }
        return mapKit;
    }

    private Set<GpsWaypoint> getWaypointSet() {
        if (waypointSet == null) {
            waypointSet = new HashSet<GpsWaypoint>();
        }
        return waypointSet;
    }

}
