package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import com.sun.javafx.collections.ImmutableObservableList;
import command.AllCommands;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> labList;

    @FXML
    private FlowPane labView;

    @FXML
    void initialize() {
        AllCommands allCommands = new AllCommands();
        ObservableList<String> list = FXCollections.observableArrayList(allCommands.getCommands());
        labList.setItems(list);
        labList.setStyle("-fx-background-color: #010101;");
        labView.setStyle("-fx-background-color: #1F2023");
        labView.setPrefSize(2780d, 1920d);
        labView.setMaxSize(2780d, 1920d);
        labList.setOnMouseClicked(event -> {
            labView.getChildren().removeAll(labView.getChildren());
            labView.getChildren().addAll(allCommands.getCommandByName(labList.getSelectionModel().getSelectedItem()).getNodes());
        });
    }
}
