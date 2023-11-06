package com.example.btl1_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class History_Controller {

    @FXML
    private VBox Main_Box;

    @FXML
    private ImageView Background;

    @FXML
    private ImageView Game_Button;

    @FXML
    private final Image Game_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Games_button.png").toExternalForm());

    @FXML
    private ImageView Google_Button;

    @FXML
    private final Image Google_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Google_button.png").toExternalForm());

    @FXML
    private ImageView Saved_Button;

    @FXML
    private final Image Saved_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Saved_button.png").toExternalForm());

    @FXML
    private ImageView Search_Button;

    @FXML
    private final Image Search_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Search_button.png").toExternalForm());

    @FXML
    private TextField word1;

    @FXML
    private TextField word10;

    @FXML
    private TextField word2;

    @FXML
    private TextField word3;

    @FXML
    private TextField word4;

    @FXML
    private TextField word5;

    @FXML
    private TextField word6;

    @FXML
    private TextField word7;

    @FXML
    private TextField word8;

    @FXML
    private TextField word9;

    public void initialize() throws IOException {
        try {
            String path = "src/main/resources/com/example/btl1_dictionary/History.txt";
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String line = "";
            int count = 0;
            List<String> list = new ArrayList<String>();
            while ((line = bf.readLine()) != null && count <= 10) {
                list.add(line.trim());
                count++;
            }
            bf.close();

            int size = list.size();
            if (size < 10) {
                int tmp = 10 - size;
                while (tmp > 0) {
                    list.add(" ");
                    tmp--;
                }
            }
            word1.setText(list.get(0));
            word2.setText(list.get(1));
            word3.setText(list.get(2));
            word4.setText(list.get(3));
            word5.setText(list.get(4));
            word6.setText(list.get(5));
            word7.setText(list.get(6));
            word8.setText(list.get(7));
            word9.setText(list.get(8));
            word10.setText(list.get(9));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void entered(MouseEvent event) {
        TextField entered = (TextField) event.getSource();
        Font font = Font.font("Segoe UI Black",24);
        if (entered == word1) {
            word1.setFont(font);
        } else if (entered == word2) {
            word2.setFont(font);
        } else if (entered == word3) {
            word3.setFont(font);
        } else if (entered == word4) {
            word4.setFont(font);
        } else if (entered == word5) {
            word5.setFont(font);
        } else if (entered == word6) {
            word6.setFont(font);
        } else if (entered == word7) {
            word7.setFont(font);
        } else if (entered == word8) {
            word8.setFont(font);
        } else if (entered == word9) {
            word9.setFont(font);
        } else if (entered == word10) {
            word10.setFont(font);
        }
    }

    @FXML
    void attempt(MouseEvent event) throws Exception {
    }

    @FXML
    void exited(MouseEvent event) {
        TextField exited = (TextField) event.getSource();
        Font font = Font.font("Segoe UI",20);
        if (exited == word1) {
            word1.setFont(font);
        } else if (exited == word2) {
            word2.setFont(font);
        } else if (exited == word3) {
            word3.setFont(font);
        } else if (exited == word4) {
            word4.setFont(font);
        } else if (exited == word5) {
            word5.setFont(font);
        } else if (exited == word6) {
            word6.setFont(font);
        } else if (exited == word7) {
            word7.setFont(font);
        } else if (exited == word8) {
            word8.setFont(font);
        } else if (exited == word9) {
            word9.setFont(font);
        } else if (exited == word10) {
            word10.setFont(font);
        }
    }


    @FXML
    void switchSceneToGame(MouseEvent event) {
        switchScene("FXML File/games.fxml",  event);
    }

    @FXML
    void switchSceneToGoogle(MouseEvent event) {
        switchScene("FXML File/google.fxml", event);
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
        } else if (clickedImageView == Google_Button) {
            switchSceneToGoogle(event);
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
        }
    }

    @FXML
    void Entered(MouseEvent event) {
        ImageView enteredImageView = (ImageView) event.getSource();

        if (enteredImageView == Search_Button) {
            Search_Button.setImage(Search_Image);
        } else if (enteredImageView == Google_Button) {
            Google_Button.setImage(Google_Image);
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
        } else if (exitedImageView == Google_Button) {
            Google_Button.setImage(null);
        } else if (exitedImageView == Game_Button) {
            Game_Button.setImage(null);
        } else if (exitedImageView == Saved_Button) {
            Saved_Button.setImage(null);
        }
    }
}
