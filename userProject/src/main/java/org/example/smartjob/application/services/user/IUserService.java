package org.example.smartjob.application.services.user;

import org.example.smartjob.application.dtos.UserDto;
import org.example.smartjob.application.dtos.UserResponseDto;

public interface IUserService {
    UserResponseDto registerUser(UserDto user);
}
