package com.dgs.entity;

import com.dgs.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long documentId;

    @Column(length = 100, nullable = false)
    private String documentName;

    @Column(length = 4000)
    private String documentBody;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "templateId", referencedColumnName = "templateId")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
