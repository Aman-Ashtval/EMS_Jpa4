package com.example.eventmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import com.example.eventmanagementsystem.repository.*;
import com.example.eventmanagementsystem.model.*;

@Service
public class SponsorJpaService implements SponsorRepository {

    @Autowired
    private SponsorJpaRepository sponsorJpaRepository;

    @Autowired
    private EventJpaRepository eventJpaRepository;

    @Override
    public ArrayList<Sponsor> getSponsors() {
        List<Sponsor> sponsors = sponsorJpaRepository.findAll();
        return new ArrayList<>(sponsors);
    }

    @Override
    public Sponsor getSponsorById(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
            return sponsor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Sponsor addSponsor(Sponsor sponsor) {
        List<Integer> eventsIds = new ArrayList<>();

        for (Event event : sponsor.getEvents()) {
            eventsIds.add(event.getEventId());
        }

        List<Event> events = eventJpaRepository.findAllById(eventsIds);

        if (eventsIds.size() != events.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        sponsor.setEvents(events);

        for (Event event : events) {
            event.getSponsors().add(sponsor);
        }

        Sponsor newSponsor = sponsorJpaRepository.save(sponsor);

        eventJpaRepository.saveAll(events);
        return newSponsor;
    }

    @Override
    public Sponsor updateSponsor(int sponsorId, Sponsor sponsor) {
        try {
            Sponsor newSponsor = sponsorJpaRepository.findById(sponsorId).get();
            if (sponsor.getSponsorName() != null) {
                newSponsor.setSponsorName(sponsor.getSponsorName());
            }
            if (sponsor.getIndustry() != null) {
                newSponsor.setIndustry(sponsor.getIndustry());
            }
            if (sponsor.getEvents() != null) {
                List<Event> events = newSponsor.getEvents();

                for (Event event : events) {
                    event.getSponsors().remove(newSponsor);
                }

                eventJpaRepository.saveAll(events);

                List<Integer> newEventsIds = new ArrayList<>();

                for (Event event : sponsor.getEvents()) {
                    newEventsIds.add(event.getEventId());
                }

                List<Event> newEvents = eventJpaRepository.findAllById(newEventsIds);

                if (newEvents.size() != newEventsIds.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Event event : newEvents) {
                    event.getSponsors().add(newSponsor);
                }

                eventJpaRepository.saveAll(newEvents);
                newSponsor.setEvents(newEvents);
            }
            return sponsorJpaRepository.save(newSponsor);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteSponsor(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();

            List<Event> events = sponsor.getEvents();
            for (Event event : events) {
                event.getSponsors().remove(sponsor);
            }

            eventJpaRepository.saveAll(events);
            sponsorJpaRepository.deleteById(sponsorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public ArrayList<Event> getSponsorEvents(int sponsorId) {
        try {
            Sponsor sponsor = sponsorJpaRepository.findById(sponsorId).get();
            return new ArrayList<>(sponsor.getEvents());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}