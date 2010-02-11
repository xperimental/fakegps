package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.WaypointModel;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

public class WaypointPanel extends JPanel {

    private static final long serialVersionUID = -1272970302372723527L;

    private IDataModel model;

    private JToggleButton addButton;

    private JButton removeButton;

    private ActionMap actions;

    private TitledBorder border;

    public WaypointPanel(IDataModel dataModel) {
        super();
        model = dataModel;

        createResourceMap();

        setBorder(getPanelBorder());
        setLayout(new FlowLayout());
        add(getAddButton());
        add(getRemoveButton());
        setMinimumSize(new Dimension(50, 100));
    }

    private TitledBorder getPanelBorder() {
        if (border == null) {
            border = new TitledBorder("Waypoint control");
        }
        return border;
    }

    private void createResourceMap() {
        ApplicationContext ctx = Application.getInstance().getContext();
        actions = ctx.getActionMap(WaypointActions.class, new WaypointActions(model));
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
