package com.example.btl1_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Start_Controller {

    @FXML
    private VBox Start_Box;

    @FXML
    private ImageView Start;
    @FXML
    private final Image curr_image = new Image(getClass().getResource("/com/example/btl1_dictionary/start.png").toExternalForm());
    @FXML
    private final Image new_image = new Image(getClass().getResource("/com/example/btl1_dictionary/new_start.png").toExternalForm());

    @FXML
    void blurButton(MouseEvent event) {
        Start.setImage(new_image);
    }

    @FXML
    void resetButton(MouseEvent event) {
        Start.setImage(curr_image);
    }


    @FXML
    void switchToMain(MouseEvent event) {
        Start_Box.setVisible(false);
    }


    public void switchToScene2(MouseEvent event) throws IOException {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("search.fxml"));
            Scene scene = new Scene(fxmlLoader, 900, 620);
            stage.setTitle("3L DICTIONARY");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
}
