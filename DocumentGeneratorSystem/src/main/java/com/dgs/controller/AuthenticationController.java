package com.dgs.controller;

import com.dgs.config.AuthenticateService;
import com.dgs.exception.ApiException;
import com.dgs.exception.CustomException.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticateService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate (
            @RequestBody AuthenticationRequest request
    )  {
        try{
            return ResponseEntity.ok(service.authenticate(request));
        }catch (Exception e){
            throw new UserNotFoundException("User Not Found");
        }
    }
}
