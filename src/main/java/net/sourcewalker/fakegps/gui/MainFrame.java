package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;
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

/**
 * Contains the main window.
 * 
 * @author Xperimental
 */
public class MainFrame extends JFrame {

    /**
     * Constant for frame minimum width.
     */
    private static final int MIN_WIDTH = 800;

    /**
     * Constant for frame minimum height.
     */
    private static final int MIN_HEIGHT = 600;

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = 4381691372731768163L;

    /**
     * Contains the map GUI element.
     */
    private JXMapKit mapKit;

    /**
     * Contains the panel used for the frame content.
     */
    private JPanel contentPane;

    /**
     * Contains the panel used for the controls on the side.
     */
    private JPanel sidePanel;

    /**
     * Contains the data model used for the application.
     */
    private IDataModel waypointModel;

    /**
     * Contains the resources map.
     */
    private ResourceMap resourceMap;

    /**
     * Create a new application main window.
     */
    public MainFrame() {
        super();

        setName("mainFrame");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        setContentPane(createContentPane());

        createResourceMap();
    }

    /**
     * Creates the resource map from the properties file.
     */
    private void createResourceMap() {
        resourceMap = Application.getInstance().getContext().getResourceMap(
                MainFrame.class);
        resourceMap.injectComponents(this);
    }

    /**
     * Returns the content panel of the window. The first call creates the panel
     * and the contained components.
     * 
     * @return Content panel of window.
     */
    private JPanel createContentPane() {
        if (contentPane == null) {
            contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(getMapView(), BorderLayout.CENTER);
            contentPane.add(getSidePanel(), BorderLayout.EAST);
        }
        return contentPane;
    }

    /**
     * Returns the side panel. The first call creates the panel.
     * 
     * @return Side panel
     */
    private JPanel getSidePanel() {
        if (sidePanel == null) {
            sidePanel = new SidePanel(getWaypointModel());
        }
        return sidePanel;
    }

    /**
     * Returns the map view control. The first call creates the control.
     * 
     * @return Map view control.
     */
    private JXMapKit getMapView() {
        if (mapKit == null) {
            mapKit = new JXMapKit();
            mapKit.setName("mapKit");

            mapKit.setDefaultProvider(JXMapKit.DefaultProviders.OpenStreetMaps);
            mapKit.setAddressLocationShown(false);
            mapKit.getMainMap().setOverlayPainter(
                    new RoutePainter(getWaypointModel()));
            mapKit.getMainMap().addMouseListener(
                    new MapMouseListener(mapKit, waypointModel));
        }
        return mapKit;
    }

    /**
     * Returns the application data model. The first call to the function
     * creates the model.
     * 
     * @return Application data model.
     */
    private IDataModel getWaypointModel() {
        if (waypointModel == null) {
            waypointModel = new WaypointModel();
            waypointModel.addChangeListener(new ModelListener());
            waypointModel.addToolListener(new ToolListener());
        }
        return waypointModel;
    }

    /**
     * This listener class listens for changes of the map tool and changes the
     * GUI accordingly.
     * 
     * @author Xperimental
     */
    private class ToolListener implements MapToolListener {

        /*
         * (non-Javadoc)
         * @see net.sourcewalker.fakegps.data.MapToolListener#toolChanged(net.
         * sourcewalker.fakegps.data.MapTool)
         */
        @Override
        public void toolChanged(final MapTool currentTool) {
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

    /**
     * This change listener repaints the map on a data change.
     * 
     * @author Xperimental
     */
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
