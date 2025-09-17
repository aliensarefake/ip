package taskbot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Collections;

/**
 * An example of a custom control using FXML.
 * This control represents a dialog box consisting of an ImageView to represent the speaker's face and a label
 * containing text from the speaker.
 */
public final class DialogBox extends HBox {

    public enum DialogType {
        USER,
        TASKBOT,
        ERROR
    }
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogType type;

    private DialogBox(String text, Image img, DialogType type) {
        this.type = type;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        dialog.setText(text);
        displayPicture.setImage(img);

        applyStyles();
    }
    
    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }
    
    private void applyStyles() {
        final int userImageSize = 50;
        final int botImageSize = 40;

        switch (type) {
            case USER:
                dialog.setStyle("-fx-background-color: #E3F2FD; "
                    + "-fx-background-radius: 15; "
                    + "-fx-padding: 10; "
                    + "-fx-font-size: 14px;");
                displayPicture.setFitHeight(userImageSize);
                displayPicture.setFitWidth(userImageSize);
                break;
            case TASKBOT:
                dialog.setStyle("-fx-background-color: #F5F5F5; "
                    + "-fx-background-radius: 15; "
                    + "-fx-padding: 10; "
                    + "-fx-font-size: 14px;");
                displayPicture.setFitHeight(botImageSize);
                displayPicture.setFitWidth(botImageSize);
                break;
            case ERROR:
                dialog.setStyle("-fx-background-color: #FFEBEE; "
                    + "-fx-background-radius: 15; "
                    + "-fx-padding: 10; "
                    + "-fx-font-size: 14px; "
                    + "-fx-text-fill: #C62828; "
                    + "-fx-font-weight: bold;");
                displayPicture.setFitHeight(botImageSize);
                displayPicture.setFitWidth(botImageSize);
                break;
            default:
                break;
        }

        displayPicture.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 3, 0, 0, 1);");

        javafx.scene.shape.Circle clip = new javafx.scene.shape.Circle(
            displayPicture.getFitWidth() / 2,
            displayPicture.getFitHeight() / 2,
            Math.min(displayPicture.getFitWidth(), displayPicture.getFitHeight()) / 2
        );
        displayPicture.setClip(clip);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img, DialogType.USER);
    }
    
    public static DialogBox getTaskBotDialog(String text, Image img) {
        var db = new DialogBox(text, img, DialogType.TASKBOT);
        db.flip();
        return db;
    }

    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img, DialogType.ERROR);
        db.flip();
        return db;
    }
}