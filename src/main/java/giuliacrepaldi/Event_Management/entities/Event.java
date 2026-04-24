package giuliacrepaldi.Event_Management.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    private String event_name;
    private String event_description;
    private String event_location;
    private LocalDate event_date;
    private Integer max_participants;

    private User created_by;

    @JoinColumns
    private User user;
}
