package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import math.FormulaInterpreter;

import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = Paths.get("src/main/java/sample/Main.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Вычислительная математика");
        Scene scene = new Scene(root, 1920, 1080);
        URL css = Paths.get("src/main/style/css/stylesheet.css").toUri().toURL();
        scene.getStylesheets().add(css.toString());
        URL ico = Paths.get("src/main/style/img/icon.png").toUri().toURL();
        primaryStage.getIcons().add(new Image(ico.toString()));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
