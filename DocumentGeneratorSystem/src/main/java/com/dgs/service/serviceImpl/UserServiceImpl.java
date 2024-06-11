package com.dgs.service.serviceImpl;

import com.dgs.DTO.UserDTO;
import com.dgs.entity.Department;
import com.dgs.entity.Designation;
import com.dgs.entity.User;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DepartmentRepo;
import com.dgs.repository.DesignationRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MapperConfig mapperConfig;

    @Autowired
    private DesignationRepo designationRepo;

    @Autowired
    private DepartmentRepo departmentRepo;



    @Override
    public UserDTO addUser(UserDTO userDTO) {
        Designation designation =  designationRepo.findById(userDTO.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found with id: " + userDTO.getDesignationId()));
        Department department = departmentRepo.findById(userDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + userDTO.getDepartmentId()));

        User userEntity = mapperConfig.toUser(userDTO);
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setDepartment(department);
        userEntity.setDesignation(designation);

        User userEntity1 = userRepo.save(userEntity);
        return mapperConfig.toUserDTO(userEntity1);

    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepo.findAll();
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }
        return users.stream()
                .map(mapperConfig::toUserDTO)
                .collect(Collectors.toList());
    }
    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
//            user.setPassword(userDTO.getPassword());

            if (userDTO.getDesignationId() != null) {
                Designation designation = designationRepo.findById(userDTO.getDesignationId())
                        .orElseThrow(() -> new RuntimeException("Designation not found with id: " + userDTO.getDesignationId()));
                user.setDesignation(designation);
            } else {
                user.setDesignation(null);
            }

            if (userDTO.getDepartmentId() != null) {
                Department department = departmentRepo.findById(userDTO.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Department not found with id: " + userDTO.getDepartmentId()));
                user.setDepartment(department);
            } else {
                user.setDepartment(null);
            }

            User updatedUser = userRepo.save(user);
            return mapperConfig.toUserDTO(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }
    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (userOptional.isPresent()) {
            return mapperConfig.toUserDTO(userOptional.get());
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userRepo.findByEmail(email).get();
        return mapperConfig.toUserDTO(user);
    }

}
