package giuliacrepaldi.Event_Management.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record UserDTO(
        @NotBlank(message = "Your first name cannot be empty")
        @Size(min = 2, max = 30, message = "Your first name must be between 2 and 30 characters")
        String firstName,
        @NotBlank(message = "Your last name cannot be empty")
        @Size(min = 2, max = 30, message = "Your last name must be between 2 and 30 characters")
        String lastName,
        @NotBlank(message = "Your email cannot be empty")
        @Email(message = "The email address you entered is not in the correct format.")
        String email,
        @NotBlank(message = "Please enter a password")
        @Size(min = 4, message = "Password must be at least 4 characters long")
        String password,
        @Past
        LocalDate birth_date
) {
}
