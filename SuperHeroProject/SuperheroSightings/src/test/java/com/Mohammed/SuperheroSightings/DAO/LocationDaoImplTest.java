package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DTO.Location;
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
public class LocationDaoImplTest {

    @Autowired
    LocationDao locationDao;

    @BeforeEach
    @AfterEach
    public void setUpAndTearDown() {
        List<Location> listOfLocation = locationDao.getAllLocations();
        for (int i = 0; i < listOfLocation.size(); i++) {
            locationDao.deleteLocationById(listOfLocation.get(i).getLocationId());
        }
    }

    @Test
    public void TestaddLocation() {
        //arrange 
        Location locationTest = new Location();

        locationTest.setLocationName("LocationTestName");
        locationTest.setLocationDescription("Location by the sea");
        locationTest.setAddressInformation("Test Address information");
        locationTest.setLatitude("Test Latitude");
        locationTest.setLongitude("Test Longitude");

        //act
        Location returnedLocation = locationDao.addLocation(locationTest);
        locationTest.setLocationId(returnedLocation.getLocationId());
        //assert
        assertEquals(locationTest, returnedLocation);
        assertNotNull(returnedLocation);
        assertNotNull(locationTest);
    }

    @Test
    public void TestgetLocationById() {
        //arrange 
        Location locationTest = new Location();

        locationTest.setLocationName("LocationTestName");
        locationTest.setLocationDescription("Location by the sea");
        locationTest.setAddressInformation("Test Address information");
        locationTest.setLatitude("Test Latitude");
        locationTest.setLongitude("Test Longitude");

        //act
        locationTest = locationDao.addLocation(locationTest);
        Location returnedLocation = locationDao.getLocationById(locationTest.getLocationId());

        //assert
        assertEquals(locationTest, returnedLocation);
        assertNotNull(returnedLocation);
        assertNotNull(locationTest);
    }

    @Test
    public void TestupdateLocation() {
        //arrange 
        Location locationTest = new Location();

        locationTest.setLocationName("LocationTestName");
        locationTest.setLocationDescription("Location by the sea");
        locationTest.setAddressInformation("Test Address information");
        locationTest.setLatitude("Test Latitude");
        locationTest.setLongitude("Test Longitude");

        //act
        locationTest = locationDao.addLocation(locationTest);
        locationTest.setLocationName("Updated Test");
        locationDao.updateLocation(locationTest);
        Location returnedLocation = locationDao.getLocationById(locationTest.getLocationId());

        //assert
        assertEquals(locationTest, returnedLocation);
        assertNotNull(returnedLocation);
        assertNotNull(locationTest);
    }

    @Test
    public void TestdeleteLocationById() {
        //arrange 
        Location locationTest = new Location();

        locationTest.setLocationName("LocationTestName");
        locationTest.setLocationDescription("Location by the sea");
        locationTest.setAddressInformation("Test Address information");
        locationTest.setLatitude("Test Latitude");
        locationTest.setLongitude("Test Longitude");

        //act
        locationTest = locationDao.addLocation(locationTest);
        locationDao.deleteLocationById(locationTest.getLocationId());
        Location returnedLocation = locationDao.getLocationById(locationTest.getLocationId());

        //assert
        assertNotNull(locationTest);
        assertNull(returnedLocation);
    }

    @Test
    public void TestgetAllLocations() {
        //arrange 
        Location locationTest = new Location();
        locationTest.setLocationName("LocationTestName");
        locationTest.setLocationDescription("Location by the sea");
        locationTest.setAddressInformation("Test Address information");
        locationTest.setLatitude("Test Latitude");
        locationTest.setLongitude("Test Longitude");

        Location locationTest2 = new Location();
        locationTest2.setLocationName("LocationTestName2");
        locationTest2.setLocationDescription("Location by the sea2");
        locationTest2.setAddressInformation("Test Address information2");
        locationTest2.setLatitude("Test Latitude2");
        locationTest2.setLongitude("Test Longitude2");

        //act
        locationTest = locationDao.addLocation(locationTest);
        locationTest2 = locationDao.addLocation(locationTest2);

        List<Location> listOfLocation = locationDao.getAllLocations();
        int size = 2;
        int actualLisze = listOfLocation.size();

        //assert
        assertEquals(size, actualLisze);
        assertNotNull(locationTest);
        assertNotNull(locationTest2);
        assertTrue(listOfLocation.contains(locationTest));
        assertTrue(listOfLocation.contains(locationTest2));
    }
}
