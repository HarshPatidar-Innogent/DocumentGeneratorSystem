package com.dgs.entity;

import com.dgs.enums.DesignationPermission;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "designation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Designation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long designationId;

    @Column(length = 50,nullable = false)
    private String designationName;

    @Column(length = 100)
    private String description;

    @Enumerated(EnumType.STRING)
    private DesignationPermission permission;


}
