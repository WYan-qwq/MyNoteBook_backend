package com.example.mynotebook.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserInfoEntity {


    @Id
    @Column(name = "UserId", nullable = false)
    private Integer id;

    @Column(name = "UserName", length = 100)
    private String userName;

    @Column(name = "password", nullable = false)
    private Integer password;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "picture", length = 100)
    private String picture;

}
