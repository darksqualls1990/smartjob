package org.example.smartjob.application.dtos;

import lombok.Data;

@Data
public class UserResponseDto {

    private String id;
    private String name;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private boolean isActive;
}
