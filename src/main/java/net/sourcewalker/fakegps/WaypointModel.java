package net.sourcewalker.fakegps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class WaypointModel implements ListModel {

    private Set<GpsWaypoint> waypointSet;
    private List<ListDataListener> listeners;

    public WaypointModel() {
        waypointSet = new HashSet<GpsWaypoint>();
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
        listeners.remove(l);
    }

    public void addWaypoint(GpsWaypoint wp) {
        waypointSet.add(wp);
        itemAdded(waypointSet.size() - 1);
    }

    private void itemAdded(int index) {
        ListDataEvent evt = new ListDataEvent(this,
                ListDataEvent.INTERVAL_ADDED, index, index);
        for (ListDataListener listener : listeners) {
            listener.intervalAdded(evt);
        }
    }

    public Set<GpsWaypoint> getDataSet() {
        return waypointSet;
    }

}
