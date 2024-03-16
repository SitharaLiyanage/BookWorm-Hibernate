package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.TransactionDTO;

import java.io.IOException;
import java.util.List;

public interface TransactionBO extends SuperBO {
    public String generateNewTransactionID() throws IOException;
    public List<TransactionDTO> getAllTransaction() throws Exception;
    public boolean addTransaction(TransactionDTO dto) throws Exception;
    public boolean borrowedBook(String id);
    public boolean deleteTransaction(String id) throws Exception;
    public boolean returnedBook(String id);
}
