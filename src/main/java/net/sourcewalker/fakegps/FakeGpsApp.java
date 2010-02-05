package net.sourcewalker.fakegps;

import org.jdesktop.application.Application;

public class FakeGpsApp extends Application {

    /*
     * (non-Javadoc)
     * @see org.jdesktop.application.Application#startup()
     */
    protected void startup() {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Application.launch(FakeGpsApp.class, args);
    }
}
