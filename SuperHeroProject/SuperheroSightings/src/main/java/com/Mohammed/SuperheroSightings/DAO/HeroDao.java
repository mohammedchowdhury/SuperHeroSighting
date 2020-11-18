package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import java.util.List;

/**
 *
 * @author mohammedchowdhury
 */
public interface HeroDao {

    public Hero addHero(Hero hero);

    public Hero getHeroById(int HeroID);

    public void deleteHero(int HeroId);

    public void UpdateHero(Hero heroToUpdate);

    public List<Hero> getAllHero();

    //The system must be able to report all of the superheroes sighted at a particular location.
    public List<Hero> getHeroForLocation(Location location);
    

  

}






//Sighting dao
//The system must be able to report all of the locations where a particular superhero has been seen. 
// getAllLocationForSuperHero(Superhero superhero); 

//Sighting dao
//The system must be able to report all sightings (hero and location) for a particular date.

