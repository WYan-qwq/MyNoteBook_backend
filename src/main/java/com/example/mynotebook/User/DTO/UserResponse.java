package com.example.mynotebook.User.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String userName;
    private String email;
    private String picture;
}