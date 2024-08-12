package tn.esprit.controllers.BackFatma;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tn.esprit.entities.Event;
import tn.esprit.services.EventService;
import tn.esprit.services.GroupService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetAllEvents {

    @FXML
    private TableColumn<Event, LocalDate> debutDateColumn;

    @FXML
    private TableColumn<Event, String>  descColumn;

    @FXML
    private TableColumn<Event, LocalDate> endDateColumn;

    @FXML
    private TableView<Event> eventTV;

    @FXML
    private TableColumn<Event, String> groupColumn;

    @FXML
    private TableColumn<Event, Integer> idColumn;

    @FXML
    private TableColumn<Event, String> nameColumn;

    EventService es=new EventService();
    GroupService gs=new GroupService();

    @FXML
    void initialize() {
        List<Event> events = es.getAll();
        List<String>groupes=new ArrayList<>();
        ObservableList<Event> observableList= FXCollections.observableList(events);
        eventTV.setItems(observableList);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc_event"));
        debutDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_d"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("date_f"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("groupe_id"));
        eventTV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //bouton dans table view
        // Créez une colonne de table pour les boutons


    }

    @FXML
    void showEventFront(ActionEvent event) {

    }
    @FXML
    void viewStatsClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLFATMA/StatsEvents.fxml"));
            AnchorPane root = fxmlLoader.load();
            // You may need to pass any necessary data to the StatsEvent controller here

            // Create a new scene
            javafx.scene.Scene scene = new javafx.scene.Scene(root);
            // Get the stage from the event source
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            // Set the new scene
            stage.setScene(scene);
            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
