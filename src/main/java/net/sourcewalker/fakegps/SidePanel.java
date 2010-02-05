package net.sourcewalker.fakegps;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

public class SidePanel extends JPanel {

    private static final long serialVersionUID = -1272970302372723527L;

    private Set<GpsWaypoint> waypointSet;

    private JList waypointList;

    private JPanel buttonPanel;

    private JButton addButton;

    private JButton removeButton;

    private ActionMap actions;

    private Random rand = new Random();

    public SidePanel(Set<GpsWaypoint> waypoints) {
        super();
        waypointSet = waypoints;

        createResourceMap();

        setLayout(new BorderLayout());
        add(getWaypointList(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.SOUTH);
        setMinimumSize(new Dimension(50, 100));
    }

    private void createResourceMap() {
        ApplicationContext ctx = Application.getInstance().getContext();
        actions = ctx.getActionMap(this);
    }

    private JList getWaypointList() {
        if (waypointList == null) {
            waypointList = new JList(new WaypointListModel());
        }
        return waypointList;
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(getAddButton());
            buttonPanel.add(getRemoveButton());
        }
        return buttonPanel;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton(actions.get("addPoint"));
        }
        return addButton;
    }

    private JButton getRemoveButton() {
        if (removeButton == null) {
            removeButton = new JButton(actions.get("removePoint"));
        }
        return removeButton;
    }

    @Action
    public void addPoint() {
        GpsWaypoint wp = new GpsWaypoint(rand.nextDouble() * 180 - 90, rand
                .nextDouble() * 360 - 180);
        System.out.println("New Waypoint: " + wp.getLatitude() + " , "
                + wp.getLongitude());
        waypointSet.add(wp);
    }

    @Action(enabledProperty = "listEmpty")
    public void removePoint() {
        System.out.println("SidePanel.removePoint()");
    }

    public boolean getListEmpty() {
        return waypointList.getModel().getSize() == 0;
    }

    public class WaypointListModel implements ListModel {

        /*
         * (non-Javadoc)
         * @seejavax.swing.ListModel#addListDataListener(javax.swing.event.
         * ListDataListener)
         */
        @Override
        public void addListDataListener(ListDataListener l) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         * @see javax.swing.ListModel#getElementAt(int)
         */
        @Override
        public Object getElementAt(int index) {
            return waypointSet.toArray()[index];
        }

        /*
         * (non-Javadoc)
         * @see javax.swing.ListModel#getSize()
         */
        @Override
        public int getSize() {
            return waypointSet.size();
        }

        /*
         * (non-Javadoc)
         * @seejavax.swing.ListModel#removeListDataListener(javax.swing.event.
         * ListDataListener)
         */
        @Override
        public void removeListDataListener(ListDataListener l) {
            // TODO Auto-generated method stub

        }

    }

}
