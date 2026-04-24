package giuliacrepaldi.Event_Management.controllers;

import giuliacrepaldi.Event_Management.entities.Event;
import giuliacrepaldi.Event_Management.entities.User;
import giuliacrepaldi.Event_Management.exceptions.ValidationException;
import giuliacrepaldi.Event_Management.payloads.EventDTO;
import giuliacrepaldi.Event_Management.services.EventsService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventsController {

    private EventsService eventsService;

    //1. POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ORGANIZER', 'ADMIN')")
    public Event saveEvent(@RequestBody @Validated EventDTO body,
                           @AuthenticationPrincipal User user, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(req -> req.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
//        Event newEvent = this.eventsService.saveEvent(body, user);
//        return new NewEventRespDTO(newEvent.getEventId());
        return eventsService.saveEvent(body, user);
    }

    //2. GET
    @GetMapping
    public List<Event> getAllEvents() {
        return this.eventsService.findAll();
    }

    //2.1 GET
    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable UUID eventId) {
        return this.eventsService.findById(eventId);
    }

    //3.PUT
    @PutMapping("/{eventId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('ORGANIZER', 'ADMIN')")
    public Event updateEvent(@PathVariable UUID eventId,
                             @AuthenticationPrincipal User user, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors().stream()
                    .map(req -> req.getDefaultMessage()).toList();
            throw new ValidationException(errors);
        }
//        Event updatedEvent = this.eventsService.findByIdAndDelete(eventId, user);
//        return new NewEventRespDTO(updatedEvent.getEventId());
        return eventsService.findByIdAndDelete(eventId, user);
    }


    //4.DELETE
    @DeleteMapping("/{eventId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ORGANIZER', 'ADMIN')")
    public void deleteEvent(@PathVariable UUID eventId,
                            @AuthenticationPrincipal User user) {
        this.eventsService.findByIdAndDelete(eventId, user);
    }
}
