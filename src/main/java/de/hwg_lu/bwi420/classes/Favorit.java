package de.hwg_lu.bwi420.classes;

import java.sql.Timestamp;

public class Favorit {

    private int favoritId;
    private String email;
    private int eventId;
    private Timestamp favoritDatum;

    public Favorit() {
    }

    public Favorit(int favoritId, String email, int eventId, Timestamp favoritDatum) {
        this.favoritId = favoritId;
        this.email = email;
        this.eventId = eventId;
        this.favoritDatum = favoritDatum;
    }

    public int getFavoritId() {
        return favoritId;
    }

    public void setFavoritId(int favoritId) {
        this.favoritId = favoritId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Timestamp getFavoritDatum() {
        return favoritDatum;
    }

    public void setFavoritDatum(Timestamp favoritDatum) {
        this.favoritDatum = favoritDatum;
    }
}