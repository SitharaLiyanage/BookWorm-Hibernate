package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Branch;

public interface BranchDAO extends CrudDAO<Branch> {
    public String getTotalBranches();
}
