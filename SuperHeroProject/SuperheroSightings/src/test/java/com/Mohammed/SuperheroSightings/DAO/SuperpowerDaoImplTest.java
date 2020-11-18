package com.Mohammed.SuperheroSightings.DAO;

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
public class SuperpowerDaoImplTest {

    @Autowired
    SuperpowerDao superpowerDao;

    @BeforeEach
    @AfterEach
    public void setUpAndTearDown() {
        List<Superpower> listOfSuperPowers = superpowerDao.getAllSuperpowers();
        for (Superpower superpower : listOfSuperPowers) {
            superpowerDao.deleteSuperpowerById(superpower.getSuperPowerID());
        }

    }

    @Test
    public void TestaddSuperpower() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        //act
        Superpower returnedSuperpower = superpowerDao.addSuperpower(superpower);
        superpower.setSuperPowerID(returnedSuperpower.getSuperPowerID());
        //assert
        assertEquals(superpower, returnedSuperpower);
        assertNotNull(superpower);
        assertNotNull(returnedSuperpower);
    }

    @Test
    public void TestgetSuperpowerByID() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        //act
        superpower = superpowerDao.addSuperpower(superpower);
        Superpower returnedSuperpower = superpowerDao.getSuperpowerByID(superpower.getSuperPowerID());
        //assert
        assertEquals(superpower, returnedSuperpower);
        assertNotNull(superpower);
        assertNotNull(returnedSuperpower);
    }

    @Test
    public void TestupdateSuperpower() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        //act
        superpower = superpowerDao.addSuperpower(superpower);
        superpower.setSuperPowerName("Crying");
        superpowerDao.updateSuperpower(superpower);

        Superpower returnedSuperpower = superpowerDao.getSuperpowerByID(superpower.getSuperPowerID());
        //assert
        assertEquals(superpower, returnedSuperpower);
        assertNotNull(superpower);
        assertNotNull(returnedSuperpower);
    }

    @Test
    public void TestdeleteSuperpowerById() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");
        //act
        superpower = superpowerDao.addSuperpower(superpower);
        superpowerDao.deleteSuperpowerById(superpower.getSuperPowerID());
        Superpower returnedSuperpower = superpowerDao.getSuperpowerByID(superpower.getSuperPowerID());
        //assert

        assertNull(returnedSuperpower);
        assertNotNull(superpower);
    }

    @Test
    public void TestgetAllSuperpowers() {
        //arrange
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName("Flying");

        Superpower superpower2 = new Superpower();
        superpower2.setSuperPowerName("Crying");
        //act
        superpower = superpowerDao.addSuperpower(superpower);
        superpower2 = superpowerDao.addSuperpower(superpower2);

        List<Superpower> listOfSuperPowers = superpowerDao.getAllSuperpowers();
        int size = 2, actualSize = listOfSuperPowers.size();

        //assert
        assertNotNull(superpower);
        assertNotNull(superpower2);
        assertNotNull(listOfSuperPowers);
        assertEquals(size, actualSize);
        assertTrue(listOfSuperPowers.contains(superpower));
        assertTrue(listOfSuperPowers.contains(superpower2));
    }

}
