package com.dgs.entity;
import com.dgs.enums.SignatureType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Table(name ="signature")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Signature {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long signatureId;

  @Enumerated(EnumType.STRING)
  private SignatureType signatureType;

  @Column(nullable = false)
  private Blob signatureData;

  @Column(length = 50,nullable = false,unique = true)
  private String recipientEmail;

  @CreationTimestamp
  private LocalDateTime signedAt;

  @ManyToOne
  @JoinColumn(name ="userId",referencedColumnName = "userId")
  private User user;

  @ManyToOne
  @JoinColumn(name ="documentId",referencedColumnName = "documentId")
  private Document document;

}
