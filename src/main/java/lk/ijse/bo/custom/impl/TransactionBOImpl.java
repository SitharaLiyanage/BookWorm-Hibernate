package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.TransactionBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.TransactionDAO;
import lk.ijse.dto.TransactionDTO;
import lk.ijse.dto.TransactionDTO;
import lk.ijse.entity.Transactions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionBOImpl implements TransactionBO {
    TransactionDAO transactionDAO = (TransactionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TRANSACTION);

    @Override
    public String generateNewTransactionID() throws IOException {
        return transactionDAO.generateNewID();
    }

    @Override
    public List<TransactionDTO> getAllTransaction() throws Exception {
        List<TransactionDTO> transactionList= new ArrayList<>();
        List<Transactions> transactions = transactionDAO.getAll();
        for (Transactions transaction : transactions) {
            transactionList.add(new TransactionDTO(transaction.getTranID(), transaction.getMemID(), transaction.getMemName(), transaction.getBookID(), transaction.getBookName(), transaction.getTranDate(), transaction.getTranEndDate()));
        }
        return transactionList;
    }

    @Override
    public boolean addTransaction(TransactionDTO dto) throws Exception {
        return transactionDAO.add(new Transactions(dto.getTranID(), dto.getMemID(), dto.getMemName(), dto.getBookID(), dto.getBookName(), dto.getTranDate(), dto.getTranEndDate()));
    }

    public boolean borrowedBook(String id){
        return transactionDAO.borrowedBooks(id);
    }
    @Override
    public boolean deleteTransaction(String id) throws Exception {
        return transactionDAO.delete(id);
    }
    public boolean returnedBook(String id){
        return transactionDAO.returnedBook(id);
    }

}
