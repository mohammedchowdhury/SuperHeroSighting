package com.Mohammed.SuperheroSightings.DAO;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
import com.Mohammed.SuperheroSightings.TestApplicationConfiguration;
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
public class OrganizationDaoImplTest {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    LocationDao locationDao;

    @BeforeEach
    @AfterEach
    public void setUpAndTearDown() {
        List<Organization> listOfOrganization = organizationDao.getAllOrganizations();

        List<Hero> listOfHeros = heroDao.getAllHero();
        for (int i = 0; i < listOfHeros.size(); i++) {
            heroDao.deleteHero(listOfHeros.get(i).getHeroID());
        }

        List<Superpower> listOfSuperPower = superpowerDao.getAllSuperpowers();
        for (int i = 0; i < listOfSuperPower.size(); i++) {
            superpowerDao.deleteSuperpowerById(listOfSuperPower.get(i).getSuperPowerID());
        }

        for (int i = 0; i < listOfOrganization.size(); i++) {
            organizationDao.deleteOrganizationById(listOfOrganization.get(i).getOrganizationID());
        }

        List<Location> listOfLocation = locationDao.getAllLocations();
        for (int i = 0; i < listOfLocation.size(); i++) {
            locationDao.deleteLocationById(listOfLocation.get(i).getLocationId());
        }
    }
    
    @Test
    public void TestaddOrganization() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero");
        hero.setHeroDescription("Test Description");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setLocationName("TestLocation");
        location.setLocationDescription("TestDescription");
        location.setAddressInformation("Address Information");
        location.setLatitude("123455");
        location.setLongitude("123455");

