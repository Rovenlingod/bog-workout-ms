package com.example.bogworkoutms.feign.feignDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String login;
    private String encryptedPassword;
    private String email;
    private String firstName;
    private String lastName;
}
