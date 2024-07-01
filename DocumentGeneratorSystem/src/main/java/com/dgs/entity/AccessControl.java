package com.dgs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "access_control")
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
    @JoinColumn(name = "templateId", referencedColumnName = "templateId")
    private Template template;

    @Enumerated(EnumType.STRING)
    private com.dgs.enums.AccessControl templateAccess;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "userId")
    private User ownerId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private String ownerName;

}
