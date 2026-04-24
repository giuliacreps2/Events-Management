package giuliacrepaldi.Event_Management.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    @Column(nullable = false)
    private String eventName;
    @Column(nullable = false)
    private String eventDescription;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private LocalDate eventDate;
    @Column(nullable = false)
    private Integer maxParticipants;
    private Integer currentParticipants = 0;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User organizer;

    public Event(String eventName, String eventDescription, String location, LocalDate eventDate, Integer maxParticipants, User organizer) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventDate = eventDate;
        this.maxParticipants = maxParticipants;
        this.organizer = organizer;
    }

    public Event(String eventName, String eventDescription, String location, LocalDate eventDate, Integer maxParticipants) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.location = location;
        this.eventDate = eventDate;
        this.maxParticipants = maxParticipants;
    }
}
