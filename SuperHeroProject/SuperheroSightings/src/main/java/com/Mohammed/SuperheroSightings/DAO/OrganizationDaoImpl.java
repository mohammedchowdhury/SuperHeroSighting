package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DAO.HeroDaoImpl.HeroMapper;
import com.Mohammed.SuperheroSightings.DAO.LocationDaoImpl.LocationMapper;
import com.Mohammed.SuperheroSightings.DAO.SuperpowerDaoImpl.SuperpowerMapper;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mohammedchowdhury
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    //done 
    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO ORGANIZATION(OrganizationName, OrganizationDescription, ContactInformationPhone, LocationID) "
                + " VALUES(?,?,?,?) ; ";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getContactInformationPhone(),
                organization.getLocation().getLocationId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID() ; ", Integer.class);
        organization.setOrganizationID(newId);
        insertHeroOrganization(organization);
        return organization;
    }

    //done
    private void insertHeroOrganization(Organization organization) {
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO "
                + "HERO_ORGANIZATION(HeroID, OrganizationID) VALUES(?,?) ; ";
        try {
            for (Hero hero : organization.getListOfHeros()) {
                jdbc.update(INSERT_HERO_ORGANIZATION,
                        hero.getHeroID(),
                        organization.getOrganizationID());
            }
        } catch (NullPointerException ex) {
        }
    }

    @Override
    @Transactional
    public Organization getOrganizationByID(int OrganizationID) {
        final String GET_ORGANIZATION_BY_ID = "SELECT ORGANIZATION.* FROM ORGANIZATION WHERE OrganizationID = ? ; ";
        try {
            Organization organization = jdbc.queryForObject(GET_ORGANIZATION_BY_ID, new OrganizationMapper(), OrganizationID);
            getHerosAndLocationForOrganization(organization);
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private void getHerosAndLocationForOrganization(Organization organization) {
        final String SELECT_LOCATION_FOR_ORGANIZATION = "SELECT LOCATION.* from LOCATION "
                + "JOIN ORGANIZATION ON ORGANIZATION.LocationID = LOCATION.LocationID WHERE ORGANIZATION.OrganizationID =? ; ";
        Location location = jdbc.queryForObject(SELECT_LOCATION_FOR_ORGANIZATION, new LocationMapper(), organization.getOrganizationID());
        organization.setLocation(location);

        final String GET_HEROES_BY_ORGANIZATION = "SELECT HERO.* "
                + " FROM HERO JOIN HERO_ORGANIZATION ON HERO.HeroID = HERO_ORGANIZATION.HeroID "
                + " WHERE HERO_ORGANIZATION.OrganizationID = ? ; ";
        List<Hero> heroes = jdbc.query(GET_HEROES_BY_ORGANIZATION,
                new HeroMapper(), organization.getOrganizationID());

        if (heroes.isEmpty()) {
            heroes = null;
        } else {
            for (Hero hero : heroes) {
                final String SELECT_SUPERPOWER_FOR_HERO = "SELECT SUPERPOWER.* FROM SUPERPOWER "
                        + "JOIN HERO ON HERO.SuperPowerID = SUPERPOWER.SuperPowerID WHERE HERO.HeroID =? ; ";
                Superpower thisPower = jdbc.queryForObject(SELECT_SUPERPOWER_FOR_HERO, new SuperpowerMapper(), hero.getHeroID());
                hero.setSuperPower(thisPower);
            }
        }
        organization.setListOfHeros(heroes);
    }

    @Override
    @Transactional
    public List<Organization> getAllOrganizations() {
        final String GET_ALL_ORGANIZATIONS = "SELECT * FROM ORGANIZATION ; ";
        List<Organization> organizations = jdbc.query(GET_ALL_ORGANIZATIONS, new OrganizationMapper());
        for (Organization organization : organizations) {
            getHerosAndLocationForOrganization(organization);
        }
        return organizations;
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE ORGANIZATION SET OrganizationName = ?, OrganizationDescription = ?, "
                + "ContactInformationPhone = ?, LocationID = ? WHERE OrganizationID = ? ; ";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getContactInformationPhone(),
                organization.getLocation().getLocationId(),
                organization.getOrganizationID());

        final String DELETE_HERO_ORGANIZATION = "DELETE FROM HERO_ORGANIZATION WHERE HERO_ORGANIZATION.OrganizationID = ? ; ";
        jdbc.update(DELETE_HERO_ORGANIZATION, organization.getOrganizationID());
        insertHeroOrganization(organization);
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int OrganizationID) {
        final String DELETE_ORGANIZATION_FROM_HERO = "DELETE FROM HERO_ORGANIZATION WHERE OrganizationID = ?";
        jdbc.update(DELETE_ORGANIZATION_FROM_HERO, OrganizationID);

        final String DELETE_ORGANIZATION = "DELETE FROM ORGANIZATION WHERE OrganizationID = ?";
        jdbc.update(DELETE_ORGANIZATION, OrganizationID);
    }

    @Override
    @Transactional
    public List<Organization> getOrganizationsByHero(Hero hero) {
        final String SELECT_ORGANIZATIONS_BY_HERO = "SELECT ORGANIZATION.* FROM ORGANIZATION "
                + " JOIN HERO_ORGANIZATION ON HERO_ORGANIZATION.OrganizationID = ORGANIZATION.OrganizationID "
                + " WHERE HERO_ORGANIZATION.HeroID = ? ";

        List<Organization> organizations = jdbc.query(SELECT_ORGANIZATIONS_BY_HERO, new OrganizationMapper(), hero.getHeroID());
        for (Organization organization : organizations) {
            getHerosAndLocationForOrganization(organization);
        }
        return organizations;
    }

//    @Override
//    public Organization getOrganizationByLocation(Location location) {
//        final String SELECT_ORGANIZATION_BY_LOCATION = "SELECT * FROM ORGANIZATION WHERE LocationID = ? ; ";
//        try {
//            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_LOCATION, new OrganizationMapper(), location.getLocationId());
//            return organization;
//        } catch (NullPointerException ex) {
//            return null;
//        }
//    }
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationID(rs.getInt("OrganizationID"));
            organization.setOrganizationName(rs.getString("OrganizationName"));
            organization.setOrganizationDescription(rs.getString("OrganizationDescription"));
            organization.setContactInformationPhone(rs.getString("ContactInformationPhone"));

            return organization;
        }
    }

}
