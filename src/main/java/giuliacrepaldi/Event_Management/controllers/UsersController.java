package giuliacrepaldi.Event_Management.controllers;

import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.exceptions.ValidationException;
import giuliacrepaldi.Event_Management.payloads.NewUserRespDTO;
import giuliacrepaldi.Event_Management.payloads.UserDTO;
import giuliacrepaldi.Event_Management.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    //1. POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO saveUser(@RequestBody @Validated UserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
        User newUser = this.usersService.saveUser(body);
        return new NewUserRespDTO(newUser.getUserId());
    }

}
