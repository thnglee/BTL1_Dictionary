package com.example.btl1_dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search_Controller {

    @FXML
    private VBox Main_Box;

    @FXML
    private ImageView Background;

    @FXML
    private ImageView Game_Button;

    @FXML
    private final Image Game_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Games_button.png").toExternalForm());

    @FXML
    private ImageView Google_Button;

    @FXML
    private final Image Google_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Google_button.png").toExternalForm());

    @FXML
    private ImageView History_Button;

    @FXML
    private final Image History_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/History_button.png").toExternalForm());

    @FXML
    private ImageView Saved_Button;

    @FXML
    private final Image Saved_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Saved_button.png").toExternalForm());

    @FXML
    private ImageView Search_Button;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

    private ObservableList<String> suggestions;

    private List<String> wordList = new ArrayList<String>();

    private void readWordList(List<String> wordList) throws FileNotFoundException {
        String path = "src/main/resources/com/example/btl1_dictionary/Dictionaries.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty() && line.charAt(0) == '@') {
                    wordList.add(line.substring(1));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Search_Controller() throws FileNotFoundException {
        try {
            readWordList(wordList);
            suggestions = FXCollections.observableArrayList(wordList);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(KeyEvent event) {

        listView.setCellFactory(new CustomListCellFactory());

        listView.setItems(suggestions);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            showSuggestions(newValue);
        });
    }

    private void showSuggestions(String input) {
        ObservableList<String> filteredSuggestions = FXCollections.observableArrayList();
        List<String> matchingPrefixSuggestions = new ArrayList<>();
        List<String> nonMatchingSuggestions = new ArrayList();

        for (String suggestion : suggestions) {
            if (suggestion.toLowerCase().startsWith(input.toLowerCase())) {
                matchingPrefixSuggestions.add(suggestion);
            } else if (suggestion.toLowerCase().contains(input.toLowerCase())) {
                nonMatchingSuggestions.add(suggestion);
            }
        }

        matchingPrefixSuggestions.sort((s1, s2) -> {
            String prefix = input.toLowerCase();
            return Integer.compare(s1.toLowerCase().indexOf(prefix), s2.toLowerCase().indexOf(prefix));
        });

        filteredSuggestions.addAll(matchingPrefixSuggestions);
        filteredSuggestions.addAll(nonMatchingSuggestions);

        listView.setItems(filteredSuggestions);
    }

    public static class CustomListCellFactory implements Callback<ListView<String>, ListCell<String>> {
        @Override
        public ListCell<String> call(ListView<String> param) {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setFont(Font.font("Arial", FontWeight.BOLD, 20));
                    }
                }
            };
        }
    }


    @FXML
    void switchSceneToGame(MouseEvent event) {
        switchScene("games.fxml",  event);
    }

    @FXML
    void switchSceneToGoogle(MouseEvent event) {
        switchScene("google.fxml", event);
    }

    @FXML
    void switchSceneToHistory(MouseEvent event) {
        switchScene("history.fxml", event);
    }

    @FXML
    void switchSceneToSaved(MouseEvent event) {
        switchScene("saved.fxml", event);
    }

    @FXML
    void handleSceneSwitch(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();

        if (clickedImageView == History_Button) {
            switchSceneToHistory(event);
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
            // Handle the exception as needed
        }
    }

    @FXML
    void Entered(MouseEvent event) {
        ImageView enteredImageView = (ImageView) event.getSource();

        if (enteredImageView == History_Button) {
            History_Button.setImage(History_Image);
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

        if (exitedImageView == History_Button) {
            History_Button.setImage(null);
        } else if (exitedImageView == Google_Button) {
            Google_Button.setImage(null);
        } else if (exitedImageView == Game_Button) {
            Game_Button.setImage(null);
        } else if (exitedImageView == Saved_Button) {
            Saved_Button.setImage(null);
        }
    }
}
