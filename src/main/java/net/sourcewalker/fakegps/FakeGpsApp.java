package net.sourcewalker.fakegps;

import net.sourcewalker.fakegps.gui.MainFrame;

import org.jdesktop.application.Application;

/**
 * Application class for FakeGPS. Also contains a <code>main</code> static
 * method so that it can be launched from the command-line.
 * 
 * @author Xperimental
 */
public class FakeGpsApp extends Application {

    /**
     * This method is run from the JDesktop framework to start the application.
     */
    protected final void startup() {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    /**
     * Startup method for launching the application. Uses the JDesktop framework
     * to start the application class.
     * 
     * @param args
     *            Command-line parameters.
     */
    public static void main(final String[] args) {
        Application.launch(FakeGpsApp.class, args);
    }
}
