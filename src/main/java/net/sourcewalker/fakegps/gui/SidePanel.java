package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import net.sourcewalker.fakegps.data.WaypointModel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

public class SidePanel extends JPanel {

    private static final long serialVersionUID = -1272970302372723527L;

    private WaypointModel model;

    private JList waypointList;

    private JPanel buttonPanel;

    private JToggleButton addButton;

    private JButton removeButton;

    private ActionMap actions;

    public SidePanel(WaypointModel dataModel) {
        super();
        model = dataModel;

        createResourceMap();

        setLayout(new BorderLayout());
        add(getWaypointList(), BorderLayout.CENTER);
        add(getButtonPanel(), BorderLayout.SOUTH);
        setMinimumSize(new Dimension(50, 100));
    }

    private void createResourceMap() {
        ApplicationContext ctx = Application.getInstance().getContext();
        actions = ctx.getActionMap(WaypointActions.class, new WaypointActions(model));
    }

    private JList getWaypointList() {
        if (waypointList == null) {
            waypointList = new JList(model);
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

    private JToggleButton getAddButton() {
        if (addButton == null) {
            addButton = new JToggleButton(actions.get("addPoint"));
        }
        return addButton;
    }

    private JButton getRemoveButton() {
        if (removeButton == null) {
            removeButton = new JButton(actions.get("removePoint"));
        }
        return removeButton;
    }

}
