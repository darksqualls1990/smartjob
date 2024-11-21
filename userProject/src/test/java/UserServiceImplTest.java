import org.example.smartjob.application.dtos.UserDto;
import org.example.smartjob.application.dtos.UserResponseDto;
import org.example.smartjob.application.services.user.UserServiceImpl;
import org.example.smartjob.domain.entities.User;
import org.example.smartjob.domain.repositories.user.IUserRepository;
import org.example.smartjob.infrastructure.exception.ValidationException;
import org.example.smartjob.infrastructure.security.JwtUtil;
import org.example.smartjob.infrastructure.util.KeyValidationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private KeyValidationUtils keyValidationUtils;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_validInput_shouldReturnUserResponseDto() {

        UUID id = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("Valid@123");
        userDto.setName("Hector");

        User user = new User();
        user.setId(id);
        user.setName(userDto.getName());

        User savedUser = new User();
        savedUser.setId(id);
        savedUser.setEmail(userDto.getEmail());
        savedUser.setCreated(null); // Example for created timestamp

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId("123e4567-e89b-12d3-a456-426614174000");
        userResponseDto.setName(userDto.getName());

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        doNothing().when(keyValidationUtils).validateKey(userDto.getPassword());
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(modelMapper.map(savedUser, UserResponseDto.class)).thenReturn(userResponseDto);

        UserResponseDto response = userService.registerUser(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(userDto.getName(), response.getName());
        verify(userRepository, times(1)).existsByEmail(userDto.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(keyValidationUtils, times(1)).validateKey(userDto.getPassword());
    }

    @Test
    void registerUser_invalidEmail_shouldThrowValidationException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("invalid-email");
        userDto.setPassword("Valid@123");

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(userDto);
        });

        assertEquals("Formato de correo inválido", exception.getMessage());
        verify(userRepository, never()).existsByEmail(anyString());
    }

    @Test
    void registerUser_existingEmail_shouldThrowValidationException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("Valid@123");

        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(true);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(userDto);
        });

        assertEquals("El correo ya está registrado", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(userDto.getEmail());
    }

    @Test
    void registerUser_invalidPassword_shouldThrowValidationException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("weakpassword");

        doThrow(new ValidationException("La clave debe contener al menos una mayúscula, un carácter especial y tener entre 8 y 20 caracteres."))
                .when(keyValidationUtils).validateKey(userDto.getPassword());

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(userDto);
        });

        assertEquals("La clave debe contener al menos una mayúscula, un carácter especial y tener entre 8 y 20 caracteres.", exception.getMessage());
        verify(userRepository, times(1)).existsByEmail(userDto.getEmail());
        verify(keyValidationUtils, times(1)).validateKey(userDto.getPassword());
    }
}
