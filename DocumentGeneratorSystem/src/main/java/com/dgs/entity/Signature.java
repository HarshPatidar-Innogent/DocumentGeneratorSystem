package com.dgs.entity;
import com.dgs.enums.SignatureType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmOnDeleteEnum;

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

  private String recipientEmail;

  @Column(columnDefinition = "boolean default false")
  private Boolean signed;

  @Lob
  @Column(columnDefinition = "LONGBLOB")
  private byte[] signatureData;

  private String placeholder;

  @CreationTimestamp
  private LocalDateTime signedAt;

  @ManyToOne
  @JoinColumn(name ="documentId",referencedColumnName = "documentId")
  private Document document;


  
}
