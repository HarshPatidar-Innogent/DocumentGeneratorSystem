package com.dgs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(length = 50,nullable = false)
    private String firstName;

    @Column(length = 50,nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 50,nullable = false)
    private String password;

    @JoinColumn(name = "designationId",referencedColumnName ="designationId")
    @ManyToOne
    private Designation designation;

    @JoinColumn(name = "departmentId",referencedColumnName = "departmentId")
    @ManyToOne
    private Department department;

}
