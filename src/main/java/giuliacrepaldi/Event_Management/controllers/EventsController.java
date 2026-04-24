package giuliacrepaldi.Event_Management.controllers;

import giuliacrepaldi.Event_Management.entities.Event;
import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.payloads.EventDTO;
import giuliacrepaldi.Event_Management.services.EventsService;
import giuliacrepaldi.Event_Management.services.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsController {

    private EventsService eventsService;
    private UsersService usersService;

    //1. POST
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZER', 'ADMIN')")
    public Event saveEvent(@RequestBody EventDTO body, @AuthenticationPrincipal User user) {
        return eventsService.saveEvent(body, user);
    }

}
