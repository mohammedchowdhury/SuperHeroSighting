package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DAO.OrganizationDaoImpl.OrganizationMapper;
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

@Repository
public class HeroDaoImpl implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO HERO(HeroName,HeroDescription,SuperPowerID) "
                + "VALUES(?,?,?) ;";
        jdbc.update(INSERT_HERO,
                hero.getHeroName(),
                hero.getHeroDescription(),
                hero.getSuperPower().getSuperPowerID());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroID(newId);
        return hero;
    }

    @Override
    public Hero getHeroById(int HeroID) {
        try {
            final String GET_HERO_BY_ID = "SELECT * FROM HERO WHERE HeroID = ? ; ";
            Hero hero = jdbc.queryForObject(GET_HERO_BY_ID, new HeroMapper(), HeroID);
            getSuperPowerAndOrganizationForHero(hero);
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private void getSuperPowerAndOrganizationForHero(Hero hero) {
        final String SELECT_SUPERPOWER_FOR_HERO = "SELECT SUPERPOWER.* FROM SUPERPOWER "
                + "JOIN HERO ON HERO.SuperPowerID = SUPERPOWER.SuperPowerID WHERE HERO.HeroID =? ; ";
        Superpower superpower = jdbc.queryForObject(SELECT_SUPERPOWER_FOR_HERO, new SuperpowerMapper(), hero.getHeroID());
        hero.setSuperPower(superpower);
    }

    @Override
    @Transactional
    public void deleteHero(int HeroId) {
        final String DELETE_HERO_SIGHTING = "DELETE FROM SIGHTING WHERE HeroID = ? ;";
        jdbc.update(DELETE_HERO_SIGHTING, HeroId);

        final String DELETE_HERO_From_Organization = "DELETE FROM HERO_ORGANIZATION WHERE HeroID = ? ;";
        jdbc.update(DELETE_HERO_From_Organization, HeroId);

        final String DELETE_HERO = "DELETE FROM HERO WHERE HeroID = ? ;";
        jdbc.update(DELETE_HERO, HeroId);
    }

    @Override
    @Transactional
    public void UpdateHero(Hero heroToUpdate) {
        final String UPDATE_HERO = "UPDATE HERO SET HeroName = ?, HeroDescription = ?, SuperPowerID = ? "
                + " WHERE HeroID = ? ; ";
        jdbc.update(UPDATE_HERO,
                heroToUpdate.getHeroName(),
                heroToUpdate.getHeroDescription(),
                heroToUpdate.getSuperPower().getSuperPowerID(),
                heroToUpdate.getHeroID());
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM HERO_ORGANIZATION WHERE HERO_ORGANIZATION.HeroID = ? ;";
        jdbc.update(DELETE_HERO_ORGANIZATION, heroToUpdate.getHeroID());
    }

    @Override
    public List<Hero> getAllHero() {
        final String GET_ALL_HEROES = "SELECT * FROM HERO";
        List<Hero> listOfHeros = jdbc.query(GET_ALL_HEROES, new HeroMapper());
        for (Hero hero : listOfHeros) {
            getSuperPowerAndOrganizationForHero(hero);
        }
        return listOfHeros;
    }

    @Override
    @Transactional
    public List<Hero> getHeroForLocation(Location location) {
        final String GET_ALL_HEROES = "SELECT DISTINCT HERO.* FROM HERO "
                + " INNER JOIN SIGHTING ON SIGHTING.HeroID = HERO.HeroID WHERE SIGHTING.LocationID = ? ; ";
        List<Hero> listOfHeros = jdbc.query(GET_ALL_HEROES, new HeroMapper(), location.getLocationId());
        for (Hero hero : listOfHeros) {
            getSuperPowerAndOrganizationForHero(hero);
        }
        return listOfHeros;
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroID(rs.getInt("HeroID"));
            hero.setHeroName(rs.getString("HeroName"));
            hero.setHeroDescription(rs.getString("HeroDescription"));
            return hero;
        }
    }
}