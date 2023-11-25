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

    /**
     * The PlayButton function checks for the selected graph type and opens the game view window if a
     * valid mode is selected.
     */
    @FXML
    protected void PlayButton() {
        int mode = askForGraphType();
        if (mode != 0) {
            GameController.selectedGraphMode = mode;
            ApplicationMain.hideWindow((Stage) vText.getScene().getWindow());
            ApplicationMain.showWindow("game-view", null);
        }
    }

    /**
     * The function asks the user to select a graph type (either "Adjacency List" or "Adjacency
     * Matrix") and returns an integer value representing the selected option.
     * 
     * @return The method is returning an integer value. The value returned depends on the selected
     * option in the choice dialog. If "Adjacency List" is selected, it returns 1. If "Adjacency
     * Matrix" is selected, it returns 2. If neither option is selected, it returns 0.
     */
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