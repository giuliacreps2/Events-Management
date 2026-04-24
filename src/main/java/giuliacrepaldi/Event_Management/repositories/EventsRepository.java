package giuliacrepaldi.Event_Management.repositories;

import giuliacrepaldi.Event_Management.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventsRepository extends JpaRepository<Event, UUID> {
    Optional<Event> findByEventName(String eventName);

    Optional<Event> findById(UUID eventId);

}
