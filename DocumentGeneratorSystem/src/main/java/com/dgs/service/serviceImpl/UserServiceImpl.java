package com.dgs.service.serviceImpl;

import com.dgs.DTO.UserDTO;
import com.dgs.entity.User;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepo.findByEmail(email).get();
        return mapperConfig.toUserDTO(user);
    }
}
