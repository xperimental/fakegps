package net.sourcewalker.fakegps.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;
import net.sourcewalker.fakegps.data.MapToolListener;

import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

public class WaypointPanel extends JPanel {

    private static final long serialVersionUID = -1272970302372723527L;

    private IDataModel model;

    private JToggleButton addButton;

    private JToggleButton removeButton;

    private ActionMap actions;

    private TitledBorder border;

    private ResourceMap resMap;

    private JButton clearButton;

    public WaypointPanel(IDataModel dataModel) {
        super();
        model = dataModel;

        initResources();

        setName("waypointPanel");
        setBorder(getPanelBorder());
        setLayout(new FlowLayout());
        add(getAddButton());
        add(getRemoveButton());
        add(getClearButton());
        setMinimumSize(new Dimension(50, 100));

        resMap.injectComponents(this);
        model.addToolListener(new ToolListener());
    }

    private TitledBorder getPanelBorder() {
        if (border == null) {
            border = new TitledBorder("");
        }
        return border;
    }

    private void initResources() {
        ApplicationContext ctx = Application.getInstance().getContext();
        resMap = ctx.getResourceMap(WaypointPanel.class);
        actions = ctx.getActionMap(WaypointActions.class, new WaypointActions(
                model));
    }

    private JToggleButton getAddButton() {
        if (addButton == null) {
            addButton = new JToggleButton(actions.get("addPoint"));
        }
        return addButton;
    }

    private JToggleButton getRemoveButton() {
        if (removeButton == null) {
            removeButton = new JToggleButton(actions.get("removePoint"));
        }
        return removeButton;
    }

    private JButton getClearButton() {
        if (clearButton == null) {
            clearButton = new JButton(actions.get("clearPoints"));
        }
        return clearButton;
    }

    public void setTitle(String title) {
        getPanelBorder().setTitle(title);
    }

    public class ToolListener implements MapToolListener {

        /*
         * (non-Javadoc)
         * @seenet.sourcewalker.fakegps.data.MapToolListener#toolChanged(net.
         * sourcewalker.fakegps.data.MapTool)
         */
        @Override
        public void toolChanged(MapTool currentTool) {
            getAddButton().setSelected(currentTool == MapTool.ADDPOINT);
            getRemoveButton().setSelected(currentTool == MapTool.REMOVEPOINT);
        }

    }

}
