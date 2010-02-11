package net.sourcewalker.fakegps.gui;

import java.awt.event.ActionEvent;

import javax.swing.JToggleButton;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;

import org.jdesktop.application.Action;

public class WaypointActions {

    private IDataModel model;

    public WaypointActions(IDataModel dataModel) {
        this.model = dataModel;
    }

    @Action
    public void addPoint(ActionEvent evt) {
        toggleMapTool(evt, MapTool.ADDPOINT);
    }

    private void toggleMapTool(ActionEvent evt, MapTool tool) {
        JToggleButton source = (JToggleButton) evt.getSource();
        if (source.isSelected()) {
            model.setCurrentTool(tool);
        } else {
            model.setCurrentTool(MapTool.NULL);
        }
    }

    @Action
    public void removePoint(ActionEvent evt) {
        toggleMapTool(evt, MapTool.REMOVEPOINT);
    }

    @Action
    public void clearPoints() {
        model.clearWaypoints();
    }

}
