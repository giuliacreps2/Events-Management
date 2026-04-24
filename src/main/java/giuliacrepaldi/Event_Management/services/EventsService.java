package giuliacrepaldi.Event_Management.services;

import giuliacrepaldi.Event_Management.entities.Event;
import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.payloads.EventDTO;
import giuliacrepaldi.Event_Management.repositories.EventsRepository;
import giuliacrepaldi.Event_Management.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventsService {

    private final EventsRepository eventsRepository;
    private final UsersRepository usersRepository;

    public EventsService(EventsRepository eventsRepository, UsersRepository usersRepository) {
        this.eventsRepository = eventsRepository;
        this.usersRepository = usersRepository;
    }

    //1. SAVE (EventDTO body, User organizer)
    public Event saveEvent(EventDTO body, User user) {
        Event newEvent = this.eventsRepository.saveEvent(body);
        return new NewEventRespDTO(newEvent.getEventName());
    }

    //2. DELETE(UUID eventId, User user)

    //3. FINDBYID AND UPDATE(NewEventRespDTO)


}
