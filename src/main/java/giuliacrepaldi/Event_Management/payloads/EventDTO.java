package giuliacrepaldi.Event_Management.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventDTO(
        @NotBlank(message = "Event Name cannot be empty")
        @Size(min = 2, max = 30, message = "Event Name must be between 2 and 30 characters")
        String eventName,
        @NotBlank(message = "Event description cannot be empty")
        @Size(min = 10, max = 150, message = "Event description must be between 10 and 150 characters")
        String eventDescription,
        String location,
        LocalDate eventDate,
        Integer maxParticipants
) {
}
