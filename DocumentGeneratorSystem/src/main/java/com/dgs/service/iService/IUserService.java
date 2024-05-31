package com.dgs.service.iService;

import com.dgs.DTO.UserDTO;

public interface IUserService {
    public UserDTO findUserByEmail(String email);
}
