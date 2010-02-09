package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Random;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import net.sourcewalker.fakegps.data.GpsWaypoint;
import net.sourcewalker.fakegps.data.WaypointModel;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

public class SidePanel extends JPanel {

    private static final long serialVersionUID = -1272970302372723527L;

    private WaypointModel model;

    private JList waypointList;

    private JPanel buttonPanel;

    private JButton addButton;

    private JButton removeButton;

    private ActionMap actions;

    private Random rand = new Random();

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
        actions = ctx.getActionMap(this);
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
        GpsWaypoint wp = new GpsWaypoint(rand.nextDouble() * 90 - 45, rand
                .nextDouble() * 180 - 90);
        model.addWaypoint(wp);
    }

    @Action(enabledProperty = "itemSelected")
    public void removePoint() {
    }

    public boolean getItemSelected() {
        return waypointList.getSelectedIndex() != -1;
    }

}
