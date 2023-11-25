package com.example.pipegame.control;

import com.example.pipegame.ApplicationMain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ApplicationController {

    @FXML
    private Label vText;

    @FXML
    protected void PlayButton() {
        int mode = askForGraphType();
        if (mode != 0) {
            GameController.selectedGraphMode = mode;
            ApplicationMain.hideWindow((Stage) vText.getScene().getWindow());
            ApplicationMain.showWindow("game-view", null);
        }
    }

    private int askForGraphType() {
        ArrayList<String> options = new ArrayList<>(List.of("Adjacency List", "Adjacency Matrix"));
        String selectedOption = ApplicationMain.showChoiceDialog("Confirmation", "Set graph type", "Select an option:", options);
        if ("Adjacency List".equals(selectedOption)) {
            return 1;
        } else if ("Adjacency Matrix".equals(selectedOption)) {
            return 2;
        }
        return 0;
    }
}