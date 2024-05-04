package com.example.eventmanagementsystem.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.model.*;

public interface EventRepository {

    ArrayList<Event> getAllEvents();

    Event getEventById(int eventId);

    Event addEvent(Event event);

    Event updateEvent(int eventId, Event event);

    void delteEvent(int eventId);

    ArrayList<Sponsor> getEventSponsors(int eventId);
}