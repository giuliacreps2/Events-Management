package giuliacrepaldi.Event_Management.services;

import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.exceptions.NotFoundException;
import giuliacrepaldi.Event_Management.exceptions.UnauthorizedException;
import giuliacrepaldi.Event_Management.payloads.LoginDTO;
import giuliacrepaldi.Event_Management.security.TokenTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsersService usersService;
    private final TokenTools tokenTools;
    private final PasswordEncoder bcrypt;


    public AuthService(UsersService usersService, TokenTools tokenTools, PasswordEncoder bcrypt) {
        this.usersService = usersService;
        this.tokenTools = tokenTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        try {
            User found = this.usersService.findByEmail(body.email());
            if (bcrypt.matches(body.password(), found.getPassword())) {
                return this.tokenTools.generateToken(found);
            } else {
                throw new UnauthorizedException("");
            }
        } catch (NotFoundException ex) {
            throw new UnauthorizedException("");

        }
    }

}
