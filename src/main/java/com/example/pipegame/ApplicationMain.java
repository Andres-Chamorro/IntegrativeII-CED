package com.example.pipegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ApplicationMain extends Application {

    /**
     * The start function is used to initialize and display the "hello-view" window in a Java
     * application.
     * 
     * @param stage The stage parameter is an instance of the Stage class, which represents the main
     * window of a JavaFX application. It is the top-level container for all JavaFX content.
     */
    @Override
    public void start(Stage stage) {
        showWindow("hello-view", stage);
    }

    /**
     * The function "showWindow" loads an FXML file, creates a new stage, sets the scene with the
     * loaded FXML file, and displays the stage.
     * 
     * @param fxml The fxml parameter is a String that represents the name of the FXML file that
     * contains the layout of the window you want to show. The ".fxml" extension is added to the end of
     * the String to specify the file type.
     * @param stage The stage parameter is the JavaFX Stage object that represents the window in which
     * the FXML file will be displayed.
     */
    public static void showWindow(String fxml, Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource(fxml + ".fxml"));
        Scene scene;
        try {
            Parent root = fxmlLoader.load();
            scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Pipe game");
            stage.sizeToScene();
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The showAlert function creates and displays an alert dialog with the specified alert type,
     * title, header text, and content text, and returns the button type that was clicked by the user.
     * 
     * @param alertType The type of alert to be displayed. It can be one of the following values:
     * Alert.AlertType.NONE, Alert.AlertType.INFORMATION, Alert.AlertType.WARNING,
     * Alert.AlertType.ERROR, or Alert.AlertType.CONFIRMATION.
     * @param title The title of the alert dialog box. It is displayed at the top of the dialog box.
     * @param headerText The headerText parameter is a string that represents the text to be displayed
     * as the header of the alert dialog. It typically provides a brief summary or description of the
     * alert.
     * @param contentText The contentText parameter is a string that represents the main message or
     * content of the alert dialog. It is typically displayed below the header text.
     * @return The method is returning an Optional<ButtonType> object.
     */
    public static Optional<ButtonType> showAlert(Alert.AlertType alertType, String title, String headerText,
            String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }
/**
 * The function "showChoiceDialog" displays a dialog box with a list of choices and returns the
 * selected choice, or prompts the user to choose an option if none is selected.
 * 
 * @param title The title of the choice dialog window. It is displayed at the top of the dialog window.
 * @param headerText The header text is the main text that appears at the top of the dialog box. It
 * usually provides a brief description or instruction to the user.
 * @param contentText The contentText parameter is a string that represents the message or prompt
 * displayed in the dialog box. It provides additional information or instructions to the user.
 * @param choices An ArrayList of strings representing the available choices for the user to select
 * from in the dialog.
 * @return The method returns a String value.
 */

    public static String showChoiceDialog(String title, String headerText, String contentText,
            ArrayList<String> choices) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        while (true) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                return result.get(); // Valid selection, return the result
            } else {
                Optional<ButtonType> alertResult = showAlert(Alert.AlertType.WARNING, "Warning", "No option selected",
                        "You must choose an option before proceeding.");
                if (alertResult.isPresent() && alertResult.get() == ButtonType.OK) {
                    return "Return to Menu";
                }
            }
        }
    }

    /**
     * The getFile function returns a File object corresponding to the specified file name.
     * 
     * @param fileName The `fileName` parameter is a string that represents the name of the file that
     * you want to retrieve.
     * @return The method is returning a File object.
     */
    public static File getFile(String fileName) {
        return new File(Objects.requireNonNull(ApplicationMain.class.getResource(fileName)).getPath());
    }

    /**
     * The function hides a JavaFX stage window.
     * 
     * @param stage The stage parameter is an instance of the Stage class in JavaFX. The Stage class
     * represents the main window or container for a JavaFX application.
     */
    public static void hideWindow(Stage stage) {
        stage.hide();
    }

    /**
     * The main function launches the Java program.
     */
    public static void main(String[] args) {
        launch();
    }
}