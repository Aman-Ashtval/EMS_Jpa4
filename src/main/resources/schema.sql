CREATE TABLE IF NOT EXISTS sponsor(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    industry VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS event(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    date VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS event_sponsor(
    sponsorId INTEGER,
    eventId INTEGER,
    PRIMARY KEY (sponsorId, eventId),
    FOREIGN KEY (sponsorId) REFERENCES sponsor(id),
    FOREIGN KEY (eventId) REFERENCES event(id)
);