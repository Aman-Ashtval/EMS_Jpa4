package com.example.eventmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.service.EventJpaService;
import com.example.eventmanagementsystem.model.*;

@RestController
public class EventController {

    @Autowired
    private EventJpaService eventJpaService;

    @GetMapping("/events")
    public ArrayList<Event> getAllEvents() {
        return eventJpaService.getAllEvents();
    }

    @GetMapping("/events/{eventId}")
    public Event getEventById(@PathVariable("eventId") int eventId) {
        return eventJpaService.getEventById(eventId);
    }

    @PostMapping("/events")
    public Event addEvent(@RequestBody Event event) {
        return eventJpaService.addEvent(event);
    }

    @PutMapping("/events/{eventId}")
    public Event updateEvent(@PathVariable("eventId") int eventId, @RequestBody Event event) {
        return eventJpaService.updateEvent(eventId, event);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@PathVariable("eventId") int eventId) {
        eventJpaService.delteEvent(eventId);
    }

    @GetMapping("/events/{eventId}/sponsors")
    public ArrayList<Sponsor> getEventSponsors(@PathVariable("eventId") int eventId) {
        return eventJpaService.getEventSponsors(eventId);
    }

}