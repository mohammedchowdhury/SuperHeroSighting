package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import com.Mohammed.SuperheroSightings.DTO.Sighting;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
import com.Mohammed.SuperheroSightings.TestApplicationConfiguration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author mohammedchowdhury
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class HeroDaoImplTest {

    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @BeforeEach
    @AfterEach
    public void setUpAndTearDown() {

        //empty sighting
        List<Sighting> listOfAllSighting = sightingDao.getAllSightings();
        for (int i = 0; i < listOfAllSighting.size(); i++) {
            sightingDao.deleteSightingById(listOfAllSighting.get(i).getSightingID());
        }

        //empty Location
        List<Location> listOfAllLocation = locationDao.getAllLocations();
        for (int i = 0; i < listOfAllLocation.size(); i++) {
            locationDao.deleteLocationById(listOfAllLocation.get(i).getLocationId());
        }

        List<Hero> listOfHeros = heroDao.getAllHero();
        for (int i = 0; i < listOfHeros.size(); i++) {
            heroDao.deleteHero(listOfHeros.get(i).getHeroID());
        }

        List<Superpower> listOfSuperPower = superpowerDao.getAllSuperpowers();
        for (int i = 0; i < listOfSuperPower.size(); i++) {
            superpowerDao.deleteSuperpowerById(listOfSuperPower.get(i).getSuperPowerID());
        }

    }

    @Test
    public void testaddHero() {
        //arrange
        Hero hero = new Hero();
        hero.setHeroName("TestHero");
        hero.setHeroDescription("ThisIsATestHero");

        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        hero.setSuperPower(superpower);

        //act
        Hero returnedHero = heroDao.addHero(hero);
        hero.setHeroID(returnedHero.getHeroID());

        //assert
        assertNotNull(hero);
        assertNotNull(returnedHero);
        assertEquals(hero, returnedHero);

    }

    @Test
    public void testgetHeroById() {
        //arrange
        Hero hero = new Hero();
        hero.setHeroName("TestHero");
        hero.setHeroDescription("ThisIsATestHero");

        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        hero.setSuperPower(superpower);
        
        //act
        hero = heroDao.addHero(hero);
        Hero returnedHero = heroDao.getHeroById(hero.getHeroID());

        //assert
        assertNotNull(hero);
        assertNotNull(returnedHero);
        assertEquals(hero, returnedHero);

    }

    @Test
    public void testUpdateHero() {
        //arrange
        Hero hero = new Hero();
        hero.setHeroName("TestHero");
        hero.setHeroDescription("ThisIsATestHero");

        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        hero.setSuperPower(superpower);
       
        //act
        hero = heroDao.addHero(hero);
        hero.setHeroName("UpdatedName");
        heroDao.UpdateHero(hero);
        Hero returnedHero = heroDao.getHeroById(hero.getHeroID());

        //assert
        assertNotNull(hero);
        assertNotNull(returnedHero);
        assertEquals(hero, returnedHero);

    }

    @Test
    public void testDeleteHero() {
        //arrange
        Hero hero = new Hero();
        hero.setHeroName("TestHero");
        hero.setHeroDescription("ThisIsATestHero");

        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        hero.setSuperPower(superpower);
        
        //act
        hero = heroDao.addHero(hero);
        heroDao.deleteHero(hero.getHeroID());
        Hero returnedHero = heroDao.getHeroById(hero.getHeroID());

        //assert
        assertNotNull(hero);
        assertNull(returnedHero);
    }

    @Test
    public void testGetAllHero() {
        //arrange
        Hero hero = new Hero();
        hero.setHeroName("TestHero");
        hero.setHeroDescription("ThisIsATestHero");

        Hero hero2 = new Hero();
        hero2.setHeroName("TestHero");
        hero2.setHeroDescription("ThisIsATestHero");

        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        hero.setSuperPower(superpower);
        hero2.setSuperPower(superpower);

        //act
        hero = heroDao.addHero(hero);
        hero2 = heroDao.addHero(hero2);

        List<Hero> listOfHeros = heroDao.getAllHero();
        int size = 2, actualSize = listOfHeros.size();

        //assert
        assertNotNull(hero);
        assertNotNull(hero2);
        assertNotNull(listOfHeros);

        assertEquals(size, actualSize);
    }

    @Test
    public void testGetHeroForLocation() {
        //arrange
        Hero hero = new Hero();
        hero.setHeroName("TestHero");
        hero.setHeroDescription("ThisIsATestHero");

        Hero hero2 = new Hero();
        hero2.setHeroName("TestHero");
        hero2.setHeroDescription("ThisIsATestHero");

        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        hero.setSuperPower(superpower);
        hero2.setSuperPower(superpower);

        hero = heroDao.addHero(hero);
        hero2 = heroDao.addHero(hero2);

        Location location = new Location();
        location.setLocationName("TestLocationName");
        location.setLocationDescription("LocationDescription");
        location.setAddressInformation("NewYork");
        location.setLatitude("1234");
        location.setLongitude("1234");
        location = locationDao.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setHeroSighted(hero);
        sighting.setSightingDescription("Seen in NewYork eating MCDonalds");
        sighting.setLocation(location);
        sighting.setSightingDate(LocalDate.parse("2020-10-20"));
        sighting = sightingDao.addSighting(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setHeroSighted(hero2);
        sighting2.setSightingDescription("Seen in NewYork eating MCDonalds");
        sighting2.setLocation(location);
        sighting2.setSightingDate(LocalDate.parse("2020-10-20"));
        sighting2 = sightingDao.addSighting(sighting2);

        //act
        List<Hero> listOfHeros = heroDao.getHeroForLocation(location);
        int size = 2, actualSize = listOfHeros.size();

        //assert
        assertNotNull(hero);
        assertNotNull(hero2);
        assertNotNull(location);
        assertNotNull(sighting);
        assertNotNull(sighting2);
        assertNotNull(listOfHeros);

        assertEquals(size, actualSize);
        assertTrue(listOfHeros.contains(hero));
        assertTrue(listOfHeros.contains(hero2));
    }
}
