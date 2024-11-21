package org.example.smartjob.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.smartjob.application.dtos.ApiResponseDto;
import org.example.smartjob.application.dtos.UserDto;
import org.example.smartjob.application.dtos.UserResponseDto;
import org.example.smartjob.application.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(summary = "Registrar un nuevo usuario", description = "Este endpoint registra un nuevo usuario en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Error en la validación de datos",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody UserDto request) {
        try {
            UserResponseDto response = userService.registerUser(request);
            return ResponseEntity.ok(new ApiResponseDto<>(200, "Usuario registrado exitosamente", response));
        } catch (Exception ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponseDto<>(400, ex.getMessage(), null));
        }
    }
}
