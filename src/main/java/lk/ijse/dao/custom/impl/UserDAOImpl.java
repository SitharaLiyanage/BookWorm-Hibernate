package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT id FROM user ORDER BY id DESC LIMIT 1");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("U0");
            int newID = Integer.parseInt(strings[1]);
            newID++;
            String ID = String.valueOf(newID);
            int length = ID.length();
            if (length < 2) {
                return "U00" + newID;
            } else {
                if (length < 3) {
                    return "U0" + newID;
                } else {
                    return "U" + newID;
                }
            }
        } else {
            return "U001";
        }
    }

    @Override
    public List<User> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<User> list = session.createNativeQuery("SELECT * FROM User", User.class).list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean add(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
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
        session.createNativeQuery("delete from User where id='"+id+"'", User.class).executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public User search(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        User entity = null;

        Query<User> query = session.createQuery("FROM User WHERE id = :id", User.class);
        query.setParameter("id", id);
        List<User> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            entity = resultList.get(0);
        }

        transaction.commit();
        session.close();
        return entity;
    }
    public String getTotalUsers() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT count(*) FROM user", Long.class);
        Long count = query.uniqueResult();
        String totalCount = String.valueOf(count);

        transaction.commit();
        session.close();

        return totalCount;
    }
}
