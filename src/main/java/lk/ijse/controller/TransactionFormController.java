package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.BookBO;
import lk.ijse.bo.custom.TransactionBO;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.BookDTO;
import lk.ijse.dto.TransactionDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.dto.tm.TransactionTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static javafx.util.Duration.*;

public class TransactionFormController {
    @FXML
    private DatePicker borrowDate;

    @FXML
    private JFXComboBox<String> cmbBookCode;

    @FXML
    private JFXComboBox<String> cmbMemberId;

    @FXML
    private TableColumn<?, ?> colBookID;

    @FXML
    private TableColumn<?, ?> colBookName;

    @FXML
    private TableColumn<?, ?> colBorrowedDate;

    @FXML
    private TableColumn<?, ?> colMemID;

    @FXML
    private TableColumn<?, ?> colMemName;

    @FXML
    private TableColumn<?, ?> colReturnedDate;

    @FXML
    private TableColumn<?, ?> colTranID;

    @FXML
    private Label lblBookName;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblMemberName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTranscationId;

    @FXML
    private DatePicker returnDate;

    @FXML
    private TableView<TransactionTM> tblOrderCart;

    BookBO bookBO = (BookBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOK);
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    TransactionBO transactionBO = (TransactionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.TRANSACTION);

    public void initialize() throws Exception {
        setDateAndTime();
        generateNextUserId();
        loadAllTransactions();
        setCellValueFactory();
        tableListener();
        loadMemberIds();
        loadBookIds();
    }

    private void setDateAndTime(){
        Platform.runLater(() -> {
            lblDate.setText(String.valueOf(LocalDate.now()));

            Timeline timeline = new Timeline(new KeyFrame(millis(1), event -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                String timeNow = LocalTime.now().format(formatter);
                lblTime.setText(timeNow);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    public void clearfield(){
        lblTranscationId.setText("");
        cmbMemberId.setValue("");
        lblMemberName.setText("");
        cmbBookCode.setValue("");
        lblBookName.setText("");
    }

    private void generateNextUserId() throws SQLException, IOException, ClassNotFoundException {
        String nextId = transactionBO.generateNewTransactionID();
        lblTranscationId.setText(nextId);
    }

    private void loadAllTransactions() throws Exception {
        ObservableList<TransactionTM> obList = FXCollections.observableArrayList();
        try{
            List<TransactionDTO> dtoList = transactionBO.getAllTransaction();

            for (TransactionDTO dto : dtoList) {
                obList.add(
                        new TransactionTM(
                                dto.getTranID(),
                                dto.getMemID(),
                                dto.getMemName(),
                                dto.getBookID(),
                                dto.getBookName(),
                                dto.getTranDate(),
                                dto.getTranEndDate()
                        )
                );
            }
            tblOrderCart.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tableListener() {
        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(TransactionTM row) {
        lblTranscationId.setText(row.getTranID());
        cmbMemberId.setValue(row.getMemID());
        lblMemberName.setText(String.valueOf(row.getMemName()));
        cmbBookCode.setValue(String.valueOf(row.getBookID()));
        lblBookName.setText(row.getBookName());
        borrowDate.setValue(LocalDate.parse(row.getTranDate()));
        returnDate.setValue(LocalDate.parse(row.getTranEndDate()));
    }

    private void setCellValueFactory() {
        colTranID.setCellValueFactory(new PropertyValueFactory<>("tranID"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("memID"));
        colBookName.setCellValueFactory(new PropertyValueFactory<>("memName"));
        colMemID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colMemName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("tranDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("tranEndDate"));
    }

    private void loadBookIds() throws Exception {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<BookDTO> idList = bookBO.getAllBookes();

            for (BookDTO dto : idList) {
                obList.add(dto.getId());
            }

            cmbBookCode.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMemberIds() throws Exception {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<UserDTO> idList = userBO.getAllUser();

            for (UserDTO dto : idList) {
                obList.add(dto.getId());
            }

            cmbMemberId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String id = lblTranscationId.getText();
        String memID = cmbMemberId.getValue();
        String memName = lblMemberName.getText();
        String bookID = cmbBookCode.getValue();
        String bookName = lblBookName.getText();
        String borrowDateValue = String.valueOf(borrowDate.getValue());
        String returnDateValue = String.valueOf(returnDate.getValue());

        try {
            transactionBO.addTransaction(new TransactionDTO(id, memID, memName, bookID,bookName,borrowDateValue,returnDateValue));
            new Alert(Alert.AlertType.CONFIRMATION,"Book Borrowed !", ButtonType.OK).show();
            transactionBO.borrowedBook(bookID);
            new Alert(Alert.AlertType.CONFIRMATION,"QTY Updated !", ButtonType.OK).show();
            generateNextUserId();
            loadAllTransactions();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Book Not Added !", ButtonType.OK).show();
        }

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblTranscationId.getText();
        String bookID = cmbBookCode.getValue();

        try {
            transactionBO.deleteTransaction(id);
            new Alert(Alert.AlertType.CONFIRMATION,"Book Borrowed !", ButtonType.OK).show();
            transactionBO.returnedBook(bookID);
            new Alert(Alert.AlertType.CONFIRMATION,"QTY Updated !", ButtonType.OK).show();
            generateNextUserId();
            loadAllTransactions();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Book Not Added !", ButtonType.OK).show();
        }
    }
    @FXML
    void cmbBookOnAction(ActionEvent event) throws Exception {
        String id = cmbBookCode.getValue();
        try {
            BookDTO branchDTO = bookBO.search(id);
            lblBookName.setText(branchDTO.getName());
            lblQtyOnHand.setText(String.valueOf(branchDTO.getQty()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbMemberOnAction(ActionEvent event) throws Exception {
        String id = cmbMemberId.getValue();
        try {
            UserDTO userDTO = userBO.search(id);
            lblMemberName.setText(userDTO.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
