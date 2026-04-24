package giuliacrepaldi.Event_Management.repositories;

import giuliacrepaldi.Event_Management.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingsRepository extends JpaRepository<Booking, UUID> {
}
