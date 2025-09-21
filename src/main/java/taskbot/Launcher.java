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
        System.setProperty("prism.text", "t2k");
        System.setProperty("javafx.animation.pulse", "60");
        Application.launch(Main.class, args);
    }
}