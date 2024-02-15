package com.vedruna.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_TWT_COMMENTS")
public class Comments implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_TWT_COMMENTS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonBackReference
    private Posts postId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User authorId;

    @Column(name = "C_TWT_COMMENTS_COMMENT", nullable = false)
    @NotBlank(message = "La publicación no puede estar vacía")
    private String comment;

    @Lob
    @Column(name = "C_TWT_COMMENTS_IMG", columnDefinition = "BLOB")
    private byte[] image;

    @CreationTimestamp
    @Column(name = "C_TWT_COMMENTS_CREATION_DATE", nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    private LocalDateTime creationDate;
}
