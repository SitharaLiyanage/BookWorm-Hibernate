package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.tm.UserTM;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class MemberFormController {

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPassword;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPassword2;

    @FXML
    private TextField txtSearch;
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void initialize() throws Exception {
        generateNextUserId();
        loadAllUseres();
        setCellValueFactory();
        setDateAndTime();
        tableListener();
    }
    private void setDateAndTime(){
        Platform.runLater(() -> {
            lblDate.setText(String.valueOf(LocalDate.now()));

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                String timeNow = LocalTime.now().format(formatter);
                lblTime.setText(timeNow);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    public void clearfield(){
        lblUserId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtPassword2.setText("");
        txtSearch.setText("");
    }

    private void generateNextUserId() throws SQLException, IOException, ClassNotFoundException {
        String nextId = userBO.generateNewUserID();
        lblUserId.setText(nextId);
    }

    private void loadAllUseres() throws Exception {
        ObservableList<UserTM> obList = FXCollections.observableArrayList();
        try{
            List<UserDTO> dtoList = userBO.getAllUser();

            for (UserDTO dto : dtoList) {
                obList.add(
                        new UserTM(
                                dto.getId(),
                                dto.getUsername(),
                                dto.getEmail(),
                                dto.getPassword(),
                                dto.getRepeatpassword()
                        )
                );
            }
            tblUser.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableListener() {
        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(UserTM row) {
        lblUserId.setText(row.getId());
        txtName.setText(row.getUsername());
        txtEmail.setText(row.getEmail());
        txtPassword.setText(row.getPassword());
        txtPassword2.setText(row.getRepeatpassword());
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
    }
    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        String id = lblUserId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String repeatPassword = txtPassword2.getText();
        try {
            if (!validateUserDetails()) {
                return;
            }
            if(!password.equals(repeatPassword)) {
                showErrorNotification("Password do not match !", "The password should be equal !");
                return;
            }
            System.out.println(id);
            userBO.addUser(new UserDTO(id, name, email, password, repeatPassword));
            new Alert(Alert.AlertType.CONFIRMATION,"User Added Successfully !!!", ButtonType.OK).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "User Added Not Successful !!!", ButtonType.OK).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteUserOnAction(ActionEvent event) {
        String id = lblUserId.getText();
        try {
            System.out.println(id);
            userBO.deleteUser(id);
            new Alert(Alert.AlertType.CONFIRMATION,"User Deleted !!!", ButtonType.OK).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "User Not Deleted !!!", ButtonType.OK).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws Exception {
        String id = txtSearch.getText();

        try {
            UserDTO userDTO;
            userDTO = userBO.search(id);
            if (userDTO != null) {
                lblUserId.setText(String.valueOf(userDTO.getId()));
                txtName.setText(userDTO.getUsername());
                txtEmail.setText(userDTO.getEmail());
                txtPassword.setText(String.valueOf(userDTO.getPassword()));
                txtPassword2.setText(String.valueOf(userDTO.getRepeatpassword()));
            } else {
                new Alert(Alert.AlertType.ERROR,"Student Doesn't exist").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateUserOnAction(ActionEvent event) {
        String id = lblUserId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String repeatPassword = txtPassword2.getText();
        try {
            if (!validateUserDetails()) {
                return;
            }
            if(!password.equals(repeatPassword)) {
                showErrorNotification("Password do not match !", "The password should be equal !");
                return;
            }
            System.out.println(id);
            userBO.updateUser(new UserDTO(id, name, email, password, repeatPassword));
            new Alert(Alert.AlertType.CONFIRMATION,"User Updated !!!", ButtonType.OK).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "User Not Updated !!!", ButtonType.OK).show();
        }
    }
    public boolean validateUserDetails() {
        boolean isValid = true;

        if (!Pattern.matches("[A-Za-z]{4,}", txtName.getText())) {
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
