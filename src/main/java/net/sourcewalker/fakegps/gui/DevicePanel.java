package net.sourcewalker.fakegps.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sourcewalker.fakegps.data.IDataModel;

public class DevicePanel extends PanelBase {

    private static final long serialVersionUID = -143033466306885706L;
    private JTextField portBox;
    private JButton connectButton;
    private JButton disconnectButton;

    public DevicePanel(final IDataModel dataModel) {
        super(DevicePanel.class, "devicePanel", new DeviceActions(dataModel),
                dataModel);
        ((DeviceActions) actionsContainer).setPanel(this);
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
        JLabel portLabel = new JLabel(resMap.getString("devicePanel.portLabel",
                ""));
        gridPanel.add(portLabel, c);

        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 3;
        portBox = new JTextField();
        portBox.setText(resMap.getString("devicePanel.defaultPort", ""));
        gridPanel.add(portBox, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.gridwidth = 2;
        connectButton = new JButton();
        connectButton.setAction(actions.get("connect"));
        gridPanel.add(connectButton, c);

        c.gridx = 2;
        c.gridwidth = 2;
        c.weightx = 1;
        disconnectButton = new JButton();
        disconnectButton.setAction(actions.get("disconnect"));
        gridPanel.add(disconnectButton, c);
    }

    /**
     * Returns the port entered into the text field.
     * 
     * @return Current port.
     */
    public int getPort() {
        Integer port;
        try {
            port = Integer.parseInt(portBox.getText());
        } catch (NumberFormatException e) {
            port = 5554;
        }
        return port.intValue();
    }

}
