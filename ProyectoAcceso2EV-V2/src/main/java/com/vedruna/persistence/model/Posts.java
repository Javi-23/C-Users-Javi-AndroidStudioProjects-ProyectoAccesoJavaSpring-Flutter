package com.vedruna.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "T_TWT_POSTS")
public class Posts implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_TWT_POSTS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    private User authorId;

    @Column(name = "C_TWT_POSTS_TEXT", nullable = false)
    @NotBlank(message = "La publicación no puede estar vacía")
    private String text;

    @Lob
    @Column(name = "C_TWT_POSTS_IMG", columnDefinition = "BLOB")
    private byte[] image;

    @CreationTimestamp
    @Column(name = "C_TWT_POSTS_CREATION_DATE", nullable = false, updatable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Europe/Zagreb")
    @Column(name = "C_TWT_POSTS_EDIT_DATE")
    private LocalDateTime editDate;

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL)
    private List<Comments> comments;
}