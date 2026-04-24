package giuliacrepaldi.Event_Management.services;

import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.exceptions.BadRequestException;
import giuliacrepaldi.Event_Management.exceptions.NotFoundException;
import giuliacrepaldi.Event_Management.payloads.UserDTO;
import giuliacrepaldi.Event_Management.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder bcrypt;

    public UsersService(UsersRepository usersRepository, PasswordEncoder bcrypt) {
        this.usersRepository = usersRepository;
        this.bcrypt = bcrypt;
    }

    //1.SAVE saveUser(UserDTO dto)

    public User saveUser(UserDTO body) {
        if (this.usersRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email" + body.email() + " already exists");

        User newUser = new User(body.first_name(), body.last_name(), body.email(), this.bcrypt.encode(body.password()), body.birth_date());
        User savedUser = this.usersRepository.save(newUser);

        log.info("User" + savedUser.getFirst_name() + " " + savedUser.getEmail() + " saved successfully");

        return savedUser;
    }

    //2.FINDBYEMAIL findByEmail(String email)
    public User findByEmail(String email) {
        return this.usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email" + email + " not found"));
    }


}
