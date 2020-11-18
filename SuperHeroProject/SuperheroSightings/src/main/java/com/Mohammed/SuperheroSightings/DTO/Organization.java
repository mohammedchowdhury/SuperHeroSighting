/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mohammedchowdhury
 */
public class Organization {

    private int OrganizationID;
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must be fewer than 50 characters")
    private String OrganizationName;
    @NotBlank(message = "Description must not be blank")
    @Size(max = 300, message = "Description must be fewer than 300 characters")
    private String OrganizationDescription;
    @NotBlank(message = "Phone must not be blank")
    @Size(max = 15, message = "Phone must be fewer than 15 characters")
    private String ContactInformationPhone;
    @NotNull(message = "Location must not be blank")
    private Location location;
    @NotNull(message = "Must select a Hero. Hero must not be blank")
    private List<Hero> listOfHeros = new ArrayList<Hero>();

    public Organization() {

    }

    public int getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(int OrganizationID) {
        this.OrganizationID = OrganizationID;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String OrganizationName) {
        this.OrganizationName = OrganizationName;
    }

    public String getOrganizationDescription() {
        return OrganizationDescription;
    }

    public void setOrganizationDescription(String OrganizationDescription) {
        this.OrganizationDescription = OrganizationDescription;
    }

    public String getContactInformationPhone() {
        return ContactInformationPhone;
    }

    public void setContactInformationPhone(String ContactInformationPhone) {
        this.ContactInformationPhone = ContactInformationPhone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Hero> getListOfHeros() {
        return listOfHeros;
    }

    public void setListOfHeros(List<Hero> listOfHeros) {
        this.listOfHeros = listOfHeros;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.OrganizationID;
        hash = 37 * hash + Objects.hashCode(this.OrganizationName);
        hash = 37 * hash + Objects.hashCode(this.OrganizationDescription);
        hash = 37 * hash + Objects.hashCode(this.ContactInformationPhone);
        hash = 37 * hash + Objects.hashCode(this.location);
        hash = 37 * hash + Objects.hashCode(this.listOfHeros);
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
        final Organization other = (Organization) obj;
        if (this.OrganizationID != other.OrganizationID) {
            return false;
        }
        if (!Objects.equals(this.OrganizationName, other.OrganizationName)) {
            return false;
        }
        if (!Objects.equals(this.OrganizationDescription, other.OrganizationDescription)) {
            return false;
        }
        if (!Objects.equals(this.ContactInformationPhone, other.ContactInformationPhone)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.listOfHeros, other.listOfHeros)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Organization{" + "OrganizationID=" + OrganizationID + ", OrganizationName=" + OrganizationName + ", OrganizationDescription=" + OrganizationDescription + ", ContactInformationPhone=" + ContactInformationPhone + ", location=" + location + ", listOfHeros=" + listOfHeros + '}';
    }
}
