package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.TransactionDAO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Transactions;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transactions = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT tranID FROM Transactions ORDER BY tranID DESC LIMIT 1");
        String id = nativeQuery.uniqueResult();
        transactions.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("T0");
            int newID = Integer.parseInt(strings[1]);
            newID++;
            String ID = String.valueOf(newID);
            int length = ID.length();
            if (length < 2) {
                return "T00" + newID;
            } else {
                if (length < 3) {
                    return "T0" + newID;
                } else {
                    return "T" + newID;
                }
            }
        } else {
            return "T001";
        }
    }

    @Override
    public List<Transactions> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transactions = session.beginTransaction();
        List<Transactions> list = session.createNativeQuery("SELECT * FROM Transactions", Transactions.class).list();
        transactions.commit();
        session.close();
        return list;
    }

    @Override
    public boolean add(Transactions entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transactions = session.beginTransaction();
        session.save(entity);
        transactions.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Transactions entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transactions = session.beginTransaction();
        session.update(entity);
        transactions.commit();
        session.close();
        return true;
    }

    @Override
    public boolean exist(String id) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transactions = session.beginTransaction();
        session.createNativeQuery("delete from Transactions where tranID='"+id+"'", Transactions.class).executeUpdate();

        transactions.commit();
        session.close();
        return true;
    }

    @Override
    public Transactions search(String id) throws SQLException {
        return null;
    }

    public boolean borrowedBooks(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transactions = session.beginTransaction();

        Book item = session.createQuery("FROM Book WHERE id = :id", Book.class)
                .setParameter("id", id)
                .uniqueResult();
        try{
            if (item != null) {
                // Decrement the quantity by 1
                int updatedQty = item.getQty() - 1;
                item.setQty(updatedQty);
                session.update(item);

                transactions.commit();
                return true;
            } else {
                // Book not found
                return false;
            }
        } catch (Exception e) {
            if (transactions != null) {
                transactions.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    public boolean returnedBook(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transactions = session.beginTransaction();

        Book item = session.createQuery("FROM Book WHERE id = :id", Book.class)
                .setParameter("id", id)
                .uniqueResult();
        try{
            if (item != null) {
                // Decrement the quantity by 1
                int updatedQty = item.getQty() + 1;
                item.setQty(updatedQty);
                session.update(item);

                transactions.commit();
                return true;
            } else {
                // Book not found
                return false;
            }
        } catch (Exception e) {
            if (transactions != null) {
                transactions.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
    public String getTotalTransactions() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT count(*) FROM Transactions", Long.class);
        Long count = query.uniqueResult();
        String totalCount = String.valueOf(count);

        transaction.commit();
        session.close();

        return totalCount;
    }
}
