package com.example.btl1_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Google_Controller {

    @FXML
    private VBox Main_Box;

    @FXML
    private ImageView Background;

    @FXML
    private ImageView Game_Button;

    @FXML
    private final Image Game_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Games_button.png").toExternalForm());

    @FXML
    private ImageView History_Button;

    @FXML
    private final Image History_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/History_button.png").toExternalForm());

    @FXML
    private ImageView Saved_Button;

    @FXML
    private final Image Saved_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Saved_button.png").toExternalForm());

    @FXML
    private ImageView Search_Button;

    @FXML
    private final Image Search_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Search_button.png").toExternalForm());

    @FXML
    private ImageView langFrom;

    @FXML
    private final Image Vie = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Vietnam_Button.png").toExternalForm());

    @FXML
    private ImageView langTo;

    @FXML
    private final Image Eng = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/English_Button.png").toExternalForm());

    @FXML
    private TextArea input;

    @FXML
    private TextArea output;

    @FXML
    private ImageView Switch_Button;

    @FXML
    private Button Translate_Button;

    private boolean checked = true;

    private static String ConnectToGGAPI( String input, String langFrom, String langTo) throws IOException, IOException {
        String urlSource = "https://script.google.com/macros/s/AKfycby3AOWmhe32TgV9nW-Q0TyGOEyCHQeFiIn7hRgy5m8jHPaXDl2GdToyW_3Ys5MTbK6wjg/exec"
                        + "?q=" + URLEncoder.encode(input, "UTF-8")
                        + "&target=" + langTo
                        + "&source=" + langFrom;
        URL url = new URL(urlSource);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder res = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            res.append(line);
            res.append("\n");
        }
        bf.close();
        return res.toString();
    }

    @FXML
    void translate(MouseEvent event) throws IOException {
        String in = input.getText();
        String res = (checked) ? ConnectToGGAPI(in, "en", "vi") : ConnectToGGAPI(in, "vi", "en");
        output.setText(res);
    }

    @FXML
    void switchLanguage(MouseEvent event) {
        if (checked) {
            checked = false;
            langFrom.setImage(Vie);
            langTo.setImage(Eng);
        }
        else {
            checked = true;
            langFrom.setImage(Eng);
            langTo.setImage(Vie);
        }
    }

    @FXML
    void switchSceneToGame(MouseEvent event) {
        switchScene("FXML File/games.fxml",  event);
    }

    @FXML
    void switchSceneToHistory(MouseEvent event) {
        switchScene("FXML File/history.fxml", event);
    }

    @FXML
    void switchSceneToSearch(MouseEvent event) {
        switchScene("FXML File/search.fxml", event);
    }

    @FXML
    void switchSceneToSaved(MouseEvent event) {
        switchScene("FXML File/saved.fxml", event);
    }

    @FXML
    void handleSceneSwitch(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();

        if (clickedImageView == Search_Button) {
            switchSceneToSearch(event);
        } else if (clickedImageView == History_Button) {
            switchSceneToHistory(event);
        } else if (clickedImageView == Game_Button) {
            switchSceneToGame(event);
        } else if (clickedImageView == Saved_Button) {
            switchSceneToSaved(event);
        }
    }

    @FXML
    private void switchScene(String fxmlPath, MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader, 900, 620);
            stage.setTitle("3L DICTIONARY");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    @FXML
    void Entered(MouseEvent event) {
        ImageView enteredImageView = (ImageView) event.getSource();

        if (enteredImageView == Search_Button) {
            Search_Button.setImage(Search_Image);
        } else if (enteredImageView == History_Button) {
            History_Button.setImage(History_Image);
        } else if (enteredImageView == Game_Button) {
            Game_Button.setImage(Game_Image);
        } else if (enteredImageView == Saved_Button) {
            Saved_Button.setImage(Saved_Image);
        }
    }

    @FXML
    void Exited(MouseEvent event) {
        ImageView exitedImageView = (ImageView) event.getSource();

        if (exitedImageView == Search_Button) {
            Search_Button.setImage(null);
        } else if (exitedImageView == History_Button) {
            History_Button.setImage(null);
        } else if (exitedImageView == Game_Button) {
            Game_Button.setImage(null);
        } else if (exitedImageView == Saved_Button) {
            Saved_Button.setImage(null);
        }
    }


}
