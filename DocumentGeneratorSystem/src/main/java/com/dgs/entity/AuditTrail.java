package com.dgs.entity;

import com.dgs.enums.ActionPerformed;
import com.dgs.enums.SignatureType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_trail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long auditTrailId;

    @Enumerated(EnumType.STRING)
    private ActionPerformed actionPerformed;

    private String details;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @Column(length = 40,nullable = false,unique = true)
    private String ipAddress;

    @ManyToOne
    @JoinColumn(name ="documentId", referencedColumnName = "documentId")
    private Document document;

    @ManyToOne
    @JoinColumn(name="userId",referencedColumnName = "userId")
    private User user;
}
