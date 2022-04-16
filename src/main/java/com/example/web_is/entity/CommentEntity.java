package com.example.web_is.entity;

import com.example.jpa.entity.BaseEntity;
import com.example.web_is.data.User;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommentEntity extends BaseEntity {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;
}
