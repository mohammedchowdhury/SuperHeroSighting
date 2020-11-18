/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.DTO;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author mohammedchowdhury
 */
public class Location {

    private int LocationId;
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must be fewer than 50 characters")
    private String LocationName;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 300, message = "Description must be fewer than 300 characters")
    private String LocationDescription;

    @NotBlank(message = "Address must not be blank")
    @Size(max = 50, message = "Address must be fewer than 50 characters")
    private String AddressInformation;

    @NotBlank(message = "Latitude must not be blank")
    @Size(max = 15, message = "Latitude must be fewer than 15 characters")
    private String Latitude;

    @NotBlank(message = "Longitude must not be blank")
    @Size(max = 15, message = "Longitude must be fewer than 15 characters")
    private String Longitude;

    public Location(int LocationId, String LocationName, String LocationDescription, String AddressInformation, String Latitude, String Longitude) {
        this.LocationId = LocationId;
        this.LocationName = LocationName;
        this.LocationDescription = LocationDescription;
        this.AddressInformation = AddressInformation;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public Location(String LocationName, String LocationDescription, String AddressInformation, String Latitude, String Longitude) {
        this.LocationName = LocationName;
        this.LocationDescription = LocationDescription;
        this.AddressInformation = AddressInformation;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
    }

    public Location() {
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int LocationId) {
        this.LocationId = LocationId;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String LocationName) {
        this.LocationName = LocationName;
    }

    public String getLocationDescription() {
        return LocationDescription;
    }

    public void setLocationDescription(String LocationDescription) {
        this.LocationDescription = LocationDescription;
    }

    public String getAddressInformation() {
        return AddressInformation;
    }

    public void setAddressInformation(String AddressInformation) {
        this.AddressInformation = AddressInformation;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.LocationId;
        hash = 67 * hash + Objects.hashCode(this.LocationName);
        hash = 67 * hash + Objects.hashCode(this.LocationDescription);
        hash = 67 * hash + Objects.hashCode(this.AddressInformation);
        hash = 67 * hash + Objects.hashCode(this.Latitude);
        hash = 67 * hash + Objects.hashCode(this.Longitude);
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
        final Location other = (Location) obj;
        if (this.LocationId != other.LocationId) {
            return false;
        }
        if (!Objects.equals(this.LocationName, other.LocationName)) {
            return false;
        }
        if (!Objects.equals(this.LocationDescription, other.LocationDescription)) {
            return false;
        }
        if (!Objects.equals(this.AddressInformation, other.AddressInformation)) {
            return false;
        }
        if (!Objects.equals(this.Latitude, other.Latitude)) {
            return false;
        }
        if (!Objects.equals(this.Longitude, other.Longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "LocationId=" + LocationId + ", LocationName=" + LocationName + ", LocationDescription=" + LocationDescription + ", AddressInformation=" + AddressInformation + ", Latitude=" + Latitude + ", Longitude=" + Longitude + '}';
    }
}
