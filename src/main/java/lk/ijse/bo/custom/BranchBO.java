package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.BranchDTO;

import java.io.IOException;
import java.util.List;

public interface BranchBO extends SuperBO {
    public String generateNewUserID() throws IOException;
    public List<BranchDTO> getAllBranches() throws Exception;
    public boolean addBranch(BranchDTO dto) throws Exception;
    public boolean updateBranch(BranchDTO dto) throws Exception;
    public boolean deleteBranch(String id) throws Exception;
    public BranchDTO search(String id) throws Exception;
}
