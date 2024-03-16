package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;

public class LoginDAOImpl implements LoginDAO {
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
    public boolean save(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    public boolean checkPassword(String username, String password) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT password FROM user WHERE username = :username");
        nativeQuery.setParameter("username",username);

        String pass = nativeQuery.uniqueResult();

        transaction.commit();
        session.close();

        if (password.equalsIgnoreCase(pass)) {
            return true;
        }else {
            return false;
        }
    }
}
