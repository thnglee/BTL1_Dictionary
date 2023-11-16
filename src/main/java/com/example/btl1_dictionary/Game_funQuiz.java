package com.example.btl1_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game_funQuiz extends Games_Controller {

    static class Quiz {
        private int id;
        private String question;
        private String answer;
        private String explanation;
        private Image image;

        public Quiz(int id, String question, String answer, String explanation) {
            this.id = id;
            this.question = question;
            this.answer = answer;
            this.explanation = explanation;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }
    }

    List<Quiz> quizzes = new ArrayList<>();
    List<String> questions = new ArrayList<>();
    List<String> answers = new ArrayList<>();
    List<String> explanations = new ArrayList<>();

    @FXML
    private TextField Number;

    @FXML
    private TextField Score;

    @FXML
    private TextField answer;

    @FXML
    private ImageView back_button;

    @FXML
    private ImageView check;

    @FXML
    private TextArea explanation;

    @FXML
    private TextField nextStep;

    @FXML
    private ImageView next_button;

    @FXML
    private TextField question;

    @FXML
    private AnchorPane result;

    @FXML
    private ImageView showResult;

    @FXML
    private ImageView status;

    @FXML
    private AnchorPane submit;

    @FXML
    private ImageView submit_button;

    @FXML
    private TextField yourAnswer;

    private boolean isTrue = false;

    private boolean first;
    int index = 0;
    int score = 0;
    int numberQuestion = 0;

    int questionLeft = 0;
    int number = 1;

    public Game_funQuiz() throws Exception {
        first = true;
    }

    public void initialize() throws Exception {
        if (first) {
            quizzes.clear();
            Database_Connect.loadQuiz();
            questions = Database_Connect.questions;
            answers = Database_Connect.answers;
            explanations = Database_Connect.explanations;
            numberQuestion = questions.size();
            questionLeft = numberQuestion;
            System.out.println(questions.size());

            for (int i = 0; i < numberQuestion; i++) {
                Quiz quiz = new Quiz(i, questions.get(i), answers.get(i), explanations.get(i) == null ? "" : explanations.get(i));
                quizzes.add(quiz);
                //setImage(i);
            }

            Random rand = new Random();
            index = rand.nextInt(numberQuestion);

            question.setText(quizzes.get(index).question.toUpperCase());
            Number.setText(number + "/" + numberQuestion);
            first = false;
        }

    }


    void setImage(int id) {
        String path = "/com/example/btl1_dictionary/Image/funQUiz/Question" + String.valueOf(id+1) +  ".png";
        quizzes.get(id).setImage(new Image(getClass().getResource(path).toExternalForm()));
    }

    @FXML
    void Submit(MouseEvent event) {
        String answer = optimize(yourAnswer.getText());
        if (answer.equals(optimize(quizzes.get(index).answer))) {
            isTrue = true;
            score += 10;
            Score.setText(String.valueOf(score));
            nextStep.setText("NEXT");
            status.setImage(quizzes.get(index).image);
            System.out.println(true);
        }
    }

    String optimize(String input) {
        input = input.trim();
        input = input.toLowerCase();
        if (input.startsWith("a ")) {
            input = input.substring(2);
        }
        if (input.startsWith("the ")) {
            input = input.substring(4);
        }
        input = input.replace(".","");
        return input;
    }

    @FXML
    void Back(MouseEvent event) {
        switchScene("FXML File/games.fxml",event);
    }

    @FXML
    void nextQuestion(MouseEvent event) {
        quizzes.remove(index);
        questionLeft--;
        if (numberQuestion==0) {
            nextStep.setVisible(false);
        }
        Random rand = new Random();
        index = rand.nextInt(questionLeft);
        question.setText(quizzes.get(index).question.toUpperCase());
        nextStep.setText("SKIP");
        number++;
        Number.setText(number + "/" + numberQuestion);
    }

    @FXML
    void onKeyTyped(KeyEvent event) {

    }
}
