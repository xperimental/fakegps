package net.sourcewalker.fakegps.gui;

import java.awt.event.ActionEvent;

import javax.swing.JToggleButton;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;

import org.jdesktop.application.Action;

/**
 * This class contains action definitions connected to managing the waypoints of
 * the route. The actions are used within the {@link WaypointPanel} GUI element.
 * 
 * @author Xperimental
 */
public class WaypointActions {

    /**
     * Contains a reference to the data model which is modified by the actions.
     */
    private IDataModel model;

    /**
     * Create a new instance using the data model provided.
     * 
     * @param dataModel
     *            Data model to modify.
     */
    public WaypointActions(final IDataModel dataModel) {
        this.model = dataModel;
    }

    /**
     * Activates the map tool for adding points to the route.
     * 
     * @param evt
     *            Event with a {@link JToggleButton} source to indicate whether
     *            the tool should be set active.
     */
    @Action
    public final void addPoint(final ActionEvent evt) {
        toggleMapTool(evt, MapTool.ADDPOINT);
    }

    /**
     * Toggle a map tool.
     * 
     * @param evt
     *            Event with a {@link JToggleButton} source to indicate whether
     *            the tool should be set active.
     * @param tool
     *            Tool to toggle active or inactive.
     */
    private void toggleMapTool(final ActionEvent evt, final MapTool tool) {
        JToggleButton source = (JToggleButton) evt.getSource();
        if (source.isSelected()) {
            model.setCurrentTool(tool);
        } else {
            model.setCurrentTool(MapTool.NULL);
        }
    }

    /**
     * Activates the map tool for removing points from the route.
     * 
     * @param evt
     *            Event with a {@link JToggleButton} source to indicate whether
     *            the tool should be set active.
     */
    @Action
    public final void removePoint(final ActionEvent evt) {
        toggleMapTool(evt, MapTool.REMOVEPOINT);
    }

    /**
     * Clears the currently set route.
     */
    @Action
    public final void clearPoints() {
        model.clearWaypoints();
    }

}
