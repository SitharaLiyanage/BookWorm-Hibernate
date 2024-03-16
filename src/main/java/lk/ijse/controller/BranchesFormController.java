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
import lk.ijse.bo.custom.BranchBO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.tm.BranchTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BranchesFormController {

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private Label lblBranchId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<BranchTM> tblBranch;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtSearch;
    BranchBO branchBO = (BranchBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BRANCH);
    public void initialize() throws Exception {
        generateNextUserId();
        loadAllBranches();
        setCellValueFactory();
        tableListener();
        setDateAndTime();
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
        lblBranchId.setText("");
        txtLocation.setText("");
        txtMobile.setText("");
        txtEmail.setText("");
        txtSearch.setText("");
    }

    private void generateNextUserId() throws SQLException, IOException, ClassNotFoundException {
        String nextId = branchBO.generateNewUserID();
        lblBranchId.setText(nextId);
    }

    private void loadAllBranches() throws Exception {
        ObservableList<BranchTM> obList = FXCollections.observableArrayList();
        try{
            List<BranchDTO> dtoList = branchBO.getAllBranches();

            for (BranchDTO dto : dtoList) {
                obList.add(
                        new BranchTM(
                                dto.getId(),
                                dto.getLocation(),
                                dto.getEmail(),
                                dto.getMobile()
                        )
                );
            }
            tblBranch.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableListener() {
        tblBranch.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(BranchTM row) {
        lblBranchId.setText(row.getId());
        txtLocation.setText(row.getLocation());
        txtEmail.setText(String.valueOf(row.getEmail()));
        txtMobile.setText(String.valueOf(row.getMobile()));
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
    }

    @FXML
    void btnAddBranchOnAction(ActionEvent event) {
        String id = lblBranchId.getText();
        String location = txtLocation.getText();
        String email = txtEmail.getText();
        String mobile = txtMobile.getText();
        try {
            branchBO.addBranch(new BranchDTO(id, location, email, mobile));
            new Alert(Alert.AlertType.CONFIRMATION,"Branch Added !", ButtonType.OK).show();
            generateNextUserId();
            clearfield();
            loadAllBranches();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Branch Not Added !", ButtonType.OK).show();
        }

    }

    @FXML
    void btnClearBranchOnAction(ActionEvent event) {
        clearfield();
    }

    @FXML
    void btnDeleteBranchOnAction(ActionEvent event) {
        String id = lblBranchId.getText();
        try {
            branchBO.deleteBranch(id);
            new Alert(Alert.AlertType.CONFIRMATION,"Branch Added !", ButtonType.OK).show();
            generateNextUserId();
            clearfield();
            loadAllBranches();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Branch Not Added !", ButtonType.OK).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws Exception {
        String id = txtSearch.getText();

        try {
            BranchDTO branchDTO;
            branchDTO = branchBO.search(id);
            if (branchDTO != null) {
                lblBranchId.setText(String.valueOf(branchDTO.getId()));
                txtLocation.setText(branchDTO.getLocation());
                txtEmail.setText(branchDTO.getEmail());
                txtMobile.setText(String.valueOf(branchDTO.getMobile()));
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
    void btnUpdateBranchOnAction(ActionEvent event) {
        String id = lblBranchId.getText();
        String location = txtLocation.getText();
        String email = txtEmail.getText();
        String mobile = txtMobile.getText();
        try {
            branchBO.updateBranch(new BranchDTO(id, location, email, mobile ));
            new Alert(Alert.AlertType.CONFIRMATION,"Branch Updated !", ButtonType.OK).show();
            generateNextUserId();
            clearfield();
            loadAllBranches();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Branch Not Updated !", ButtonType.OK).show();
        }
    }

}
