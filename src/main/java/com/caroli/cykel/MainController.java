package com.caroli.cykel;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


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
}