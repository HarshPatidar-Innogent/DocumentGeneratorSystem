package com.dgs.DTO;

import lombok.Data;

@Data
public class AccessControlDTO {
    private Long accessControlId;
    private TemplateDTO template;
    private DepartmentDTO department;
    private DesignationDTO designation;
}
