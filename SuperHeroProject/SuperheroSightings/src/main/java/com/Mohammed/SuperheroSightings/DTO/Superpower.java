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
public class Superpower {

    private int SuperPowerID;
    @NotBlank(message = "Superpower Name must not be blank")
    @Size(max = 50, message = "Superpower Name must be fewer than 50 characters and greater then 1 character")
    private String SuperPowerName;

    public Superpower(int SuperPowerID, String SuperPowerName) {
        this.SuperPowerID = SuperPowerID;
        this.SuperPowerName = SuperPowerName;
    }

    public Superpower(String SuperPowerName) {
        this.SuperPowerName = SuperPowerName;
    }

    public Superpower() {

    }

    public int getSuperPowerID() {
        return SuperPowerID;
    }

    public void setSuperPowerID(int SuperPowerID) {
        this.SuperPowerID = SuperPowerID;
    }

    public String getSuperPowerName() {
        return SuperPowerName;
    }

    public void setSuperPowerName(String SuperPowerName) {
        this.SuperPowerName = SuperPowerName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.SuperPowerID;
        hash = 47 * hash + Objects.hashCode(this.SuperPowerName);
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
        final Superpower other = (Superpower) obj;
        if (this.SuperPowerID != other.SuperPowerID) {
            return false;
        }
        if (!Objects.equals(this.SuperPowerName, other.SuperPowerName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Superpower{" + "SuperPowerID=" + SuperPowerID + ", SuperPowerName=" + SuperPowerName + '}';
    }
}
