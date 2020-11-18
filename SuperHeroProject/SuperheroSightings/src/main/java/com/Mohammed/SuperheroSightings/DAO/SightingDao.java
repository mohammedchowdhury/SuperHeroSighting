/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author mohammedchowdhury
 */
public interface SightingDao{
    
    Sighting getSightingById(int SightingID);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    void deleteSightingById(int SightingID);
    
    List<Sighting> getAllSightingsOfHero(Hero hero);

    List<Sighting> getSightingsAtLocation(Location location);
    
    List<Sighting> getSightingsFromDate(LocalDate date);

    public List<Sighting> getTenLatestSighting();
    
}
