/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DAO.HeroDaoImpl.HeroMapper;
import com.Mohammed.SuperheroSightings.DAO.LocationDaoImpl.LocationMapper;
import com.Mohammed.SuperheroSightings.DAO.SuperpowerDaoImpl.SuperpowerMapper;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Sighting;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class SightingDaoImpl implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO SIGHTING(SightingDate, SightingDescription, HeroID, LocationID) "
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getSightingDate(),
                sighting.getSightingDescription(),
                sighting.getHeroSighted().getHeroID(),
                sighting.getLocation().getLocationId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingID(newId);
        return sighting;
    }

    @Override
    @Transactional
    public Sighting getSightingById(int SightingID) {
        try {
            final String GET_SIGHTING_BY_ID = "SELECT SIGHTING.* FROM SIGHTING WHERE SightingID = ? ;";
            Sighting sighting = jdbc.queryForObject(GET_SIGHTING_BY_ID, new SightingMapper(), SightingID);
            sighting = getHeroesAndLocationsForSighting(sighting);
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Sighting getHeroesAndLocationsForSighting(Sighting sighting) {
        final String GET_LOCATION_FOR_SIGHTING = "SELECT LOCATION.* "
                + " FROM LOCATION JOIN SIGHTING ON LOCATION.LocationID = SIGHTING.LocationID "
                + " WHERE SightingID = ?";
        Location location;
        try {
            location = jdbc.queryForObject(GET_LOCATION_FOR_SIGHTING, new LocationMapper(), sighting.getSightingID());
        } catch (Exception ex) {
            location = null;
        }
        sighting.setLocation(location);

        final String GET_HERO_FOR_SIGHTING
                = "SELECT HERO.* FROM HERO "
                + " JOIN SIGHTING ON SIGHTING.HeroID = HERO.HeroID "
                + " WHERE SIGHTING.SightingID = ? ;";
        Hero hero;
        try {
            hero = jdbc.queryForObject(GET_HERO_FOR_SIGHTING, new HeroMapper(), sighting.getSightingID());
        } catch (Exception ex) {
            hero = null;
        }
        if (hero != null) {
            final String SELECT_SUPERPOWER_FOR_HERO = "SELECT SUPERPOWER.* FROM SUPERPOWER "
                    + "JOIN HERO ON HERO.SuperPowerID = SUPERPOWER.SuperPowerID WHERE HERO.HeroID =? ;";
            Superpower superpower;
            try {
                superpower = jdbc.queryForObject(SELECT_SUPERPOWER_FOR_HERO, new SuperpowerMapper(), hero.getHeroID());
            } catch (Exception ex) {
                superpower = null;
            }
            hero.setSuperPower(superpower);
        }
        sighting.setHeroSighted(hero);
        return sighting;

    }

    @Override
    @Transactional
    public List<Sighting> getAllSightings() {
        final String GET_ALL_SIGHTINGS = "SELECT SIGHTING.* FROM SIGHTING ;";
        List<Sighting> sightings = jdbc.query(GET_ALL_SIGHTINGS, new SightingMapper());
        for (Sighting sighting : sightings) {
            sighting = getHeroesAndLocationsForSighting(sighting);
        }
        return sightings;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE SIGHTING SET SightingDate = ?, SightingDescription = ?, HeroID = ?, "
                + "LocationID = ? WHERE SightingID = ? ; ";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getSightingDate(),
                sighting.getSightingDescription(),
                sighting.getHeroSighted().getHeroID(),
                sighting.getLocation().getLocationId(),
                sighting.getSightingID());
    }

    @Override
    public void deleteSightingById(int SightingID) {
        final String DELETE_SIGHTING = "DELETE FROM SIGHTING WHERE SightingID = ? ; ";
        jdbc.update(DELETE_SIGHTING, SightingID);
    }

    @Override
    @Transactional
    public List<Sighting> getAllSightingsOfHero(Hero hero) {
        final String GET_SIGHTINGS_FOR_HEROES = "SELECT SIGHTING.* FROM SIGHTING "
                + " WHERE SIGHTING.HeroID = ? ; ";
        List<Sighting> sightings = jdbc.query(GET_SIGHTINGS_FOR_HEROES, new SightingMapper(), hero.getHeroID());
        for (Sighting sighting : sightings) {
            sighting = getHeroesAndLocationsForSighting(sighting);
        }
        return sightings;
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsAtLocation(Location location) {
        final String GET_SIGHTINGS_BY_Location = "SELECT SIGHTING.* FROM SIGHTING "
                + " WHERE SIGHTING.LocationID = ? ;";
        List<Sighting> sightings = jdbc.query(GET_SIGHTINGS_BY_Location, new SightingMapper(), location.getLocationId());
        for (Sighting sighting : sightings) {
            sighting = getHeroesAndLocationsForSighting(sighting);
        }
        return sightings;
    }

    @Override
    @Transactional
    public List<Sighting> getSightingsFromDate(LocalDate date) {
        final String GET_SIGHTINGS_FOR_DATE = "SELECT SIGHTING.* FROM SIGHTING "
                + " WHERE SIGHTING.SightingDate = ? ; ";
        List<Sighting> sightings = jdbc.query(GET_SIGHTINGS_FOR_DATE, new SightingMapper(), date);
        for (Sighting sighting : sightings) {
            sighting = getHeroesAndLocationsForSighting(sighting);
        }
        return sightings;
    }

    @Override
    @Transactional
    public List<Sighting> getTenLatestSighting() {
        final String GET_TOP_TEN_SIGHTINGS = "SELECT SIGHTING.* FROM SIGHTING "
                + "ORDER BY SightingDate DESC "
                + "LIMIT 10 ; ";
        List<Sighting> lastTenRecentSighting = jdbc.query(GET_TOP_TEN_SIGHTINGS, new SightingMapper());
        for (Sighting sighting : lastTenRecentSighting) {
            getHeroesAndLocationsForSighting(sighting);
        }
        return lastTenRecentSighting;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingID(rs.getInt("SightingID"));
            sighting.setSightingDescription(rs.getNString("SightingDescription"));
            sighting.setSightingDate(rs.getDate("SightingDate").toLocalDate());
            return sighting;
        }
    }

}
