package com.Mohammed.SuperheroSightings.DTO;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 *
 * @author mohammedchowdhury
 */
public class Sighting {

    private int SightingID;
    @Past(message = "Date must not be in the future")
    @NotNull(message = "Date must not be blank")
    private LocalDate SightingDate;
    @NotBlank(message = "Description Name must not be blank")
    @Size(max = 300, message = "Description Name must be fewer than 300 characters and greater then 1 character")
    private String SightingDescription;
    @NotNull(message = "Location must not be blank")
    private Hero heroSighted;
    @NotNull(message = "Location must not be blank")
    private Location location;

    public Sighting(int SightingID, LocalDate SightingDate, String SightingDescription, Hero heroSighted, Location location) {
        this.SightingID = SightingID;
        this.SightingDate = SightingDate;
        this.SightingDescription = SightingDescription;
        this.heroSighted = heroSighted;
        this.location = location;
    }

    public Sighting(LocalDate SightingDate, String SightingDescription, Hero heroSighted, Location location) {
        this.SightingDate = SightingDate;
        this.SightingDescription = SightingDescription;
        this.heroSighted = heroSighted;
        this.location = location;
    }

    public Sighting() {
    }

    public int getSightingID() {
        return SightingID;
    }

    public void setSightingID(int SightingID) {
        this.SightingID = SightingID;
    }

    public LocalDate getSightingDate() {
        return SightingDate;
    }

    public void setSightingDate(LocalDate SightingDate) {
        this.SightingDate = SightingDate;
    }

    public String getSightingDescription() {
        return SightingDescription;
    }

    public void setSightingDescription(String SightingDescription) {
        this.SightingDescription = SightingDescription;
    }

    public Hero getHeroSighted() {
        return heroSighted;
    }

    public void setHeroSighted(Hero heroSighted) {
        this.heroSighted = heroSighted;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.SightingID;
        hash = 89 * hash + Objects.hashCode(this.SightingDate);
        hash = 89 * hash + Objects.hashCode(this.SightingDescription);
        hash = 89 * hash + Objects.hashCode(this.heroSighted);
        hash = 89 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.SightingID != other.SightingID) {
            return false;
        }
        if (!Objects.equals(this.SightingDescription, other.SightingDescription)) {
            return false;
        }
        if (!Objects.equals(this.SightingDate, other.SightingDate)) {
            return false;
        }
        if (!Objects.equals(this.heroSighted, other.heroSighted)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sighting{" + "SightingID=" + SightingID + ", SightingDate=" + SightingDate + ", SightingDescription=" + SightingDescription + ", heroSighted=" + heroSighted + ", location=" + location + '}';
    }
}
