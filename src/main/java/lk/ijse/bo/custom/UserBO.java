package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface UserBO extends SuperBO {
    public String generateNewUserID() throws IOException;
    public List<UserDTO> getAllUser() throws Exception;
    public boolean addUser(UserDTO dto) throws Exception;
    public boolean updateUser(UserDTO dto) throws Exception;
    public boolean deleteUser(String id) throws Exception;
    public UserDTO search(String id) throws Exception;
}
