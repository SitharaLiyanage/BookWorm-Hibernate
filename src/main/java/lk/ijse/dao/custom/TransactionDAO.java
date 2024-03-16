package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Transactions;

public interface TransactionDAO extends CrudDAO<Transactions> {
    public boolean borrowedBooks(String id);
    public boolean returnedBook(String id);
    public String getTotalTransactions();
}
