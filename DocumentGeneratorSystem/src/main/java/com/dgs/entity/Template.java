package com.dgs.entity;

import com.dgs.enums.TemplateFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long templateId;

    @Column(length = 20, nullable = false)
    private String templateName;

    @Enumerated(EnumType.STRING)
    private TemplateFormat templateFormat;

    @Column(length = 4000, nullable = false)
    private String templateBody;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne()
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @OneToMany(mappedBy = "template",cascade = CascadeType.ALL)
    private List<Placeholder> placeholderList;
}
