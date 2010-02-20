package net.sourcewalker.fakegps.gui;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JToggleButton;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.ModelChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.application.Action;
import org.jdesktop.swingx.mapviewer.GeoPosition;

/**
 * This class contains actions for comminucating with the Android emulator
 * device.
 * 
 * @author Xperimental
 */
public class DeviceActions extends ActionsBase {

    /**
     * Logger instance for this class.
     */
    private static final Log LOGGER = LogFactory.getLog(DeviceActions.class);

    /**
     * Contains a reference to the GUI panel associated with this actions
     * instance.
     */
    private DevicePanel devicePanel;

    /**
     * Contains the socket for communication with the device, if the connection
     * is open.
     */
    private Socket deviceSocket = null;

    /**
     * Contains the stream used for writing to the device.
     */
    private PrintStream deviceStream = null;

    /**
     * Creates a new instance associated with the data model.
     * 
     * @param dataModel
     *            Data model to use.
     */
    public DeviceActions(final IDataModel dataModel) {
        super(dataModel);

        dataModel.addChangeListener(new PositionListener());
    }

    /**
     * Toggles the connection state to the device. The action should be
     * associated with a {@link JToggleButton} button.
     * 
     * @param evt
     *            Event details.
     */
    @Action
    public final void toggleConnect(final ActionEvent evt) {
        JToggleButton toggle = (JToggleButton) evt.getSource();
        if (!isConnected()) {
            int port = devicePanel.getPort();
            try {
                deviceSocket = new Socket("localhost", port);
                deviceStream = new PrintStream(deviceSocket.getOutputStream());
            } catch (IOException e) {
                LOGGER.error("Error connecting: " + e);
                deviceSocket = null;
            }
        } else {
            try {
                deviceStream.close();
                deviceSocket.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing connection: " + e);
            }
            deviceSocket = null;
        }
        toggle.setSelected(isConnected());
    }

    /**
     * Set the panel to use for reading the port to connect to.
     * 
     * @param panel
     *            Device panel to use.
     */
    public final void setPanel(final DevicePanel panel) {
        devicePanel = panel;
    }

    /**
     * Returns the current connection status.
     * 
     * @return True, if there is a live connection to the device.
     */
    public final boolean isConnected() {
        return deviceSocket != null && deviceSocket.isConnected();
    }

    /**
     * Sends a new position information to the device.
     * 
     * @param latitude
     *            Latitude to send.
     * @param longitude
     *            Longitude to send.
     */
    public final void sendNewPosition(final double latitude,
            final double longitude) {
        DecimalFormat fmt = new DecimalFormat("0.00000", DecimalFormatSymbols
                .getInstance(Locale.US));
        StringBuilder msg = new StringBuilder();
        msg.append("geo fix ");
        msg.append(fmt.format(longitude));
        msg.append(" ");
        msg.append(fmt.format(latitude));
        System.out.println("Sending: " + msg.toString());

        deviceStream.print(msg.toString() + "\r\n");
    }

    /**
     * Listener class for reading the route position information from the data
     * model.
     * 
     * @author Xperimental
     */
    private class PositionListener implements ModelChangeListener {

        /*
         * (non-Javadoc)
         * @see net.sourcewalker.fakegps.data.ModelChangeListener#dataChanged()
         */
        @Override
        public void dataChanged() {
            if (isConnected()) {
                GeoPosition pos = getModel().getRoutePosition();
                if (pos != null) {
                    sendNewPosition(pos.getLatitude(), pos.getLongitude());
                }
            }
        }

    }

}
