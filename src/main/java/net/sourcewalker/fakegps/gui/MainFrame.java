package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import net.sourcewalker.fakegps.data.WaypointModel;
import net.sourcewalker.fakegps.map.RoutePainter;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXMapKit;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 4381691372731768163L;

    private JXMapKit mapKit;

    private JPanel contentPane;

    private JPanel sidePanel;

    private JMenuBar menuBar;

    private WaypointModel waypointModel;

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
            sidePanel = new SidePanel(getWaypointModel());
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
                    new RoutePainter(getWaypointModel()));
            mapKit.getMainMap().addMouseListener(
                    new MapMouseListener(mapKit, waypointModel));
        }
        return mapKit;
    }

    private WaypointModel getWaypointModel() {
        if (waypointModel == null) {
            waypointModel = new WaypointModel();
            waypointModel.addListDataListener(new ModelListener());
        }
        return waypointModel;
    }

    public class ModelListener implements ListDataListener {

        /*
         * (non-Javadoc)
         * @see
         * javax.swing.event.ListDataListener#contentsChanged(javax.swing.event
         * .ListDataEvent)
         */
        @Override
        public void contentsChanged(ListDataEvent e) {
            getMapView().repaint();
        }

        /*
         * (non-Javadoc)
         * @see
         * javax.swing.event.ListDataListener#intervalAdded(javax.swing.event
         * .ListDataEvent)
         */
        @Override
        public void intervalAdded(ListDataEvent e) {
            getMapView().repaint();
        }

        /*
         * (non-Javadoc)
         * @see
         * javax.swing.event.ListDataListener#intervalRemoved(javax.swing.event
         * .ListDataEvent)
         */
        @Override
        public void intervalRemoved(ListDataEvent e) {
            getMapView().repaint();
        }

    }

}
