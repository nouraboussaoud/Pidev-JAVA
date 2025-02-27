package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import tn.esprit.controllers.FrontFatma.ShowAllEvents;
import tn.esprit.controllers.FrontFatma.ShowAllGroupes;
import tn.esprit.controllers.Quiz.Front.QuizList;
import tn.esprit.controllers.UserControllers.ProfileController;
import tn.esprit.entities.User;
import tn.esprit.models.Commande2;
import tn.esprit.models.Produit2;
import tn.esprit.services.*;


import java.io.IOException;
import java.net.URL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;


public class AfficherShop implements Initializable {
    private PanierService panierService = new PanierService();
    private Produit2Service produit2Service = new Produit2Service();
    private Commande2Service commande2Service = new Commande2Service();
    @FXML
    private AnchorPane main_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private ScrollPane menu_scrollPane;
    private Stage stage;

    @FXML
    private Text nomq;
    @FXML
    private Button profilename;


    final UserService pf = new UserService();
    private void fetchUserData() {
        // Retrieve user data from UserService
        User user = pf.fetchPlayerData();
        if (user != null) {
            // Populate the UI with user data

            profilename.setText(user.getNom());

        } else {
            // Handle case where user data is not found

            profilename.setText("N/A");

        }
    }


    @FXML
    void GotoPanier(ActionEvent event) {
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
            Parent root = loader.load();
            // Obtenir le contrôleur de la deuxième page
            panier controller = loader.getController();
            // Obtenir la scène actuelle à partir de l'événement
            Scene currentScene = ((Node) event.getSource()).getScene();
            // Remplacer la racine de la scène actuelle avec la nouvelle page
            currentScene.setRoot(root);
        } catch (IOException e) {
            // Gérez les exceptions d'E/S
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private List<Produit2> produit2;
    private Produit2Service se = new Produit2Service();
    List<Produit2> serviceList = new ArrayList<>(se.getAll());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int userId = (int) SessionManager.getSession("userId");

        fetchUserData();

        List<Produit2> panierProducts = panierService.getProductsInPanier(userId);
        List<Commande2> orderedProducts = commande2Service.getOrderedProductsForUser(userId);
        List<String> orderedProductNames = new ArrayList<>();
        for (Commande2 commande2 : orderedProducts) {
            String[] produits = commande2.getProduits().split(", ");
            for (String produit : produits) {
                orderedProductNames.add(produit);
            }
        }
        Produit2Service produit2Service = new Produit2Service();
        List<Produit2> allProducts = produit2Service.getAll();
        List<Produit2> displayProducts = new ArrayList<>();
        for (Produit2 product : allProducts) {
            if (!orderedProductNames.contains(product.getNomprodui())) {
                displayProducts.add(product);
            }
        }
        displayFilteredProducts(displayProducts);
    }

    private void displayFilteredProducts(List<Produit2> products) {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cardProduct.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CardProduct itemController = fxmlLoader.getController();
                itemController.setPanierService(panierService);
                itemController.setData(products.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                menu_gridPane.add(anchorPane, column++, row);
                menu_gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                menu_gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                menu_gridPane.setMaxWidth(Region.USE_PREF_SIZE);
                menu_gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                menu_gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                menu_gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void gotoprofile(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateUserGUI.fxml"));
            Parent root = loader.load();
            ProfileController controller = loader.getController();
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
