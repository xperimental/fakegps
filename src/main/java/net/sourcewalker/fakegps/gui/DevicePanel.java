package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import net.sourcewalker.fakegps.data.IDataModel;

/**
 * Contains the GUI elements used for interaction with the device emulator.
 * 
 * @author Xperimental
 */
public class DevicePanel extends PanelBase {

    /**
     * Defines the default port for connecting to the emulator.
     */
    private static final int DEFAULT_PORT = 5554;

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = -143033466306885706L;

    /**
     * Contains the textbox used for port entry.
     */
    private JTextField portBox;

    /**
     * Contains the button used for toggling the connection state.
     */
    private JToggleButton connectButton;

    /**
     * Contains the label in front of the port entry box.
     */
    private JLabel portLabel;

    /**
     * Create a new instance of this panel.
     * 
     * @param dataModel
     *            Data model to use.
     */
    public DevicePanel(final IDataModel dataModel) {
        super(DevicePanel.class, "devicePanel", new DeviceActions(dataModel),
                dataModel);
        ((DeviceActions) getActionsContainer()).setPanel(this);
    }

    /*
     * (non-Javadoc)
     * @see net.sourcewalker.fakegps.gui.PanelBase#initComponents()
     */
    @Override
    protected final void initComponents() {
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout());
        add(gridPanel, BorderLayout.NORTH);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.WEST;
        portLabel = new JLabel();
        gridPanel.add(portLabel, c);

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        portBox = new JTextField();
        gridPanel.add(portBox, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.gridwidth = 2;
        connectButton = new JToggleButton();
        connectButton.setAction(getActions().get("toggleConnect"));
        gridPanel.add(connectButton, c);
    }

    /**
     * Returns the port entered into the text field.
     * 
     * @return Current port.
     */
    public final int getPort() {
        Integer port;
        try {
            port = Integer.parseInt(portBox.getText());
        } catch (NumberFormatException e) {
            port = DEFAULT_PORT;
        }
        return port.intValue();
    }

    /**
     * Sets the text of the port input box.
     * 
     * @param port
     *            New port.
     */
    public final void setPort(final int port) {
        portBox.setText(Integer.toString(port));
    }

    /**
     * Sets the label displayed next to the port input box.
     * 
     * @param label
     *            New label.
     */
    public final void setPortLabel(final String label) {
        portLabel.setText(label);
    }

}
