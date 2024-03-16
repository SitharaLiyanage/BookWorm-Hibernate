package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.UserBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dto.BranchDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.Branch;
import lk.ijse.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    public String generateNewUserID() throws IOException {
        return userDAO.generateNewID();
    }
    @Override
    public List<UserDTO> getAllUser() throws Exception {
        List<UserDTO> allUseres= new ArrayList<>();
        List<User> all = userDAO.getAll();
        for (User user : all) {
            allUseres.add(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRepeatpassword()));
        }
        return allUseres;
    }

    @Override
    public boolean addUser(UserDTO dto) throws Exception {
        return userDAO.add(new User(dto.getId(), dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRepeatpassword()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws Exception {
        return userDAO.update(new User(dto.getId(), dto.getUsername(), dto.getEmail(), dto.getPassword(), dto.getRepeatpassword()));
    }


    @Override
    public boolean deleteUser(String id) throws Exception {
        return userDAO.delete(id);
    }

    public UserDTO search(String id) throws Exception {
        User user = userDAO.search(id);
        if (user != null) {
            return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getRepeatpassword());
        } else {
            return null;
        }
    }


}
