package giuliacrepaldi.Event_Management.controllers;

import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.exceptions.ValidationException;
import giuliacrepaldi.Event_Management.payloads.LoginDTO;
import giuliacrepaldi.Event_Management.payloads.LoginRespDTO;
import giuliacrepaldi.Event_Management.payloads.NewUserRespDTO;
import giuliacrepaldi.Event_Management.payloads.UserDTO;
import giuliacrepaldi.Event_Management.services.AuthService;
import giuliacrepaldi.Event_Management.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UsersService usersService;

    public AuthController(AuthService authService, UsersService usersService) {
        this.authService = authService;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public NewUserRespDTO saveUser(@RequestBody @Validated UserDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }

        User newUser = this.usersService.saveUser(body);
        return new NewUserRespDTO(newUser.getUserId());
    }

}
