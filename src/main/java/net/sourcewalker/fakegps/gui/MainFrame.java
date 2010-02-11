package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;
import net.sourcewalker.fakegps.data.MapToolListener;
import net.sourcewalker.fakegps.data.ModelChangeListener;
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

    private IDataModel waypointModel;

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

    private IDataModel getWaypointModel() {
        if (waypointModel == null) {
            waypointModel = new WaypointModel();
            waypointModel.addChangeListener(new ModelListener());
            waypointModel.addToolListener(new ToolListener());
        }
        return waypointModel;
    }

    private class ToolListener implements MapToolListener {

        /*
         * (non-Javadoc)
         * @see net.sourcewalker.fakegps.data.MapToolListener#toolChanged(net.
         * sourcewalker.fakegps.data.MapTool)
         */
        @Override
        public void toolChanged(MapTool currentTool) {
            switch (currentTool) {
            case ADDPOINT:
                getMapView().getMainMap().setCursor(
                        Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                break;
            case NULL:
                getMapView().getMainMap().setCursor(Cursor.getDefaultCursor());
                break;
            default:
                break;
            }
        }

    }

    private class ModelListener implements ModelChangeListener {

        /*
         * (non-Javadoc)
         * @see net.sourcewalker.fakegps.data.ModelChangeListener#dataChanged()
         */
        @Override
        public void dataChanged() {
            getMapView().repaint();
        }
    }

}
