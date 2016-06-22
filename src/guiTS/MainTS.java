package guiTS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainTS extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainWindowTS.fxml"));
        primaryStage.setTitle("LS");
        primaryStage.setScene(new Scene(root, 740, 529));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
