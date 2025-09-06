package taskbot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    
    private TaskBot taskBot;
    
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image taskBotImage = new Image(this.getClass().getResourceAsStream("/images/TaskBot.png"));
    
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
            DialogBox.getTaskBotDialog("Hello! I'm TaskBot\nWhat can I do for you?", taskBotImage)
        );
    }
    
    public void setTaskBot(TaskBot t) {
        taskBot = t;
    }
    
    /**
     * Creates two dialog boxes, one echoing user input and the other containing TaskBot's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = taskBot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTaskBotDialog(response, taskBotImage)
        );
        userInput.clear();
        
        if (input.trim().equals("bye")) {
            javafx.application.Platform.exit();
        }
    }
}