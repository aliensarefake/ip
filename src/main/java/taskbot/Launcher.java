package taskbot;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public final class Launcher {
    private Launcher() {
    }
    public static void main(String[] args) {
        System.setProperty("prism.order", "sw");
        System.setProperty("java.awt.headless", "false");

        Application.launch(Main.class, args);
    }
}