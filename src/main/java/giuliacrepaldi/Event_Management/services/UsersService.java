package giuliacrepaldi.Event_Management.services;

import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.enums.Role;
import giuliacrepaldi.Event_Management.exceptions.BadRequestException;
import giuliacrepaldi.Event_Management.exceptions.NotFoundException;
import giuliacrepaldi.Event_Management.exceptions.UnauthorizedException;
import giuliacrepaldi.Event_Management.payloads.UserDTO;
import giuliacrepaldi.Event_Management.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
            throw new BadRequestException("Email" + body.email() + "already exists");

        User newUser = new User(body.firstName(), body.lastName(), body.email(), this.bcrypt.encode(body.password()), body.birthDate());
        User savedUser = usersRepository.save(newUser);

        log.info("User saved: " + savedUser.toString());

        return savedUser;
    }

    //2.FINDBYEMAIL findByEmail(String email)
    public User findByEmail(String email) {
        return this.usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email" + email + " not found"));
    }

    //3.DELETE deleteUser(String email, User user)
    public void findByEmailAndDelete(String email, User user) {
        if (user.getRole().equals(Role.ADMIN) || user.getEmail().equals(email)) {
            this.usersRepository.delete(user);
        } else {
            throw new UnauthorizedException("Unauthorized");
        }
        log.info("User deleted: " + user.toString());
    }

    public void findByEmailAndDelete(String email) {
        User user = findByEmail(email);
        this.usersRepository.delete(user);
        log.info("User deleted: " + user.toString());
    }

    //FINDBYID
    public User findById(UUID userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id" + userId + " not found"));
    }
}
