package taskbot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for TaskBot using FXML.
 */
public class Main extends Application {
    
    private TaskBot taskBot = new TaskBot("data/taskbot.txt");
    
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("TaskBot");
            fxmlLoader.<MainWindow>getController().setTaskBot(taskBot);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}