package com.example.btl1_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
public class Start_Screen {

    @FXML
    private VBox Start_Box;

    @FXML
    private ImageView Start;
    @FXML
    private final Image curr_image = new Image("C:\\Users\\ADM\\OneDrive\\Documents\\IDEA Projects\\BTL1_Dictionary\\src\\main\\resources\\com\\example\\btl1_dictionary\\start.png");
    @FXML
    private final Image new_image = new Image("C:\\Users\\ADM\\OneDrive\\Documents\\IDEA Projects\\BTL1_Dictionary\\src\\main\\resources\\com\\example\\btl1_dictionary\\new_start.png");

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
}
