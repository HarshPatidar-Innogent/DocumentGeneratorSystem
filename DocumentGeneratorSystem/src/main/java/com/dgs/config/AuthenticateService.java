package com.dgs.config;

import com.dgs.DTO.UserDTO;
import com.dgs.controller.AuthenticationRequest;
import com.dgs.controller.AuthenticationResponse;
import com.dgs.controller.RegisterRequest;
import com.dgs.entity.User;
import com.dgs.enums.Role;
import com.dgs.exception.ApiExceptionHandler;
import com.dgs.exception.CustomException.UserNotFoundException;
import com.dgs.mapper.MapperConfig;
import com.dgs.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    private final UserRepo repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final MapperConfig mapperConfig;

    public AuthenticationResponse register(RegisterRequest request) {
//        System.out.println(request.getFirstName());
//        System.out.println(request.getLastName());
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.USER)
//                .build();
        var user = UserDTO.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .departmentId(request.getDepartmentId())
                .designationId(request.getDesignationId())
                .role(request.getRole())
                .build();
        System.out.println();
        repository.save(mapperConfig.toUser(user));
        var jwtToken = jwtService.generateToken(mapperConfig.toUser(user));
        return AuthenticationResponse.builder()
                .token(jwtToken).build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();


        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).user(user.getUserId()).build();
    }
}
