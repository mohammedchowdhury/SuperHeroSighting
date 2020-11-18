package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import java.util.List;

/**
 *
 * @author mohammedchowdhury
 */
public interface OrganizationDao {

    Organization addOrganization(Organization organization);

    Organization getOrganizationByID(int OrganizationID);

    List<Organization> getAllOrganizations();

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int OrganizationID);

    List<Organization> getOrganizationsByHero(Hero hero);

    //public Organization getOrganizationByLocation(Location location);

}
