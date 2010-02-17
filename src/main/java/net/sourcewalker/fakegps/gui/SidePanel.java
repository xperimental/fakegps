package net.sourcewalker.fakegps.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.sourcewalker.fakegps.data.IDataModel;

public class SidePanel extends JPanel {

    private static final long serialVersionUID = -6845092835433670537L;

    private IDataModel model;

    private WaypointPanel waypointPanel;

    private RoutePanel routePanel;

    private DevicePanel devicePanel;

    public SidePanel(IDataModel waypointModel) {
        model = waypointModel;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(getWaypointPanel());
        add(getRoutePanel());
        add(getDevicePanel());
    }

    /**
     * @return
     */
    private DevicePanel getDevicePanel() {
        if (devicePanel == null) {
            devicePanel = new DevicePanel(model);
        }
        return devicePanel;
    }

    private RoutePanel getRoutePanel() {
        if (routePanel == null) {
            routePanel = new RoutePanel(model);
        }
        return routePanel;
    }

    private WaypointPanel getWaypointPanel() {
        if (waypointPanel == null) {
            waypointPanel = new WaypointPanel(model);
        }
        return waypointPanel;
    }

}
