package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.UserDTO;

import java.io.IOException;

public interface LoginBO extends SuperBO {
    public boolean saveUser(UserDTO dto) throws Exception;

    public String generateNewUserID() throws IOException;

    public boolean checkPassword(String username, String password) throws IOException;

}
