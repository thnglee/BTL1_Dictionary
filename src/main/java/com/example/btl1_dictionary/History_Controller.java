package com.example.btl1_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class History_Controller extends General_Controller {

    @FXML
    private TextField word1;
    @FXML
    private ImageView remove1;

    @FXML
    private TextField word2;
    @FXML
    private ImageView remove2;

    @FXML
    private TextField word3;
    @FXML
    private ImageView remove3;

    @FXML
    private TextField word4;
    @FXML
    private ImageView remove4;

    @FXML
    private TextField word5;
    @FXML
    private ImageView remove5;

    @FXML
    private TextField word6;
    @FXML
    private ImageView remove6;

    @FXML
    private TextField word7;
    @FXML
    private ImageView remove7;

    @FXML
    private TextField word8;
    @FXML
    private ImageView remove8;

    @FXML
    private TextField word9;
    @FXML
    private ImageView remove9;

    @FXML
    private TextField word10;
    @FXML
    private ImageView remove10;

    @FXML
    private BarChart<String, Number> frequency;

    @FXML
    private TextField today;

    @FXML
    private TextField schedule;

    public void initialize() throws IOException {
        try {
            String historyPath = "src/main/resources/com/example/btl1_dictionary/History.txt";
            BufferedReader bf = new BufferedReader(new FileReader(historyPath));
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

            boolean checkDate = true;
            boolean newDay = false;
            String lastDate = "";
            String frequencyPath = "src/main/resources/com/example/btl1_dictionary/Frequency.txt";
            BufferedReader bf2 = new BufferedReader(new FileReader(frequencyPath));
            String line2 = "";
            List<Integer> list2 = new ArrayList<>();
            while ((line2 = bf2.readLine()) != null) {
                if (!line2.isEmpty()) {
                    if (checkDate) {
                        lastDate = line2.trim();
                        checkDate = false;
                        continue;
                    }
                    list2.add(Integer.parseInt(line2.trim()));
                }
            }
            bf2.close();

            long currentTimeMillis = System.currentTimeMillis();
            Date currentDate = new Date(currentTimeMillis);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateString = dateFormat.format(currentDate);
            String day = currentDateString.substring(8,10);
            String month = currentDateString.substring(5,7);
            String year = currentDateString.substring(0,4);
            schedule.setText(day + "/" + month + "/" + year);
            if (!currentDateString.equals(lastDate)) {
                newDay = true;
            }

            today.setText((newDay) ? "0" : list2.get(0).toString());
            frequency.setAnimated(false);
            frequency.setTitle("FREQUENCY LAST 7 DAYS YOU USE THE APP");
            frequency.setCategoryGap(1);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("6D", (newDay) ? list2.get(5) : list2.get(6)));
            series.getData().add(new XYChart.Data<>("5D", (newDay) ? list2.get(4) : list2.get(5)));
            series.getData().add(new XYChart.Data<>("4D", (newDay) ? list2.get(3) : list2.get(4)));
            series.getData().add(new XYChart.Data<>("3D", (newDay) ? list2.get(2) : list2.get(3)));
            series.getData().add(new XYChart.Data<>("2D", (newDay) ? list2.get(1) : list2.get(2)));
            series.getData().add(new XYChart.Data<>("1D", (newDay) ? list2.get(0) : list2.get(1)));
            series.getData().add(new XYChart.Data<>("TODAY", (newDay) ? 0 : list2.get(0)));

            frequency.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void entered(MouseEvent event) {
        TextField entered = (TextField) event.getSource();
        Font font = Font.font("Segoe UI Black",20);
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
    void attempted(MouseEvent event) throws Exception {
        TextField attempted = (TextField) event.getSource();
        String res= "";
        if (attempted == word1) {
            res = word1.getText();
        } else if (attempted == word2) {
            res = word2.getText();
        } else if (attempted == word3) {
            res = word3.getText();
        } else if (attempted == word4) {
            res = word4.getText();
        } else if (attempted == word5) {
            res = word5.getText();
        } else if (attempted == word6) {
            res = word6.getText();
        } else if (attempted == word7) {
            res = word7.getText();
        } else if (attempted == word8) {
            res = word8.getText();
        } else if (attempted == word9) {
            res = word9.getText();
        } else if (attempted == word10) {
            res = word10.getText();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML File/search.fxml"));
        Parent fxmlLoader = loader.load();
        ((Search_Controller) loader.getController()).getSearchBar().setText(res);
        ((Search_Controller) loader.getController()).search(event);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader, 875, 650);
        stage.setTitle("DICTIONARY");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void exited(MouseEvent event) {
        TextField exited = (TextField) event.getSource();
        Font font = Font.font("Segoe UI Emoji",20);
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
    void removed(MouseEvent event) throws Exception {
        ImageView removed = (ImageView) event.getSource();

        String path = "src/main/resources/com/example/btl1_dictionary/History.txt";

        List<String> lines = new ArrayList<>();
        String line2 = "";
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line2 = br.readLine()) != null) {
            if (!line2.isEmpty()) {
                if ((removed == remove1 && line2.equals(word1.getText())) ||
                        (removed == remove2 && line2.equals(word2.getText())) ||
                        (removed == remove3 && line2.equals(word3.getText())) ||
                        (removed == remove4 && line2.equals(word4.getText())) ||
                        (removed == remove5 && line2.equals(word5.getText())) ||
                        (removed == remove6 && line2.equals(word6.getText())) ||
                        (removed == remove7 && line2.equals(word7.getText())) ||
                        (removed == remove8 && line2.equals(word8.getText())) ||
                        (removed == remove9 && line2.equals(word9.getText())) ||
                        (removed == remove10 && line2.equals(word10.getText()))) {
                    continue;
                }
                lines.add(line2.trim());
            }
        }
        br.close();


        FileWriter fw = new FileWriter(path);
        for (String lineToWrite : lines) {
            fw.write(lineToWrite);
            fw.write("\n");
        }
        fw.close();

        initialize();
    }

    @Override
    public void Entered(MouseEvent event) {
        ImageView enteredImageView = (ImageView) event.getSource();

        if (enteredImageView == getSearch_Button()) {
            setSearch_Button(Search_Image);
            setHistory_Button(null);
        } else if (enteredImageView == getSaved_Button()) {
            setSaved_Button(Saved_Image);
            setHistory_Button(null);
        } else if (enteredImageView == getEdit_Button()) {
            setEdit_Button(Edit_Image);
            setHistory_Button(null);
        } else if (enteredImageView == getGoogle_Button()) {
            setGoogle_Button(Google_Image);
            setHistory_Button(null);
        } else if (enteredImageView == getGame_Button()) {
            setGame_Button(Game_Image);
            setHistory_Button(null);
        }
    }

    @Override
    public void Exited(MouseEvent event) {
        ImageView exitedImageView = (ImageView) event.getSource();

        if (exitedImageView == getSearch_Button()) {
            setSearch_Button(null);
            setHistory_Button(History_Image);
        } else if (exitedImageView == getSaved_Button()) {
            setSaved_Button(null);
            setHistory_Button(History_Image);
        } else if (exitedImageView == getEdit_Button()) {
            setEdit_Button(null);
            setHistory_Button(History_Image);
        } else if (exitedImageView == getGoogle_Button()) {
            setGoogle_Button(null);
            setHistory_Button(History_Image);
        } else if (exitedImageView == getGame_Button()) {
            setGame_Button(null);
            setHistory_Button(History_Image);
        }
    }
}
