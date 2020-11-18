package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DAO.OrganizationDaoImpl.OrganizationMapper;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class LocationDaoImpl implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    //works
    @Override
    public Location getLocationById(int LocationID) {
        try {
            final String GET_LOCATION_BY_ID = "SELECT * FROM LOCATION WHERE LocationID = ?; ";
            return jdbc.queryForObject(GET_LOCATION_BY_ID, new LocationMapper(), LocationID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    //works
    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO LOCATION(LocationName, LocationDescription, AddressInformation, Latitude, Longitude) "
                + "VALUES(?,?,?,?,?) ;";
        jdbc.update(INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getAddressInformation(),
                location.getLatitude(),
                location.getLongitude());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(newId);
        return location;
    }

    //works
    @Override
    public List<Location> getAllLocations() {
        final String GET_ALL_LOCATIONS = "SELECT * FROM LOCATION ;";
        return jdbc.query(GET_ALL_LOCATIONS, new LocationMapper());
    }

    //works
    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE LOCATION SET LocationName = ?, LocationDescription = ?, "
                + "AddressInformation = ?, Latitude = ?, Longitude = ? WHERE LocationID = ? ;";
        jdbc.update(UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getAddressInformation(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    //works
    @Override
    @Transactional
    public void deleteLocationById(int LocationID) {
        List<Organization> organizationsAtLocation = new ArrayList<Organization>();

        final String GET_ORGANIZATION_BY_LOCATION = "SELECT ORGANIZATION.* FROM ORGANIZATION WHERE LocationID = ? ;";
        organizationsAtLocation = jdbc.query(GET_ORGANIZATION_BY_LOCATION, new OrganizationMapper(), LocationID);

        if (organizationsAtLocation.size() > 0) {
            for (int i = 0; i < organizationsAtLocation.size(); i++) {
                final String DELETE_FROM_HEROES_ORGANIZATION = "DELETE FROM HERO_ORGANIZATION "
                        + " WHERE HERO_ORGANIZATION.OrganizationID = ? ;";
                jdbc.update(DELETE_FROM_HEROES_ORGANIZATION, organizationsAtLocation.get(i).getOrganizationID());
            }
        }
        
        final String DELETE_ORGANIZATION_BY_LOCATION = "DELETE FROM ORGANIZATION WHERE LocationID = ? ;";
        jdbc.update(DELETE_ORGANIZATION_BY_LOCATION, LocationID);

        final String DELETE_SIGHTING_BY_LOCATION = "DELETE FROM SIGHTING WHERE LocationID = ? ;";
        jdbc.update(DELETE_SIGHTING_BY_LOCATION, LocationID);

        final String DELETE_LOCATION = "DELETE FROM LOCATION WHERE LocationID = ? ;";
        jdbc.update(DELETE_LOCATION, LocationID);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationId(rs.getInt("LocationID"));
            location.setLocationName(rs.getString("LocationName"));
            location.setLocationDescription(rs.getString("LocationDescription"));
            location.setAddressInformation(rs.getString("AddressInformation"));
            location.setLatitude(rs.getString("Latitude"));
            location.setLongitude(rs.getString("Longitude"));
            return location;
        }
    }
}
