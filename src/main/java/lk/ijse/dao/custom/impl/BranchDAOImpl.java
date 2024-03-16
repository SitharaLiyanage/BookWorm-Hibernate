package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.BranchDAO;
import lk.ijse.entity.Branch;
import lk.ijse.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BranchDAOImpl implements BranchDAO {
    public String generateNewID() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<String> nativeQuery = session.createNativeQuery("SELECT id FROM Branch ORDER BY id DESC LIMIT 1");
        String id = nativeQuery.uniqueResult();
        transaction.commit();
        session.close();

        if (id != null) {
            String[] strings = id.split("BR0");
            int newID = Integer.parseInt(strings[1]);
            newID++;
            String ID = String.valueOf(newID);
            int length = ID.length();
            if (length < 2) {
                return "BR00" + newID;
            } else {
                if (length < 3) {
                    return "BR0" + newID;
                } else {
                    return "BR" + newID;
                }
            }
        } else {
            return "BR001";
        }
    }
    @Override
    public List<Branch> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Branch> list = session.createNativeQuery("SELECT * FROM Branch", Branch.class).list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean add(Branch entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Branch entity) throws Exception {
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
        session.createNativeQuery("delete from Branch where id='"+id+"'",Branch.class).executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Branch search(String id) throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Branch entity = null;

        Query<Branch> query = session.createQuery("FROM Branch WHERE id = :id", Branch.class);
        query.setParameter("id", id);
        List<Branch> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            entity = resultList.get(0);
        }

        transaction.commit();
        session.close();
        return entity;
    }

    public String getTotalBranches() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Long> query = session.createQuery("SELECT count(*) FROM Branch", Long.class);
        Long count = query.uniqueResult();
        String totalCount = String.valueOf(count);

        transaction.commit();
        session.close();

        return totalCount;
    }
}
