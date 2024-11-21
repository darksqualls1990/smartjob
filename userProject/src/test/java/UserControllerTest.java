import org.example.smartjob.application.dtos.ApiResponseDto;
import org.example.smartjob.application.dtos.UserDto;
import org.example.smartjob.application.dtos.UserResponseDto;
import org.example.smartjob.application.services.user.IUserService;
import org.example.smartjob.presentation.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private IUserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_validRequest_shouldReturnSuccessResponse() {


        UserDto request = new UserDto();
        request.setEmail("test@example.com");
        request.setPassword("Valid@123");
        request.setName("Hector");

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId("123e4567-e89b-12d3-a456-426614174000");
        responseDto.setName(request.getName());

        when(userService.registerUser(request)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<?>> response = userController.registerUser(request);

        assertNotNull(response);
        assertEquals(200, response.getBody().getStatus());
        assertEquals("Usuario registrado exitosamente", response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        verify(userService, times(1)).registerUser(request);
    }

    @Test
    void registerUser_serviceThrowsException_shouldReturnErrorResponse() {
        UserDto request = new UserDto();
        request.setEmail("invalid-email");
        request.setPassword("weakpassword");

        when(userService.registerUser(request))
                .thenThrow(new RuntimeException("Formato de correo inválido"));

        ResponseEntity<ApiResponseDto<?>> response = userController.registerUser(request);

        assertNotNull(response);
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Formato de correo inválido", response.getBody().getMessage());
        assertNull(response.getBody().getData());
        verify(userService, times(1)).registerUser(request);
    }
}
