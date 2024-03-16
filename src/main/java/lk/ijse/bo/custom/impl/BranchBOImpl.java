package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.BranchBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.BranchDAO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.entity.Branch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BranchBOImpl implements BranchBO {
    BranchDAO branchDAO = (BranchDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BRANCH);

    public String generateNewUserID() throws IOException {
        return branchDAO.generateNewID();
    }
    @Override
    public List<BranchDTO> getAllBranches() throws Exception {
        List<BranchDTO> allBranches= new ArrayList<>();
        List<Branch> all = branchDAO.getAll();
        for (Branch branch : all) {
            allBranches.add(new BranchDTO(branch.getId(), branch.getLocation(), branch.getEmail(), branch.getMobile()));
        }
        return allBranches;
    }

    @Override
    public boolean addBranch(BranchDTO dto) throws Exception {
        return branchDAO.add(new Branch(dto.getId(), dto.getLocation(), dto.getEmail(), dto.getMobile()));
    }

    @Override
    public boolean updateBranch(BranchDTO dto) throws Exception {
        return branchDAO.update(new Branch(dto.getId(), dto.getLocation(), dto.getEmail(), dto.getMobile()));
    }


    @Override
    public boolean deleteBranch(String id) throws Exception {
        return branchDAO.delete(id);
    }
    public BranchDTO search(String id) throws Exception {
        Branch branch = branchDAO.search(id);
        if (branch != null) {
            return new BranchDTO(branch.getId(), branch.getLocation(), branch.getEmail(), branch.getMobile());
        } else {
            return null;
        }
    }

}
