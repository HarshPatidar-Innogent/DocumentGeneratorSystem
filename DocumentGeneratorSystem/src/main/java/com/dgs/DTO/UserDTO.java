package com.dgs.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Long designationId;

    private Long departmentId;
}
