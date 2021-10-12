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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> labList;

    @FXML
    private ScrollPane labScroll;

    @FXML
    void initialize() {
        labScroll.setStyle("-fx-background-color: #1F2023");
        labScroll.setPrefSize(2780d, 1920d);
        labScroll.setMaxSize(2780d, 1920d);
        FlowPane labView = new FlowPane(10, 10);
        labView.setStyle("-fx-background-color: #1F2023;");
        labScroll.setContent(labView);
        labScroll.setId("labscroll");
        AllCommands allCommands = new AllCommands(labScroll);
        ObservableList<String> list = FXCollections.observableArrayList(allCommands.getCommands());
        labList.setItems(list);
        labList.setStyle("-fx-background-color: #010101;");
        labList.setOnMouseClicked(event -> {
            labView.getChildren().removeAll(labView.getChildren());
            labView.getChildren().addAll(allCommands.getCommandByName(labList.getSelectionModel().getSelectedItem()).getNodes());
        });
    }
}
