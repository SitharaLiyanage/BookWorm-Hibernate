package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.BookDAO;
import lk.ijse.entity.Book;
import lk.ijse.entity.Book;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    @Override
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT id FROM book ORDER BY id DESC LIMIT 1");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("B0");
            int newID = Integer.parseInt(strings[1]);
            newID++;
            String ID = String.valueOf(newID);
            int length = ID.length();
            if (length < 2) {
                return "B00" + newID;
            } else {
                if (length < 3) {
                    return "B0" + newID;
                } else {
                    return "B" + newID;
                }
            }
        } else {
            return "B001";
        }
    }

    @Override
    public List<Book> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Book> list = session.createNativeQuery("SELECT * FROM Book", Book.class).list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean add(Book entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Book entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
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
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("delete from Book where id='"+id+"'",Book.class).executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Book search(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Book entity = null;

        Query<Book> query = session.createQuery("FROM Book WHERE id = :id", Book.class);
        query.setParameter("id", id);
        List<Book> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            entity = resultList.get(0);
        }

        transaction.commit();
        session.close();
        return entity;
    }
    public String getTotalBooks() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT count(*) FROM Book", Long.class);
        Long count = query.uniqueResult();
        String totalCount = String.valueOf(count);

        transaction.commit();
        session.close();

        return totalCount;
    }
}
