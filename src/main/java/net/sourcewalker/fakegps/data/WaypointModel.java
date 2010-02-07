package net.sourcewalker.fakegps.data;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class WaypointModel implements ListModel, IDataModel {

    private List<GpsWaypoint> waypoints;
    private List<ListDataListener> listeners;

    public WaypointModel() {
        waypoints = new ArrayList<GpsWaypoint>();
        listeners = new ArrayList<ListDataListener>();
    }

    /*
     * (non-Javadoc)
     * @seejavax.swing.ListModel#addListDataListener(javax.swing.event.
     * ListDataListener)
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        System.out.println("WaypointModel.addListDataListener()");
        listeners.add(l);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.ListModel#getElementAt(int)
     */
    @Override
    public Object getElementAt(int index) {
        return waypoints.get(index);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.ListModel#getSize()
     */
    @Override
    public int getSize() {
        return waypoints.size();
    }

    /*
     * (non-Javadoc)
     * @seejavax.swing.ListModel#removeListDataListener(javax.swing.event.
     * ListDataListener)
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /*
     * (non-Javadoc)
     * @see
     * net.sourcewalker.fakegps.IDataModel#addWaypoint(net.sourcewalker.fakegps
     * .GpsWaypoint)
     */
    public void addWaypoint(GpsWaypoint wp) {
        waypoints.add(wp);
        itemAdded(waypoints.size() - 1);
    }

    private void itemAdded(int index) {
        ListDataEvent evt = new ListDataEvent(this,
                ListDataEvent.INTERVAL_ADDED, index, index);
        for (ListDataListener listener : listeners) {
            listener.intervalAdded(evt);
        }
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.IDataModel#getWaypoints()
     */
    @Override
    public List<GpsWaypoint> getWaypoints() {
        return waypoints;
    }

}
