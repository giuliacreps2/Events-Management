package giuliacrepaldi.Event_Management.repositories;

import giuliacrepaldi.Event_Management.entities.Event;
import giuliacrepaldi.Event_Management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventsRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByEventName(String eventName);

    Optional<Event> findByEventId(UUID eventId);

    List<Event> findByOrganizer(User organizer);

    boolean existsByEventId(UUID eventId);
}
