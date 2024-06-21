package com.dgs.entity;

import com.dgs.enums.TemplateAccess;
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

    @Enumerated(EnumType.STRING)
    private TemplateAccess templateAccess;

    private User ownerId;

    private User userId;

}
