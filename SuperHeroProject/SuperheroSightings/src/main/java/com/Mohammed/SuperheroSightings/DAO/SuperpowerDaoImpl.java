package com.Mohammed.SuperheroSightings.DAO;

import com.Mohammed.SuperheroSightings.DAO.HeroDaoImpl.HeroMapper;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
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
public class SuperpowerDaoImpl implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;

    private Superpower findSuperpowerByName(String name) {
        try {
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM SUPERPOWER WHERE SuperPowerName = ? ; ";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), name);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Superpower getSuperpowerByID(int SuperPowerID) {
        try {
            final String GET_SUPERPOWER_BY_ID = "SELECT * FROM SUPERPOWER WHERE SuperPowerID = ?;";
            return jdbc.queryForObject(GET_SUPERPOWER_BY_ID, new SuperpowerMapper(), SuperPowerID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String GET_ALL_SUPERPOWERS = "SELECT * FROM SUPERPOWER;";
        return jdbc.query(GET_ALL_SUPERPOWERS, new SuperpowerMapper());
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        if (findSuperpowerByName(superpower.getSuperPowerName()) == null) {
            final String INSERT_SUPERPOWER = "INSERT INTO SUPERPOWER(SuperPowerName) "
                    + "VALUES(?);";
            jdbc.update(INSERT_SUPERPOWER, superpower.getSuperPowerName());
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            superpower.setSuperPowerID(newId);
        }
        return superpower;
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        if (findSuperpowerByName(superpower.getSuperPowerName()) == null) {
            final String UPDATE_SUPERPOWER = "UPDATE SUPERPOWER SET SuperPowerName = ? "
                    + "WHERE SuperPowerID = ? ;";
            jdbc.update(UPDATE_SUPERPOWER,
                    superpower.getSuperPowerName(),
                    superpower.getSuperPowerID());
        }
    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int SuperPowerID) {
        List<Hero> heroesWithSuperPower = new ArrayList();

        final String GET_ALL_HEROES_BY_SUPERPOWERID = "SELECT * FROM HERO WHERE SuperPowerID = ? ";

        heroesWithSuperPower = jdbc.query(GET_ALL_HEROES_BY_SUPERPOWERID, new HeroMapper(), SuperPowerID);

        for (Hero hero : heroesWithSuperPower) {
            final String DELETE_HERO_FROM_HERO_ORGANIZATION = "DELETE FROM HERO_ORGANIZATION "
                    + "WHERE HeroID = ?";
            jdbc.update(DELETE_HERO_FROM_HERO_ORGANIZATION, hero.getHeroID());

            final String DELETE_SIGHTING = "DELETE FROM SIGHTING WHERE HeroID = ?";
            jdbc.update(DELETE_SIGHTING, hero.getHeroID());
        }

        final String DELETE_HERO = "DELETE FROM HERO WHERE SuperPowerID = ?";
        jdbc.update(DELETE_HERO, SuperPowerID);

        final String DELETE_SUPERPOWER = "DELETE FROM SUPERPOWER WHERE SuperPowerID = ?";
        jdbc.update(DELETE_SUPERPOWER, SuperPowerID);
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower(rs.getInt("SuperPowerID"), rs.getString("SuperPowerName"));
            return superpower;
        }
    }
}
