package com.dgs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "placeholder")
public class Placeholder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long placeholderId;

    @Column(length = 50)
    private String placeholderName;

    @Column(length = 20)
    private String placeholderType;

    @ManyToOne
    @JoinColumn(name = "templateId", referencedColumnName = "templateId")
    private Template template   ;

}
