package com.example.btl1_dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class Search_Controller extends General_Controller {

    @FXML
    public TextField searchBar;

    @FXML
    private ImageView voice;

    @FXML
    private final Image Voice_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Voice_Button.png").toExternalForm());

    @FXML
    private WebView meaning;

    @FXML
    private ListView<String> suggestion;

    @FXML
    private ImageView searchButton;

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

    public TextField getSearchBar() {
        return searchBar;
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

        suggestion.setCellFactory(new CustomListCellFactory());

        suggestion.setItems(suggestions);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            showSuggestions(newValue);
        });

        if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
            String currentValue = searchBar.getText();
            showSuggestions(currentValue);
        }
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

        suggestion.setItems(filteredSuggestions);
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
                        setFont(Font.font("Arial", FontWeight.BOLD, 15));
                    }
                }
            };
        }
    }

    @FXML
    void search(MouseEvent event) throws Exception {
        String input = searchBar.getText().toLowerCase();
        String path = "src/main/resources/com/example/btl1_dictionary/History.txt";

        String html = Database_Connect.GetWordFromDatabase(input);
        meaning.getEngine().loadContent(Database_Connect.GetWordFromDatabase(input), "text/html");

        if (!Database_Connect.found) {
            voice.setImage(null);
        } else {
            voice.setImage(Voice_Image);
            List<String> lines = new ArrayList<>();
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    lines.add(line.trim());
                }
            }
            br.close();

            int size = lines.size();
            int MAX_LENGTH = 30;
            if (size > MAX_LENGTH) {
                while (size >= MAX_LENGTH) {
                    lines.remove(size - 1);
                    size--;
                }
            }

            lines.removeIf(e -> e.equals(input));
            lines.add(0, input);

            FileWriter fw = new FileWriter(path);
            for (String lineToWrite : lines) {
                fw.write(lineToWrite);
                fw.write("\n");
            }
            fw.close();
        }
    }

    @FXML
    void Voice(MouseEvent event) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        Voice[] voicelist = VoiceManager.getInstance().getVoices();
        for(int  i = 0 ; i  <voicelist.length ; i++) {
            System.out.println("voice :" + voicelist[i].getName());
        }
        if(voice!=null) {
            voice.allocate();
            System.out.println("Voice Rate: " + voice.getRate());
            System.out.println("Voice pitch: " + voice.getPitch());
            System.out.println("Voice Volume: " + voice.getVolume());
            boolean status  = voice.speak(searchBar.getText());
            System.out.println("status " + status);
            voice.deallocate();
        }
    }

    @FXML
    void Fill(MouseEvent event) {
        int selectedIndex = suggestion.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            String selectedItem = suggestion.getItems().get(selectedIndex);
            searchBar.setText(selectedItem);
        }
    }


    @Override
    public void Entered(MouseEvent event) {
        ImageView enteredImageView = (ImageView) event.getSource();

        if (enteredImageView == getHistory_Button()) {
            setHistory_Button(History_Image);
            setSearch_Button(null);
        } else if (enteredImageView == getSaved_Button()) {
            setSaved_Button(Saved_Image);
            setSearch_Button(null);
        } else if (enteredImageView == getEdit_Button()) {
            setEdit_Button(Edit_Image);
            setSearch_Button(null);
        } else if (enteredImageView == getGoogle_Button()) {
            setGoogle_Button(Google_Image);
            setSearch_Button(null);
        } else if (enteredImageView == getGame_Button()) {
            setGame_Button(Game_Image);
            setSearch_Button(null);
        }
    }

    @Override
    public void Exited(MouseEvent event) {
        ImageView exitedImageView = (ImageView) event.getSource();

        if (exitedImageView == getHistory_Button()) {
            setHistory_Button(null);
            setSearch_Button(Search_Image);
        } else if (exitedImageView == getSaved_Button()) {
            setSaved_Button(null);
            setSearch_Button(Search_Image);
        } else if (exitedImageView == getEdit_Button()) {
            setEdit_Button(null);
            setSearch_Button(Search_Image);
        } else if (exitedImageView == getGoogle_Button()) {
            setGoogle_Button(null);
            setSearch_Button(Search_Image);
        } else if (exitedImageView == getGame_Button()) {
            setGame_Button(null);
            setSearch_Button(Search_Image);
        }
    }


}
