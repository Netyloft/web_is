package com.example.web_is.entity;

import com.example.jpa.entity.BaseEntity;
import com.example.web_is.data.Comment;
import com.example.web_is.data.User;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "articles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticleEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @Column(name = "tags")
    private String tags;
}
