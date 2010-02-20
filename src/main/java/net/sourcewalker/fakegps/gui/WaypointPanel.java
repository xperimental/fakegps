package net.sourcewalker.fakegps.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.MapTool;
import net.sourcewalker.fakegps.data.MapToolListener;

/**
 * This panel contains the GUI components used for manipulating the waypoints.
 * 
 * @author Xperimental
 */
public class WaypointPanel extends PanelBase {

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = -1272970302372723527L;

    /**
     * Contains the button for enabling the {@link MapTool#ADDPOINT} tool.
     */
    private JToggleButton addButton;

    /**
     * Contains the button for enabling the {@link MapTool#REMOVEPOINT} tool.
     */
    private JToggleButton removeButton;

    /**
     * Contains the button for clearing all waypoints.
     */
    private JButton clearButton;

    /**
     * Creates a new instance of this panel using the provided data model.
     * 
     * @param dataModel
     *            Data model to use.
     */
    public WaypointPanel(final IDataModel dataModel) {
        super(WaypointPanel.class, "waypointPanel", new WaypointActions(
                dataModel), dataModel);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.gui.PanelBase#initComponents()
     */
    @Override
    protected final void initComponents() {
        setLayout(new FlowLayout());
        add(getAddButton());
        add(getRemoveButton());
        add(getClearButton());

        getModel().addToolListener(new ToolListener());
    }

    /**
     * Returns the button for enabling the {@link MapTool#ADDPOINT} tool. The
     * button is created in the first method call.
     * 
     * @return Add point tool button.
     */
    private JToggleButton getAddButton() {
        if (addButton == null) {
            addButton = new JToggleButton(getActions().get("addPoint"));
        }
        return addButton;
    }

    /**
     * Returns the button for enabling the {@link MapTool#REMOVEPOINT} tool. The
     * button is created in the first method call.
     * 
     * @return Remove point tool button.
     */
    private JToggleButton getRemoveButton() {
        if (removeButton == null) {
            removeButton = new JToggleButton(getActions().get("removePoint"));
        }
        return removeButton;
    }

    /**
     * Returns the button used for clearing all waypoints.
     * 
     * @return Clear waypoints button.
     */
    private JButton getClearButton() {
        if (clearButton == null) {
            clearButton = new JButton(getActions().get("clearPoints"));
        }
        return clearButton;
    }

    /**
     * This listener updates the button selection state according to the
     * currently selected map tool.
     * 
     * @author Xperimental
     */
    public class ToolListener implements MapToolListener {

        /*
         * (non-Javadoc)
         * @seenet.sourcewalker.fakegps.data.MapToolListener#toolChanged(net.
         * sourcewalker.fakegps.data.MapTool)
         */
        @Override
        public final void toolChanged(final MapTool currentTool) {
            getAddButton().setSelected(currentTool == MapTool.ADDPOINT);
            getRemoveButton().setSelected(currentTool == MapTool.REMOVEPOINT);
        }

    }

}
