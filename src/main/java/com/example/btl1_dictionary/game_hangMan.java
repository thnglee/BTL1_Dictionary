package com.example.btl1_dictionary;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class game_hangMan extends Games_Controller {

    @FXML
    private ImageView hangmanImageView;

    @FXML
    private Label wordLabel;

    @FXML
    private Label guessLabel;

//
//    @FXML
//    private GridPane virtualKeyboardGridPane;



    private String[] words;
    private String selectedWord;
    private StringBuilder currentWord;
    private int incorrectGuessCount;
    private static final int MAX_INCORRECT_GUESSES = 6; // Set the maximum incorrect guesses allowed

    public void initialize() {
        loadWordsFromFile(Path.of("src/main/resources/com/example/btl1_dictionary/Text File/wordsforHangman.txt"));
        resetGame();

        // init keyboard element


    }

    private void loadWordsFromFile(Path filePath) {
        List<String> wordList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                wordList.add(line.toUpperCase());
            }

            if (wordList.isEmpty()) {
                System.err.println("No words found in the file: " + filePath.toString());
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath.toString());
            e.printStackTrace();
        }

        words = wordList.toArray(new String[0]);
    }

    private void selectRandomWord() {
        Random random = new Random();
        selectedWord = words[random.nextInt(words.length)].toUpperCase();
        currentWord = new StringBuilder("_".repeat(selectedWord.length()));
    }

    private void updateWordLabel() {
        wordLabel.setText(currentWord.toString());
    }

    private void resetLabels() {
        guessLabel.setText("Guess a letter:");
    }

    @FXML
    private void resetGame() {
        selectRandomWord();
        updateWordLabel();
        resetHangmanImage();
        resetLabels();
        incorrectGuessCount = 0;

    }


    private void resetHangmanImage() {
        hangmanImageView.setImage(new Image(getClass().getResource("/com/example/btl1_dictionary/Image/hangman/hangman0.png").toExternalForm()));
    }


    @FXML
    private void handleGuess(javafx.event.ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            String letter = button.getText().toUpperCase();
            processGuess(letter);
        }
    }

    @FXML
    private void handleVirtualKeyboardClick(javafx.event.ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button button = (Button) event.getSource();
            String letter = button.getText().toUpperCase();
            processGuess(letter);
//            if (!button.isDisable()) {
//                processGuess(letter);
//                button.setDisable(true);
//            }
        }
    }

    private void processGuess(String letter) {
        if (!currentWord.toString().contains(letter)) {
            boolean correctGuess = false;
            for (int i = 0; i < selectedWord.length(); i++) {
                if (selectedWord.charAt(i) == letter.charAt(0)) {
                    currentWord.setCharAt(i, letter.charAt(0));
                    correctGuess = true;
                }
            }

            if (!correctGuess) {
                handleIncorrectGuess();
            }

            updateWordLabel();

            if (currentWord.toString().equals(selectedWord)) {
                guessLabel.setText("Congratulations! You guessed the word.");
            }
        }
    }

    private void handleIncorrectGuess() {
        incorrectGuessCount++;
        updateHangmanImage();

        if (incorrectGuessCount >= MAX_INCORRECT_GUESSES) {
            // Handle game over (e.g., display a message)
            guessLabel.setText("Game Over! The word was: " + selectedWord);
//            disableLetterButtons(); // Disable letter buttons after the game is over
        }
    }

    private void updateHangmanImage() {
        String imagePath = "/com/example/btl1_dictionary/Image/hangman/" + "hangman" + incorrectGuessCount + ".png";
        hangmanImageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
    }

//    private void disableLetterButtons() {
//        // Disable all letter buttons after the game is over
//        // You can iterate through the buttons and disable them
//        // Example: button.setDisable(true);
//    }
}
