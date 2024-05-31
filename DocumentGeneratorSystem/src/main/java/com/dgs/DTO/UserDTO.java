package com.dgs.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Long designationId;

    private Long departmentId;

    private String role;
}
