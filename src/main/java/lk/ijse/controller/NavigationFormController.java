package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class NavigationFormController {
    @FXML
    private AnchorPane rootHome;

    @FXML
    private AnchorPane rootNode;

    public void initialize() throws IOException {
        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/overview_form.fxml"));
        rootHome.getChildren().clear();
        rootHome.getChildren().add(rootNode);
//        Image correct = new Image("/image/icons8-correct-64.png");
        Notifications.create()
                .title("Login Successfull")
                .text("Welcome to Jayabima Hardware Management System")
//                .graphic(new ImageView(correct))
                .hideAfter(Duration.seconds(2))
                .show();
        /*staticLabel = lbluser;*/
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/overview_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("BookWorm");
    }

    @FXML
    void btnManageBooksOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/book_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageBranchesOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/branches_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageMembersOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/member_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageTransactionsOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/transaction_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }

    @FXML
    void btnManageUsersOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/user_form.fxml"));
        this.rootHome.getChildren().clear();
        this.rootHome.getChildren().add(rootNode);
    }
}
