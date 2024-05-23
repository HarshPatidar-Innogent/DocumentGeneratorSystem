package com.dgs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="access_control")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccessControl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accessControlId;

    @ManyToOne
    @JoinColumn(name="templateId",referencedColumnName = "templateId")
    private Template template;

    @ManyToOne
    @JoinColumn(name ="departmentId" ,referencedColumnName = "departmentId")
    private Department department;

    @ManyToOne
    @JoinColumn(name="designationId" ,referencedColumnName = "designationId")
    private Designation designation;

}
