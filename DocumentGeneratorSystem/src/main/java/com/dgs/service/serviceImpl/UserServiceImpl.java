package com.dgs.service.serviceImpl;

import com.dgs.DTO.ChangePasswordDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.entity.Department;
import com.dgs.entity.Designation;
import com.dgs.entity.User;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.DepartmentRepo;
import com.dgs.repository.DesignationRepo;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public UserDTO addUser(UserDTO userDTO) {
        Designation designation =  designationRepo.findById(userDTO.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found with id: " + userDTO.getDesignationId()));
        Department department = departmentRepo.findById(userDTO.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + userDTO.getDepartmentId()));
        System.out.println(userDTO.getManager());
        User userEntity = mapperConfig.toUser(userDTO);
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setManager(userDTO.getManager());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
//        userEntity.setPassword(userDTO.getPassword());
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
            user.setManager(userDTO.getManager());
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

    @Transactional
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

    @Override
    public UserDTO findUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(()->new UsernameNotFoundException("User Not Found"));
        return mapperConfig.toUserDTO(user);
    }

    @Override
    public void changePassword(String email, ChangePasswordDTO requestPassword) {
        User user = userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));

        System.out.println("New Password Length: " + requestPassword.getNewPassword().length());
        System.out.println("Confirm Password Length: " + requestPassword.getConfirmPassword().length());

        if(!passwordEncoder.matches(requestPassword.getOldPassword(),user.getPassword())){
            System.out.println(requestPassword.getOldPassword());
            System.out.println(user.getPassword());
            throw new RuntimeException("Old password do not match");
        }

        if (!requestPassword.getNewPassword().equals(requestPassword.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        String encryptPassword = passwordEncoder.encode(requestPassword.getNewPassword());
        user.setPassword(encryptPassword);
        userRepo.save(user);
    }






}
