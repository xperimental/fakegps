package net.sourcewalker.fakegps.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.sourcewalker.fakegps.data.IDataModel;

/**
 * This class contains the controls panel displayed on the side of the main
 * application window.
 * 
 * @author Xperimental
 */
public class SidePanel extends JPanel {

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = -6845092835433670537L;

    /**
     * Contains the data model.
     */
    private IDataModel model;

    /**
     * Contains the panel used for waypoint modifications.
     */
    private WaypointPanel waypointPanel;

    /**
     * Contains the panel used for interaction with the route controller.
     */
    private RoutePanel routePanel;

    /**
     * Contains the panel used for interaction with the device emulator.
     */
    private DevicePanel devicePanel;

    /**
     * Creates a new instance of the panel using the provided data model.
     * 
     * @param dataModel
     *            Data model to use.
     */
    public SidePanel(final IDataModel dataModel) {
        model = dataModel;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(getWaypointPanel());
        add(getRoutePanel());
        add(getDevicePanel());
    }

    /**
     * Returns the panel used for interaction with the device emulator. The
     * panel is created in the first method call.
     * 
     * @return Device panel.
     */
    private DevicePanel getDevicePanel() {
        if (devicePanel == null) {
            devicePanel = new DevicePanel(model);
        }
        return devicePanel;
    }

    /**
     * Returns the panel used for interaction with the route controller. The
     * panel is created in the first method call.
     * 
     * @return Route panel.
     */
    private RoutePanel getRoutePanel() {
        if (routePanel == null) {
            routePanel = new RoutePanel(model);
        }
        return routePanel;
    }

    /**
     * Returns the panel used for modifying the waypoints.
     * 
     * @return Waypoint panel.
     */
    private WaypointPanel getWaypointPanel() {
        if (waypointPanel == null) {
            waypointPanel = new WaypointPanel(model);
        }
        return waypointPanel;
    }

}
