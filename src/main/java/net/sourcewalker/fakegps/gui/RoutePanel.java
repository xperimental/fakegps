package net.sourcewalker.fakegps.gui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sourcewalker.fakegps.data.IDataModel;
import net.sourcewalker.fakegps.data.IRoute;
import net.sourcewalker.fakegps.data.ModelChangeListener;

/**
 * This class contains the GUI panel used for interaction with the route
 * controller.
 * 
 * @author Xperimental
 */
public class RoutePanel extends PanelBase {

    /**
     * ID for serialization.
     */
    private static final long serialVersionUID = -1202641708255370124L;

    /**
     * Constant for route speed slider maximum.
     */
    private static final int SLIDER_MAXIMUM = 1000;

    /**
     * Conversion factor for converting speed to slider position.
     */
    private static final double SPEED_TO_SLIDER = 100000d;

    /**
     * Contains the slider used for controlling the route speed.
     */
    private JSlider speedSlider;

    /**
     * Contains the button used for starting the route.
     */
    private JToggleButton startButton;

    /**
     * Contains the button used for pausing/unpausing the route.
     */
    private JToggleButton pauseButton;

    /**
     * Contains the button used for stopping the route.
     */
    private JToggleButton stopButton;

    /**
     * Contains the panel holding the route control buttons.
     */
    private JPanel buttonPanel;

    /**
     * Contains the label displayed above the speed slider.
     */
    private JLabel speedLabel;

    /**
     * Create a new instance of this panel using the provided data model.
     * 
     * @param dataModel
     *            Data model to use.
     */
    public RoutePanel(final IDataModel dataModel) {
        super(RoutePanel.class, "routePanel", new RouteActions(dataModel),
                dataModel);
        getModel().addChangeListener(new ModelListener());
    }

    @Override
    protected final void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(getSpeedLabel());
        add(getSpeedSlider());
        add(getButtonPanel());
    }

    /**
     * Returns the label displayed above the speed slider. The label is created
     * in the first method call.
     * 
     * @return Label for speed slider.
     */
    public final JLabel getSpeedLabel() {
        if (speedLabel == null) {
            speedLabel = new JLabel(getResMap().getString(
                    "routePanel.speedLabel.text", ""));
        }
        return speedLabel;
    }

    /**
     * Returns the panel used for the control buttons. The panel is created in
     * the first method call.
     * 
     * @return Button panel.
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(getStartButton());
            buttonPanel.add(getPauseButton());
            buttonPanel.add(getStopButton());
        }
        return buttonPanel;
    }

    /**
     * Returns the route speed slider. The slider is created in the first method
     * call.
     * 
     * @return Route speed slider.
     */
    private JSlider getSpeedSlider() {
        if (speedSlider == null) {
            speedSlider = new JSlider();
            speedSlider.setMinimum(1);
            speedSlider.setMaximum(SLIDER_MAXIMUM);
            speedSlider.setValue(1);
            speedSlider.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(final ChangeEvent e) {
                    double newSpeed = speedSlider.getValue() / SPEED_TO_SLIDER;
                    getModel().setRouteSpeed(newSpeed);
                }
            });
        }
        return speedSlider;
    }

    /**
     * Returns the route start button. The button is created in the first method
     * call.
     * 
     * @return Route start button.
     */
    private JToggleButton getStartButton() {
        if (startButton == null) {
            startButton = new JToggleButton(getActions().get("playRoute"));
        }
        return startButton;
    }

    /**
     * Returns the route pause button. The button is created in the first method
     * call.
     * 
     * @return Route pause button.
     */
    private JToggleButton getPauseButton() {
        if (pauseButton == null) {
            pauseButton = new JToggleButton(getActions().get("pauseRoute"));
        }
        return pauseButton;
    }

    /**
     * Returns the route stop button. The button is created in the first method
     * call.
     * 
     * @return Route stop button.
     */
    private JToggleButton getStopButton() {
        if (stopButton == null) {
            stopButton = new JToggleButton(getActions().get("resetRoute"));
            stopButton.setSelected(true);
        }
        return stopButton;
    }

    /**
     * Updates the selected state of the buttons.
     */
    private void updateButtonState() {
        IRoute route = getModel().getRoute();
        switch (route.getState()) {
        case RUNNING:
            startButton.setSelected(true);
            pauseButton.setSelected(false);
            stopButton.setSelected(false);
            break;
        case PAUSED:
            startButton.setSelected(false);
            pauseButton.setSelected(true);
            stopButton.setSelected(false);
            break;
        default:
            startButton.setSelected(false);
            pauseButton.setSelected(false);
            stopButton.setSelected(true);
            break;
        }
    }

    /**
     * Updates the slider position.
     */
    private void updateSpeedSlider() {
        double speed = getModel().getRouteSpeed();
        int value = (int) (speed * SPEED_TO_SLIDER);
        speedSlider.setValue(value);
    }

    /**
     * This listener class updates the button selection states and the slider
     * position if the data model changes.
     * 
     * @author Xperimental
     */
    private class ModelListener implements ModelChangeListener {

        /*
         * (non-Javadoc)
         * @see net.sourcewalker.fakegps.data.ModelChangeListener#dataChanged()
         */
        @Override
        public void dataChanged() {
            updateButtonState();
            updateSpeedSlider();
        }

    }

}
