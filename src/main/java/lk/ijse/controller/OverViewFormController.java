package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BookDAO;
import lk.ijse.dao.custom.BranchDAO;
import lk.ijse.dao.custom.TransactionDAO;
import lk.ijse.dao.custom.UserDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OverViewFormController {
    @FXML
    private Label lblBooks;

    @FXML
    private Label lblBorrowings;

    @FXML
    private Label lblBranches;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblMembers;

    @FXML
    private Label lblTime;
    BookDAO bookDAO = (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);
    BranchDAO branchDAO = (BranchDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BRANCH);
    TransactionDAO transactionDAO = (TransactionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSACTION);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    public void initialize() throws Exception {
        setDateAndTime();
        totalBooks();
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
    private void totalBooks() {
        String books = bookDAO.getTotalBooks();
        lblBooks.setText(books);
    }
    private void totalBranches() {
        String branches = branchDAO.getTotalBranches();
        lblBranches.setText(branches);
    }

    private void totalMembers() {
        String members = userDAO.getTotalUsers();
        lblMembers.setText(members);
    }

    private void totalTransactions() {
        String transactions = transactionDAO.getTotalTransactions();
        lblBorrowings.setText(transactions);
    }
}
