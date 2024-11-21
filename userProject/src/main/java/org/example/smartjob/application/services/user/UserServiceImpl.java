package org.example.smartjob.application.services.user;

import org.example.smartjob.application.dtos.UserDto;
import org.example.smartjob.application.dtos.UserResponseDto;
import org.example.smartjob.domain.entities.User;
import org.example.smartjob.domain.repositories.user.IUserRepository;
import org.example.smartjob.infrastructure.exception.ValidationException;
import org.example.smartjob.infrastructure.security.JwtUtil;
import org.example.smartjob.infrastructure.util.DateUtils;
import org.example.smartjob.infrastructure.util.KeyValidationUtils;
import org.example.smartjob.infrastructure.util.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{

    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final KeyValidationUtils keyValidationUtils;


    @Autowired
    public UserServiceImpl(IUserRepository userRepository, ModelMapper modelMapper, JwtUtil jwtUtil, KeyValidationUtils keyValidationUtils) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtUtil=jwtUtil;
        this.keyValidationUtils = keyValidationUtils;
    }

    @Override
    public UserResponseDto registerUser(UserDto userDto)throws ValidationException {

        if (!StringUtils.isValidEmail(userDto.getEmail())) {
            throw new ValidationException("Formato de correo inválido");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ValidationException("El correo ya está registrado");
        }

        keyValidationUtils.validateKey(userDto.getPassword());

        // Mapear UserDTO a User
        User user = modelMapper.map(userDto, User.class);
        user.setToken(jwtUtil.generateToken(userDto.getEmail()));
        user.setActive(true);

        // Guardar usuario
        User savedUser = userRepository.save(user);
        UserResponseDto userResponseDto=modelMapper.map(savedUser, UserResponseDto.class);
        userResponseDto.setCreated(DateUtils.formatLocalDateTime(savedUser.getCreated()));
        userResponseDto.setLastLogin(DateUtils.formatLocalDateTime(savedUser.getLastLogin()));
        userResponseDto.setModified(DateUtils.formatLocalDateTime(savedUser.getModified()));

        return userResponseDto;
    }
}
