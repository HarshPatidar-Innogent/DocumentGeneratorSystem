package com.dgs.controller;

import com.dgs.DTO.ChangePasswordDTO;
import com.dgs.DTO.UserDTO;
import com.dgs.exception.CustomException.UserException;
import com.dgs.exception.CustomException.UserNotFoundException;
import com.dgs.repository.UserRepo;
import com.dgs.service.iService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/user")

    @PostMapping("/addUser")
    // @PreAuthorize("hasauthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
        try {
            System.out.println(userDTO.getManager());
            UserDTO createUser = userService.addUser(userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(createUser);
        } catch (Exception e) {
            throw new UserException("Exception in adding new user");
        }
    }

    @GetMapping("/getallUser")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        System.out.println("getAllUserDTO");

        try {
            List<UserDTO> getAllUserDTO = userService.getAllUser();
            return ResponseEntity.status(HttpStatus.OK).body(getAllUserDTO);
        } catch (Exception e) {
            throw new UserException("Exception in getting all users");
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (Exception e) {
            throw new UserException("Exception in updating user");
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try{
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(userDTO);
        }catch (Exception e){
            throw new UserNotFoundException("No user with userId "+id+" exists");
        }
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody ChangePasswordDTO requestPassword) {
        try{
            userService.changePassword(email, requestPassword);
            return ResponseEntity.status(HttpStatus.OK).body("Password Changed Successfully");
        }catch (Exception e){
            throw new UserException("Exception in changing password");
        }
    }

}
