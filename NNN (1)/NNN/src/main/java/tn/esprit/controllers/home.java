package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.controllers.BackFatma.AddGroupe;
import tn.esprit.controllers.FrontFatma.ShowAllEvents;
import tn.esprit.controllers.FrontFatma.ShowAllGroupes;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.services.SessionManager;
import javafx.fxml.FXML;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class home implements Initializable {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int userId = (int) SessionManager.getSession("userId");
    }


    @FXML
    void goToEvents(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllEvents.fxml"));
            Parent root = loader.load();
            ShowAllEvents controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    @FXML
    public void goToQuiz(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/QuizList.fxml"));
            Parent root = loader.load();
            QuizList controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void goToGroups(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontFatma/ShowAllGroupes.fxml"));
            Parent root = loader.load();
            ShowAllGroupes controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void goToMarket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherShop.fxml"));
            Parent root = loader.load();
            AfficherShop controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    @FXML
    void goToLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLivUser.fxml"));
            Parent root = loader.load();
            AfficherLivUser controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void goToQuiz(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Quiz/User/QuizList.fxml"));
            Parent root = loader.load();
            QuizList controller = loader.getController();
            // Obtenez la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();

            // Remplacez la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

}

