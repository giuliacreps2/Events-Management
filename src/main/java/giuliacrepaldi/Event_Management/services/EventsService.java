package giuliacrepaldi.Event_Management.services;

import giuliacrepaldi.Event_Management.entities.Event;
import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.enums.Role;
import giuliacrepaldi.Event_Management.exceptions.BadRequestException;
import giuliacrepaldi.Event_Management.exceptions.NotFoundException;
import giuliacrepaldi.Event_Management.exceptions.UnauthorizedException;
import giuliacrepaldi.Event_Management.payloads.EventDTO;
import giuliacrepaldi.Event_Management.repositories.EventsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EventsService {

    private final EventsRepository eventsRepository;

    public EventsService(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    //1. SAVE (EventDTO body, User organizer)
    public Event saveEvent(EventDTO body, User organizer) {
        //Controlli
        if (this.eventsRepository.existsById()) throw new BadRequestException("Event already exists");

        Event newEvent = new Event(body.eventName(), body.eventDescription(), body.location(), body.eventDate(), body.maxParticipants(), organizer);
        log.info("Saving new event: {}", newEvent);

        return eventsRepository.save(newEvent);
    }

    //2. DELETE(UUID eventId, User user)
    public Event findByIdAndDelete(UUID eventId, User user) {
        Event event = findById(eventId);
        if (!event.getOrganizer().getUserId().equals(Role.USER)) {
            this.eventsRepository.delete(event);
        }
        throw new UnauthorizedException("You are not allowed to delete this event");
    }

    //3. FINDBYID AND UPDATE(NewEventRespDTO)
    public Event findByIdAndUpdate(UUID eventId, EventDTO body, User user) {
        Event event = findById(eventId);
        if (event.getOrganizer().getUserId().equals(Role.USER)) {
            throw new UnauthorizedException("You are not allowed to update this event");
        }
        Event updatedEvent = new Event(body.eventName(), body.eventDescription(), body.location(), body.eventDate(), body.maxParticipants());
        return this.eventsRepository.save(updatedEvent);
    }

    //4. FINDBYID
    public Event findById(UUID eventId) {
        return this.eventsRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
    }

    //5. FINDALL by ORGANIZER
    public List<Event> findByOrganizer(User organizer) {
        return this.eventsRepository.findByOrganizer(organizer);
    }

    //5.1. FINDALL
    public List<Event> findAll() {
        return this.eventsRepository.findAll();
    }


}
