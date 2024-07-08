package com.dgs.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Long designationId;

    private Long departmentId;

    private String role;

    private String manager;

}