        location = locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setLocation(location);
        organization.setContactInformationPhone("123-456-7890");
        organization.setOrganizationDescription("TestOrganization");
        organization.setOrganizationName("TheSoftwareGuild");
        organization.setListOfHeros(heroDao.getAllHero());
        //act
        Organization returnedOrganization = organizationDao.addOrganization(organization);
        organization.setOrganizationID(returnedOrganization.getOrganizationID());
        //assert
        assertNotNull(returnedOrganization);
        assertNotNull(organization);
        assertEquals(returnedOrganization, organization);
    }

    @Test
    public void TestGetOrganizationByID() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero");
        hero.setHeroDescription("Test Description");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setLocationName("TestLocation");
        location.setLocationDescription("TestDescription");
        location.setAddressInformation("Address Information");
        location.setLatitude("123455");
        location.setLongitude("123455");

        location = locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setLocation(location);
        organization.setContactInformationPhone("123-456-7890");
        organization.setOrganizationDescription("TestOrganization");
        organization.setOrganizationName("TheSoftwareGuild");
        organization.setListOfHeros(heroDao.getAllHero());
        //act
        organization = organizationDao.addOrganization(organization);
        Organization returnedOrganization = organizationDao.getOrganizationByID(organization.getOrganizationID());

        //assert
        assertNotNull(returnedOrganization);
        assertNotNull(organization);
        assertEquals(returnedOrganization, organization);
    }

    @Test
    public void TestUpdateOrganization() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero");
        hero.setHeroDescription("Test Description");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setLocationName("TestLocation");
        location.setLocationDescription("TestDescription");
        location.setAddressInformation("Address Information");
        location.setLatitude("123455");
        location.setLongitude("123455");

        location = locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setLocation(location);
        organization.setContactInformationPhone("123-456-7890");
        organization.setOrganizationDescription("TestOrganization");
        organization.setOrganizationName("TheSoftwareGuild");
        organization.setListOfHeros(heroDao.getAllHero());
        //act
        organization = organizationDao.addOrganization(organization);
        organization.setOrganizationDescription("ChangedDescription");
        organizationDao.updateOrganization(organization);

        Organization returnedOrganization = organizationDao.getOrganizationByID(organization.getOrganizationID());

        //assert
        assertNotNull(returnedOrganization);
        assertNotNull(organization);
        assertEquals(returnedOrganization, organization);
    }

    @Test
    public void TestdeleteOrganizationById() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero");
        hero.setHeroDescription("Test Description");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setLocationName("TestLocation");
        location.setLocationDescription("TestDescription");
        location.setAddressInformation("Address Information");
        location.setLatitude("123455");
        location.setLongitude("123455");

        location = locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setLocation(location);
        organization.setContactInformationPhone("123-456-7890");
        organization.setOrganizationDescription("TestOrganization");
        organization.setOrganizationName("TheSoftwareGuild");
        organization.setListOfHeros(heroDao.getAllHero());
        //act
        organization = organizationDao.addOrganization(organization);
        organizationDao.deleteOrganizationById(organization.getOrganizationID());

        Organization returnedOrganization = organizationDao.getOrganizationByID(organization.getOrganizationID());
        //assert
        assertNull(returnedOrganization);
        assertNotNull(organization);
    }

    @Test
    public void TestgetAllOrganizations() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero");
        hero.setHeroDescription("Test Description");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setLocationName("TestLocation");
        location.setLocationDescription("TestDescription");
        location.setAddressInformation("Address Information");
        location.setLatitude("123455");
        location.setLongitude("123455");

        location = locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setLocation(location);
        organization.setContactInformationPhone("123-456-7890");
        organization.setOrganizationDescription("TestOrganization");
        organization.setOrganizationName("TheSoftwareGuild");
        organization.setListOfHeros(heroDao.getAllHero());

        Organization organization2 = new Organization();
        organization2.setLocation(location);
        organization2.setContactInformationPhone("123-456-7890");
        organization2.setOrganizationDescription("TestOrganization");
        organization2.setOrganizationName("TheSoftwareGuild");
        organization2.setListOfHeros(heroDao.getAllHero());
        //act
        organization = organizationDao.addOrganization(organization);
        organization2 = organizationDao.addOrganization(organization2);
        List<Organization> listOfOrganization = organizationDao.getAllOrganizations();
        int size = 2, actualSize = listOfOrganization.size();
        //assert
        assertNotNull(organization);
        assertNotNull(organization2);
        assertNotNull(actualSize);
        assertEquals(size, actualSize);
        assertTrue(listOfOrganization.contains(organization));
        assertTrue(listOfOrganization.contains(organization2));
    }
    
    @Test
    public void TestGetOrganizationsByHero() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        superpower = superpowerDao.addSuperpower(superpower);

        Hero hero = new Hero();
        hero.setHeroName("Test Hero");
        hero.setHeroDescription("Test Description");
        hero.setSuperPower(superpower);
        hero = heroDao.addHero(hero);

        Location location = new Location();
        location.setLocationName("TestLocation");
        location.setLocationDescription("TestDescription");
        location.setAddressInformation("Address Information");
        location.setLatitude("123455");
        location.setLongitude("123455");

        location = locationDao.addLocation(location);

        Organization organization = new Organization();
        organization.setLocation(location);
        organization.setContactInformationPhone("123-456-7890");
        organization.setOrganizationDescription("TestOrganization");
        organization.setOrganizationName("TheSoftwareGuild");
        organization.setListOfHeros(heroDao.getAllHero());

        Organization organization2 = new Organization();
        organization2.setLocation(location);
        organization2.setContactInformationPhone("123-456-7890");
        organization2.setOrganizationDescription("TestOrganization2");
        organization2.setOrganizationName("TheSoftwareGuild2");
        organization2.setListOfHeros(heroDao.getAllHero());
        //act
        organization = organizationDao.addOrganization(organization);
        organization2 = organizationDao.addOrganization(organization2);
        List<Organization> listOfOrganization = organizationDao.getOrganizationsByHero(hero);
        int size = 2, actualSize = listOfOrganization.size();
        //assert
        assertNotNull(organization);
        assertNotNull(organization2);
        assertNotNull(actualSize);
        assertEquals(size, actualSize);
        assertTrue(listOfOrganization.contains(organization));
        assertTrue(listOfOrganization.contains(organization2));
    }
}