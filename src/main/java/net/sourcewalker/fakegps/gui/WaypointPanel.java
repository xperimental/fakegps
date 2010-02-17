package net.sourcewalker.fakegps.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;
import net.sourcewalker.fakegps.data.MapToolListener;

public class WaypointPanel extends PanelBase {

    private static final long serialVersionUID = -1272970302372723527L;

    private JToggleButton addButton;

    private JToggleButton removeButton;

    private JButton clearButton;

    public WaypointPanel(IDataModel dataModel) {
        super(WaypointPanel.class, "waypointPanel", new WaypointActions(
                dataModel), dataModel);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.gui.PanelBase#initComponents()
     */
    @Override
    protected void initComponents() {
        setLayout(new FlowLayout());
        add(getAddButton());
        add(getRemoveButton());
        add(getClearButton());
        setMinimumSize(new Dimension(50, 100));

        model.addToolListener(new ToolListener());
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
