package com.dgs.service.iService;

import com.dgs.DTO.ChangePasswordDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.entity.User;

import java.util.List;

public interface IUserService {
    public UserDTO findUserByEmail(String email);

    UserDTO addUser(UserDTO userDTO);

    List<UserDTO> getAllUser();

    UserDTO updateUser(Long id, UserDTO userDTO);

    void deleteUserById(Long id);

    UserDTO getUserById(Long id);

    public UserDTO findUserById(Long id);

    public void changePassword(String email, ChangePasswordDTO requestPassword);
}
