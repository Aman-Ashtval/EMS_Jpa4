package com.example.eventmanagementsystem.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.eventmanagementsystem.model.*;

public interface SponsorRepository {

    ArrayList<Sponsor> getSponsors();

    Sponsor getSponsorById(int sponsorId);

    Sponsor addSponsor(Sponsor sponsor);

    Sponsor updateSponsor(int sponsorId, Sponsor sponsor);

    void deleteSponsor(int sponsorId);

    ArrayList<Event> getSponsorEvents(int sponsorId);

}