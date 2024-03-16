package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dto.UserDTO;
import org.controlsfx.control.Notifications;


import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterFormController {

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private PasswordField txtPasswordRepeat;

    @FXML
    private TextField txtUserName;

    @FXML
    private AnchorPane rootNode;

    String id;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);

    private void clearFields() {
        txtUserName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtPasswordRepeat.setText("");
    }

    @FXML
    void btnSignInFormOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("BookWorm");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws Exception {
        String id = loginBO.generateNewUserID();
        String name = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String repeatPassword = txtPasswordRepeat.getText();
        try {
            /*if (!validateUserDetails()) {
                return;
            }*/
            if(!password.equals(repeatPassword)) {
                showErrorNotification("Password do not match !", "The password should be equal !");
                return;
            }
            System.out.println(id);
            loginBO.saveUser(new UserDTO(id, name, email, password, repeatPassword));
            new Alert(Alert.AlertType.CONFIRMATION,"User Added Successfully !!!", ButtonType.OK).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "User Added Not Successful !!!", ButtonType.OK).show();
        }
    }

    public boolean validateUserDetails() {
        boolean isValid = true;

        if (!Pattern.matches("[A-Za-z]{4,}", txtUserName.getText())) {
            showErrorNotification("Invalid Username", "The username you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", txtEmail.getText())) {
            showErrorNotification("Invalid Email Address", "The email address you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", txtPassword.getText())) {
            showErrorNotification("Invalid Password", "The password you entered is invalid");
            isValid = false;
        }
        return isValid;
    }

    private static void showErrorNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showError();
    }
}
