package com.Mohammed.SuperheroSightings.DAO;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
import java.util.List;

/**
 *
 * @author mohammedchowdhury
 */
public interface SuperpowerDao {

    Superpower getSuperpowerByID(int SuperPowerID);

    List<Superpower> getAllSuperpowers();

    Superpower addSuperpower(Superpower superpower);

    void updateSuperpower(Superpower superpower);

    void deleteSuperpowerById(int SuperPowerID);

}
