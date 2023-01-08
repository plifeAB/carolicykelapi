package com.caroli.cykel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardOpenOption;

public class MainController {
    @FXML
    private TextFlow logBox;
    @FXML
    private ScrollPane scrollLogBox;
    @FXML
    private MenuItem closeButtonMenu;
    @FXML
    MenuBar myMenuBar;

    @FXML
    Label modeStatus;
    @FXML
    Label wareHouseStatus;
    @FXML
    Label lastRequestStatus;

    @FXML
    public void initialize() throws FileNotFoundException {
        ReadSettings settings = new ReadSettings();
        settings.ReadSettings();
        wareHouseStatus.setText(settings.getWareHouseName());
        String mode = settings.getMode();
        modeStatus.setText(mode);

    }

    @FXML
    protected void onPushButtonClick()
    {

        // Auto Scroll to Down
        scrollLogBox.vvalueProperty().bind(logBox.heightProperty());

        // create text
        Text text_1 = new Text("GeeksforGeeks\n");

        // set the text color
        text_1.setFill(Color.RED);

        // set font of the text
        text_1.setFont(Font.font("Verdana", 25));

        // create text
        Text text_2 = new Text("The computer science portal for geeks\n");

        // set the text color
        text_2.setFill(Color.BLUE);

        // set font of the text
        text_2.setFont(Font.font("Helvetica", FontPosture.ITALIC, 15));

        // add text to textflow
        logBox.getChildren().add(text_1);
        logBox.getChildren().add(text_2);

    }
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) myMenuBar.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    @FXML
    private void aboutButtonAction(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("About");
            Image image = new Image(CaroliKassaApp.class.getResourceAsStream("icons/ic_launcher-web.png"));
            stage.getIcons().add(image);
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void settingsButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings-view.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            Image image = new Image(CaroliKassaApp.class.getResourceAsStream("icons/ic_launcher-web.png"));
            stage.getIcons().add(image);
            stage.setScene(new Scene(root1));
            stage.setResizable(false);
            stage.setMaximized(false);
            /*
            init function calling dynamically but this method able to use for calling another function

            SettingsController controller = fxmlLoader.getController();
            stage.setOnShowing( event -> {controller.initialize();} );
            */

            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void saveLogButtonAction(ActionEvent actionEvent) {
        // Creating an instance of file
        Path path = Paths.get("log.txt");
        String str = getStringFromTextFlow(logBox);

        try {
            Files.writeString(path, str, StandardOpenOption.APPEND);
            logBox.getChildren().clear();
        }
        catch (IOException ex) {
            System.out.print("Invalid Path");
            Text text_1 = new Text("Invalid Path\n");
            text_1.setFill(Color.RED);
            text_1.setFont(Font.font("Verdana", 15));
            logBox.getChildren().add(text_1);
        }
    }
    public static String getStringFromTextFlow(TextFlow tf) {
        StringBuilder sb = new StringBuilder();
        tf.getChildren().stream()
                .filter(t -> Text.class.equals(t.getClass()))
                .forEach(t -> sb.append(((Text) t).getText()));
        return sb.toString();
    }
}