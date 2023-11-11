package com.example.btl1_dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.net.*;
import java.util.ResourceBundle;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


public class Search_Controller implements Initializable {

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
    private ImageView Edit_Button;

    @FXML
    private final Image Edit_Image = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/Edit_button.png").toExternalForm());


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

        String html = Database_Controller.GetWordFromDatabase(input);
        meaning.getEngine().loadContent(Database_Controller.GetWordFromDatabase(input), "text/html");

        if (!Database_Controller.found) {
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


    @FXML
    void switchSceneToGame(MouseEvent event) {
        switchScene("FXML File/games.fxml",  event);
    }

    @FXML
    void switchSceneToGoogle(MouseEvent event) {
        switchScene("FXML File/google.fxml", event);
    }

    @FXML
    void switchSceneToHistory(MouseEvent event) {
        switchScene("FXML File/history.fxml", event);
    }

    @FXML
    void switchSceneToSaved(MouseEvent event) {
        switchScene("FXML File/saved.fxml", event);
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
        } else if (clickedImageView == Edit_Button) {
            switchSceneToHistory(event);
        }
    }

    @FXML
    private void switchScene(String fxmlPath, MouseEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader, 875, 650);
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
        } else if (enteredImageView == Edit_Button) {
            Edit_Button.setImage(Edit_Image);
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
        } else if (exitedImageView == Edit_Button) {
            Edit_Button.setImage(null);
        }
    }


}
