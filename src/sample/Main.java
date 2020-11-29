package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    Parent root;
    Stage stage;

    @Override
    public void start(Stage primaryStage) {
        try {
            root = FXMLLoader.load(getClass().getResource("main.fxml"));

            stage = primaryStage;
            stage.setTitle("TÍNH TIỀN!");

            Scene scene = new Scene(root);
            stage.getIcons().add(new Image("https://images.vexels.com/media/users/3/143188/isolated/preview/5f44f3160a09b51b4fa4634ecdff62dd-money-icon-by-vexels.png"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
