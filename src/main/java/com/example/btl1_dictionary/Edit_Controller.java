package com.example.btl1_dictionary;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Edit_Controller extends General_Controller {


    @Override
    public void Entered(MouseEvent event) {
        ImageView enteredImageView = (ImageView) event.getSource();

        if (enteredImageView == getSearch_Button()) {
            setSearch_Button(Search_Image);
            setEdit_Button(null);
        } else if (enteredImageView == getHistory_Button()) {
            setHistory_Button(History_Image);
            setEdit_Button(null);
        } else if (enteredImageView == getSaved_Button()) {
            setSaved_Button(Saved_Image);
            setEdit_Button(null);
        } else if (enteredImageView == getGoogle_Button()) {
            setGoogle_Button(Google_Image);
            setEdit_Button(null);
        } else if (enteredImageView == getGame_Button()) {
            setGame_Button(Game_Image);
            setEdit_Button(null);
        }
    }

    @Override
    public void Exited(MouseEvent event) {
        ImageView exitedImageView = (ImageView) event.getSource();

        if (exitedImageView == getSearch_Button()) {
            setSearch_Button(null);
            setEdit_Button(Edit_Image);
        } else if (exitedImageView == getHistory_Button()) {
            setHistory_Button(null);
            setEdit_Button(Edit_Image);
        } else if (exitedImageView == getSaved_Button()) {
            setSaved_Button(null);
            setEdit_Button(Edit_Image);
        } else if (exitedImageView == getGoogle_Button()) {
            setGoogle_Button(null);
            setEdit_Button(Edit_Image);
        } else if (exitedImageView == getGame_Button()) {
            setGame_Button(null);
            setEdit_Button(Edit_Image);
        }
    }
}
