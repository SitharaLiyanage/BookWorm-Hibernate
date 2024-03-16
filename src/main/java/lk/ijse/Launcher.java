package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("BookWorm");
        stage.show();
        stage.setResizable(false);
    }
}