package com.example.btl1_dictionary;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game_speaking extends Games_Controller {
    @FXML
    private TextField Number;

    @FXML
    private ImageView Rate;

    @FXML
    private ImageView micro;

    @FXML
    private TextField nextStep;

    @FXML
    private ImageView next_button;

    @FXML
    private TextField question;

    @FXML
    private AnchorPane resultField;

    @FXML
    private TextField score;

    @FXML
    private TextField sentence;

    @FXML
    private AnchorPane speakField;

    @FXML
    private TextField total_score;

    @FXML
    private TextField youSpeak;

    @FXML
    private TextField originalSentence;

    @FXML
    private TextField isMicOn;

    @FXML
    private final Image excellent = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/speaking/excellent.png").toExternalForm());

    @FXML
    private final Image verygood = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/speaking/verygood.png").toExternalForm());

    @FXML
    private final Image good = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/speaking/good.png").toExternalForm());

    @FXML
    private final Image poor = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/speaking/poor.png").toExternalForm());

    @FXML
    private final Image terrible = new Image(getClass().getResource("/com/example/btl1_dictionary/Image/speaking/terrible.png").toExternalForm());


    private String yourVoice;

    private List<String> sentences = new ArrayList<String>();

    int index = 0;
    int number = 1;
    int numberSentences = 0;
    int sentenceLeft = 0;

    public void initialize() throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/btl1_dictionary/Text File/QuesforLuyenNoi.txt"));
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                if (!line.equals(" ")) {
                    sentences.add(line.substring(3));
                }
            }
        }
        br.close();

        numberSentences = sentences.size();
        sentenceLeft = numberSentences;
        Number.setText(number + "/" + numberSentences);

        Random rand = new Random();
        index = rand.nextInt(sentences.size());
        sentence.setText(sentences.get(index));
    }

    @FXML
    void speakToMic(MouseEvent event) {
        Thread thread = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    micro.setImage(Micro_On_Image);
                    isMicOn.setText("RECORDING ...");
                });

                String pythonScriptPath = "src/main/resources/com/example/btl1_dictionary/Speech.py";
                String[] cmd = new String[2];
                cmd[0] = "python";
                cmd[1] = pythonScriptPath;

                System.out.println("ready to speech");
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(cmd);

                InputStreamReader reader = new InputStreamReader(pr.getInputStream());
                BufferedReader bfr = new BufferedReader(reader);
                String line = "";
                String result = "";

                while ((line = bfr.readLine()) != null) {
                    result += line;
                }
                bfr.close();

                String finalResult = result;
                Platform.runLater(() -> {
                    yourVoice = finalResult;
                    speakField.setVisible(false);
                    resultField.setVisible(true);
                    originalSentence.setText(sentence.getText());
                    int last_score = 0;
                    if (yourVoice.length() > 35) {
                        youSpeak.setText(yourVoice.substring(35));
                    } else {
                        youSpeak.setText(null);
                    }
                    if (youSpeak.getText() == null) {
                        last_score = 0;
                        score.setText(String.valueOf(last_score));
                    } else {
                        last_score = calculateScore(youSpeak.getText(),originalSentence.getText());
                        score.setText(String.valueOf(last_score));
                    }
                    Rating(last_score);
                    int total = Integer.parseInt(total_score.getText()) + Integer.parseInt(score.getText());
                    total_score.setText(String.valueOf(total));
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    void Rating(int score) {
        if (score == 0) {
            Rate.setImage(terrible);
        } else if (1 <= score && score <= 4) {
            Rate.setImage(poor);
        } else if (5 <= score && score <= 7) {
            Rate.setImage(good);
        } else if (8 <= score && score <= 9) {
            Rate.setImage(verygood);
        } else if (score == 10) {
            Rate.setImage(excellent);
        }
    }

    int calculateScore(String youSpeak, String sentence) {
        int count = 0;
        String[] str1 = standardized(youSpeak).split(" ");
        String[] str2 = standardized(sentence).split(" ");
        int size = Math.min(str1.length, str2.length);
        for (int i = 0; i < size; i++) {
            if (str1[i].equals(str2[i])) {
                count++;
            }
        }
        System.out.println(count + "/" + size);
        return (int) (count * 10)/ size;
    }

    @Override
    void Next(MouseEvent event) {
        sentenceLeft--;
        sentences.remove(index);
        Random rand = new Random();
        index = rand.nextInt(sentenceLeft);
        sentence.setText(sentences.get(index));
        number++;
        Number.setText(number + "/" + numberSentences);

        micro.setImage(Micro_Image);
        isMicOn.setText("PLEASE SPEAK FOLLOW SENTENCE ABOVE");
        speakField.setVisible(true);
        resultField.setVisible(false);
    }
}
