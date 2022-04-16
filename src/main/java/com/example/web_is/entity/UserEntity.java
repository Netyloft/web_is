package com.example.web_is.entity;

import com.example.jpa.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserEntity extends BaseEntity {

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

}
