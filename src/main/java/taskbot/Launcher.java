package taskbot;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public final class Launcher {
    private Launcher() {
    }
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}