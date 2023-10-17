package com.example.btl1_dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search_Screen {

    @FXML
    private ImageView Background;

    @FXML
    private ImageView Game_Button;

//    @FXML
//    private final Image Game_Image = new Image("src\\main\\resources\\com\\example\\btl1_dictionary\\Games_button.png");

    @FXML
    private ImageView Google_Button;

//    @FXML
//    private final Image Google_Image = new Image("src/main/resources/com/example/btl1_dictionary/start.png");

    @FXML
    private ImageView History_Button;

    //@FXML
    //private final Image History_Image = new Image("src/main/resources/com/example/btl1_dictionary/new_start.png");

    @FXML
    private VBox Main_Box;

    @FXML
    private ImageView Saved_Button;

//    @FXML
//    private final Image Saved_Image = new Image("src\\main\\resources\\com\\example\\btl1_dictionary\\Saved_button.png");

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

    public Search_Screen() throws FileNotFoundException {
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
    void Dragged(MouseEvent event) {
        History_Button.setVisible(true);
        Game_Button.setVisible(true);
        Google_Button.setVisible(true);
        Saved_Button.setVisible(true);
    }

    @FXML
    void Reset(MouseEvent event) {
        History_Button.setVisible(false);
        Game_Button.setVisible(false);
        Google_Button.setVisible(false);
        Saved_Button.setVisible(false);
    }

    @FXML
    void search(MouseEvent event) {

    }

    @FXML
    void switchSceneToGame(MouseEvent event) {

    }

    @FXML
    void switchSceneToGoogle(MouseEvent event) {

    }

    @FXML
    void switchSceneToHis(MouseEvent event) {

    }

    @FXML
    void switchSceneToSaved(MouseEvent event) {

    }

}
