/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.DTO;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mohammedchowdhury
 */
public class Hero {
    private int HeroID;
    @NotBlank(message = "Name must not be blank")
    @Size(max = 50, message = "Name must be fewer than 50 characters")
    private String HeroName;
    @NotBlank(message = "Descriptio must not be blank")
    @Size(max = 300, message = "Descriptio must be fewer than 300 characters")
    private String HeroDescription;
    @NotNull(message = "Must Select a Superpower")
    private Superpower superPower;
    // List<Organization> listOfOrganization = new ArrayList<Organization>();

    public Hero(int HeroID, String HeroName, String HeroDescription, Superpower superPower) {
        this.HeroID = HeroID;
        this.HeroName = HeroName;
        this.HeroDescription = HeroDescription;
        this.superPower = superPower;
        // this.listOfOrganization = listOfOrganization;
    }

    public Hero(String HeroName, String HeroDescription, Superpower superPower) {
        this.HeroName = HeroName;
        this.HeroDescription = HeroDescription;
        this.superPower = superPower;
        //  this.listOfOrganization = listOfOrganization;
    }

    public Hero() {

    }

    public int getHeroID() {
        return HeroID;
    }

    public void setHeroID(int HeroID) {
        this.HeroID = HeroID;
    }

    public String getHeroName() {
        return HeroName;
    }

    public void setHeroName(String HeroName) {
        this.HeroName = HeroName;
    }

    public String getHeroDescription() {
        return HeroDescription;
    }

    public void setHeroDescription(String HeroDescription) {
        this.HeroDescription = HeroDescription;
    }

    public Superpower getSuperPower() {
        return superPower;
    }

    public void setSuperPower(Superpower superPower) {
        this.superPower = superPower;
    }

//    public List<Organization> getListOfOrganization() {
//        return listOfOrganization;
//    }
//
//    public void setListOfOrganization(List<Organization> listOfOrganization) {
//        this.listOfOrganization = listOfOrganization;
//    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.HeroID;
        hash = 29 * hash + Objects.hashCode(this.HeroName);
        hash = 29 * hash + Objects.hashCode(this.HeroDescription);
        hash = 29 * hash + Objects.hashCode(this.superPower);
        // hash = 29 * hash + Objects.hashCode(this.listOfOrganization);
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
        final Hero other = (Hero) obj;
        if (this.HeroID != other.HeroID) {
            return false;
        }
        if (!Objects.equals(this.HeroName, other.HeroName)) {
            return false;
        }
        if (!Objects.equals(this.HeroDescription, other.HeroDescription)) {
            return false;
        }
        if (!Objects.equals(this.superPower, other.superPower)) {
            return false;
        }
//        if (!Objects.equals(this.listOfOrganization, other.listOfOrganization)) {
//            return false;
//        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "Hero{" + "HeroID=" + HeroID + ", HeroName=" + HeroName + ", HeroDescription=" + HeroDescription + ", superPower=" + superPower + ", listOfOrganization=" + listOfOrganization + '}';
//    }
    @Override
    public String toString() {
        return "Hero{" + "HeroID=" + HeroID + ", HeroName=" + HeroName + ", HeroDescription=" + HeroDescription + ", superPower=" + superPower + '}';
    }

}
