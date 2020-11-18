/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DTO.Location;
import java.util.List;

/**
 *
 * @author mohammedchowdhury
 */
public interface LocationDao {

    Location getLocationById(int LocationID);

    Location addLocation(Location location);
    
    List<Location> getAllLocations();

    void updateLocation(Location location);

    void deleteLocationById(int LocationID);

}


