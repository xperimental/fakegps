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
        JToggleButton source = (JToggleButton) evt.getSource();
        if (source.isSelected()) {
            model.setCurrentTool(MapTool.ADDPOINT);
        } else {
            model.setCurrentTool(MapTool.NULL);
        }
    }

    @Action
    public void removePoint() {
    }
    
    @Action
    public void clearPoints() {
        model.clearWaypoints();
    }

}
