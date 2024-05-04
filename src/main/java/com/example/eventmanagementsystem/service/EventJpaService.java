package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.eventmanagementsystem.model.*;
import com.example.eventmanagementsystem.repository.*;

@Service
public class EventJpaService implements EventRepository {

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Override
    public ArrayList<Event> getAllEvents() {
        List<Event> events = eventJpaRepository.findAll();
        return new ArrayList<>(events);
    }

    @Override
    public Event getEventById(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();
            return event;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Event addEvent(Event event) {
        List<Integer> sponsorIds = new ArrayList<>();

        for (Sponsor sponsor : event.getSponsors()) {
            sponsorIds.add(sponsor.getSponsorId());
        }

        List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);

        if (sponsorIds.size() != sponsors.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        event.setSponsors(sponsors);
        return eventJpaRepository.save(event);
    }

    @Override
    public Event updateEvent(int eventId, Event event) {
        try {
            Event existEvent = eventJpaRepository.findById(eventId).get();
            if (event.getEventName() != null) {
                existEvent.setEventName(event.getEventName());
            }
            if (event.getDate() != null) {
                existEvent.setDate(event.getDate());
            }
            if (event.getSponsors() != null) {
                List<Integer> sponsorIds = new ArrayList<>();

                for (Sponsor sponsor : event.getSponsors()) {
                    sponsorIds.add(sponsor.getSponsorId());
                }

                List<Sponsor> sponsors = sponsorJpaRepository.findAllById(sponsorIds);
                existEvent.setSponsors(sponsors);
            }
            return eventJpaRepository.save(existEvent);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delteEvent(int eventId) {
        Event event = this.getEventById(eventId);
        if (event != null) {
            eventJpaRepository.deleteById(eventId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ArrayList<Sponsor> getEventSponsors(int eventId) {
        try {
            Event event = eventJpaRepository.findById(eventId).get();
            return new ArrayList<>(event.getSponsors());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}