package application;

import java.time.LocalDate;
import java.time.Period;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FXAgeCalculator extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            scene.setFill(Color.LIGHTBLUE);

            Label label = new Label("Pick Jane's birth date");
            DatePicker datePicker = new DatePicker();
            Label ageLabel = new Label();
            ageLabel.setVisible(false);
            
            datePicker.setOnAction(e -> {
                LocalDate selectedDate = datePicker.getValue();
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(selectedDate, currentDate).getYears();
                ageLabel.setText("Jane is " + age + " years old");
                ageLabel.setVisible(true);
            });
            
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(label, datePicker, ageLabel);
            vbox.setPadding(new Insets(20));
            vbox.setAlignment(Pos.CENTER);
            vbox.setPrefSize(300, 200);
            vbox.setStyle("-fx-background-color: #F0FFFF;");
            root.setCenter(vbox);
            label.setStyle("-fx-font-size: 20px; -fx-font-family: 'Arial';");
            datePicker.setStyle("-fx-font-size: 15px; -fx-font-family: 'Arial';");

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}