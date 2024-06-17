package com.dgs.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long departmentId;
    private Long designationId;
    private String role;
}
