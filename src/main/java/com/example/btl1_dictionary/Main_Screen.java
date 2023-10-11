package com.example.btl1_dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Main_Screen {

    @FXML
    private VBox Main_Box;

    @FXML
    private ImageView Game_Button;

    @FXML
    private ImageView Google_Button;

    @FXML
    private ImageView History_Button;

    @FXML
    private ImageView Saved_Button;

    @FXML
    private ImageView Search_Button;

    ArrayList<String> words = new ArrayList<>(
            Arrays.asList("Aston Villa", "Arsenal","Bournemouth", "Chelsea", "Liverpool",
                    "Luton Town", "Newcastle", "Tottenham", "Man City", "Man Utd", "Fulham",
                    "Wolves", "Brighton", "Everton", "Burnley", "Sheffield", "Nottingham",
                    "Brentford", "Crystal Palace")
    );

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> listView;

    @FXML
    void search(ActionEvent event) {
        listView.getItems().clear();
        listView.getItems().addAll(searchList(searchBar.getText(),words));
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll(words);
    }

    private List<String> searchList(String searchWords, List<String> listOfStrings) {

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }

}
