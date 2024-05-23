package com.dgs.entity;

import com.dgs.entity.enums.DesignationPermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "designation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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
