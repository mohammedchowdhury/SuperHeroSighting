package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Sighting;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
import com.Mohammed.SuperheroSightings.TestApplicationConfiguration;
import java.time.LocalDate;
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
public class SightingDaoImplTest {

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    HeroDao heroDao;

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

        //empty Hero
        List<Hero> listOfAllHeros = heroDao.getAllHero();
        for (int i = 0; i < listOfAllHeros.size(); i++) {
            heroDao.deleteHero(listOfAllHeros.get(i).getHeroID());
        }

        //empty superpower
        List<Superpower> listOfAllSuperpowers = superpowerDao.getAllSuperpowers();
        for (int i = 0; i < listOfAllSuperpowers.size(); i++) {
            superpowerDao.deleteSuperpowerById(listOfAllSuperpowers.get(i).getSuperPowerID());
        }

        //empty Location
        List<Location> listOfAllLocation = locationDao.getAllLocations();
        for (int i = 0; i < listOfAllLocation.size(); i++) {
            locationDao.deleteLocationById(listOfAllLocation.get(i).getLocationId());
        }
    }

    @Test
    public void TestAddSighting() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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
        //act
        Sighting returnedSighting = sightingDao.addSighting(sighting);
        sighting.setSightingID(returnedSighting.getSightingID());
        //assert
        assertNotNull(sighting);
        assertNotNull(returnedSighting);
        assertEquals(sighting, returnedSighting);
    }

    @Test
    public void TestGetSightingById() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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
        //act

        sighting = sightingDao.addSighting(sighting);
        Sighting returnedSighting = sightingDao.getSightingById(sighting.getSightingID());

        //assert
        assertNotNull(sighting);
        assertNotNull(returnedSighting);
        assertEquals(sighting, returnedSighting);
    }

    @Test
    public void TestUpdateSighting() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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
        //act

        sighting = sightingDao.addSighting(sighting);
        sighting.setSightingDescription("ChangingDescription");
        sightingDao.updateSighting(sighting);
        Sighting returnedSighting = sightingDao.getSightingById(sighting.getSightingID());

        //assert
        assertNotNull(sighting);
        assertNotNull(returnedSighting);
        assertEquals(sighting, returnedSighting);
    }

    @Test
    public void TestDeleteSightingById() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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
        //act

        sighting = sightingDao.addSighting(sighting);
        Sighting sighting1 = sightingDao.getSightingById(sighting.getSightingID());
        sightingDao.deleteSightingById(sighting.getSightingID());
        Sighting returnedSighting = sightingDao.getSightingById(sighting.getSightingID());

        //assert
        assertNotNull(sighting);
        assertNull(returnedSighting);
        assertEquals(sighting1, sighting);

    }

    @Test
    public void TestGetAllSightings() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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

        Sighting sighting2 = new Sighting();
        sighting2.setHeroSighted(hero);
        sighting2.setSightingDescription("Seen in NewYork eating MCDonalds Location2");
        sighting2.setLocation(location);
        sighting2.setSightingDate(LocalDate.parse("2020-10-20"));
        //act

        sighting = sightingDao.addSighting(sighting);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> listOfSightingByLocation = sightingDao.getAllSightings();
        int size = 2, actualSize = listOfSightingByLocation.size();

        //assert
        assertNotNull(sighting);
        assertNotNull(sighting2);
        assertNotNull(listOfSightingByLocation);
        assertTrue(listOfSightingByLocation.contains(sighting));
        assertTrue(listOfSightingByLocation.contains(sighting2));
        assertEquals(size, actualSize);

    }

    @Test
    public void TestGetAllSightingsOfHero() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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

        Sighting sighting2 = new Sighting();
        sighting2.setHeroSighted(hero);
        sighting2.setSightingDescription("Seen in NewYork eating MCDonalds Location2");
        sighting2.setLocation(location);
        sighting2.setSightingDate(LocalDate.parse("2020-10-20"));
        //act

        sighting = sightingDao.addSighting(sighting);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> listOfSightingByLocation = sightingDao.getAllSightingsOfHero(hero);
        int size = 2, actualSize = listOfSightingByLocation.size();

        //assert
        assertNotNull(sighting);
        assertNotNull(sighting2);
        assertNotNull(listOfSightingByLocation);
        assertTrue(listOfSightingByLocation.contains(sighting));
        assertTrue(listOfSightingByLocation.contains(sighting2));
        assertEquals(size, actualSize);

    }

    @Test
    public void TestgetSightingsAtLocation() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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

        Sighting sighting2 = new Sighting();
        sighting2.setHeroSighted(hero);
        sighting2.setSightingDescription("Seen in NewYork eating MCDonalds Location2");
        sighting2.setLocation(location);
        sighting2.setSightingDate(LocalDate.parse("2020-10-20"));
        //act

        sighting = sightingDao.addSighting(sighting);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> listOfSightingByLocation = sightingDao.getSightingsAtLocation(location);
        int size = 2, actualSize = listOfSightingByLocation.size();

        //assert
        assertNotNull(sighting);
        assertNotNull(sighting2);
        assertNotNull(listOfSightingByLocation);
        assertTrue(listOfSightingByLocation.contains(sighting));
        assertTrue(listOfSightingByLocation.contains(sighting2));
        assertEquals(size, actualSize);

    }

    @Test
    public void TestgetSightingsFromDate() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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

        Sighting sighting2 = new Sighting();
        sighting2.setHeroSighted(hero);
        sighting2.setSightingDescription("Seen in NewYork eating MCDonalds Location2");
        sighting2.setLocation(location);
        sighting2.setSightingDate(LocalDate.parse("2020-10-20"));
        //act

        sighting = sightingDao.addSighting(sighting);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> listOfSightingByLocation = sightingDao.getSightingsFromDate(LocalDate.parse("2020-10-20"));
        int size = 2, actualSize = listOfSightingByLocation.size();

        //assert
        assertNotNull(sighting);
        assertNotNull(sighting2);
        assertNotNull(listOfSightingByLocation);
        assertTrue(listOfSightingByLocation.contains(sighting));
        assertTrue(listOfSightingByLocation.contains(sighting2));
        assertEquals(size, actualSize);

    }

    @Test
    public void TestgetTenLatestSighting() {
        //arrange 
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroDescription("TestDescription");
        hero.setHeroName("TestName");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

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

        Sighting sighting2 = new Sighting();
        sighting2.setHeroSighted(hero);
        sighting2.setSightingDescription("Seen in NewYork eating MCDonalds Location2");
        sighting2.setLocation(location);
        sighting2.setSightingDate(LocalDate.parse("2020-10-20"));
        //act

        sighting = sightingDao.addSighting(sighting);
        sighting2 = sightingDao.addSighting(sighting2);

        List<Sighting> listOfSightingByLocation = sightingDao.getTenLatestSighting();
        int size = 2, actualSize = listOfSightingByLocation.size();

        //assert
        assertNotNull(sighting);
        assertNotNull(sighting2);
        assertNotNull(listOfSightingByLocation);
        assertTrue(listOfSightingByLocation.contains(sighting));
        assertTrue(listOfSightingByLocation.contains(sighting2));
        assertEquals(size, actualSize);

    }
}