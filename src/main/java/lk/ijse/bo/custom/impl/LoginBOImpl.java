package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.LoginDAO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;

import java.io.IOException;

public class LoginBOImpl implements LoginBO {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);
    public boolean saveUser(UserDTO dto) throws Exception {
        return loginDAO.save(new User(dto.getId(), dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRepeatpassword()));
    }

    public String generateNewUserID() throws IOException{
        return loginDAO.generateNewID();
    }

    public boolean checkPassword(String username, String password) throws IOException{
        return loginDAO.checkPassword(username, password);
    }
}
