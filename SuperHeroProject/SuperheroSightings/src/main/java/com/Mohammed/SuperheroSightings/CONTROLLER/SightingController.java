package com.Mohammed.SuperheroSightings.CONTROLLER;

import com.Mohammed.SuperheroSightings.DAO.HeroDao;
import com.Mohammed.SuperheroSightings.DAO.LocationDao;
import com.Mohammed.SuperheroSightings.DAO.SightingDao;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Sighting;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mohammedchowdhury
 */
@Controller
public class SightingController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Hero> heroes = heroDao.getAllHero();
        List<Location> locations = locationDao.getAllLocations();
        List<Sighting> sightings = sightingDao.getAllSightings();

        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);
        model.addAttribute("sightings", sightings);

        model.addAttribute("errors", violations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        Sighting sighting = new Sighting();
        String SightingDate = request.getParameter("SightingDate");
        LocalDate sightingDate;
        try {
            sightingDate = LocalDate.parse(SightingDate);
        } catch (Exception e) {
            sightingDate = null;
        }
        sighting.setSightingDate(sightingDate);

        String SightingDescription = request.getParameter("SightingDescription");
        sighting.setSightingDescription(SightingDescription);

        int heroSightedID = Integer.parseInt(request.getParameter("HeroID"));
        Hero hero = heroDao.getHeroById(heroSightedID);
        sighting.setHeroSighted(hero);

        int locationSightedId = Integer.parseInt(request.getParameter("LocationId"));
        Location location = locationDao.getLocationById(locationSightedId);
        sighting.setLocation(location);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        }
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("SightingToDelete"));
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("SightingToEdit"));
        Sighting sighting = sightingDao.getSightingById(id);

        List<Hero> heroes = heroDao.getAllHero();
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);

        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @PostMapping("updateSighting")
    public String updateSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("SightingID"));
        Sighting sighting = sightingDao.getSightingById(id);

        String SightingDate = request.getParameter("SightingDate");
        LocalDate sightingDate;
        try {
            sightingDate = LocalDate.parse(SightingDate);
        } catch (Exception e) {
            sightingDate = null;
        }
        sighting.setSightingDate(sightingDate);
        String SightingDescription = request.getParameter("SightingDescription");
        sighting.setSightingDescription(SightingDescription);

        int HeroID = Integer.parseInt(request.getParameter("HeroID"));
        Hero hero = heroDao.getHeroById(HeroID);
        sighting.setHeroSighted(hero);

        int LocationId = Integer.parseInt(request.getParameter("LocationId"));
        Location location = locationDao.getLocationById(LocationId);
        sighting.setLocation(location);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.updateSighting(sighting);
        }
        return "redirect:/sightings";
    }

    @GetMapping("/")
    public String showLatestTen(HttpServletRequest request, Model model) {
        List<Sighting> listOfTopOrganization = sightingDao.getTenLatestSighting();
        model.addAttribute("topTenSighting", listOfTopOrganization);
        return "sightingsTopTen";
    }

    @PostMapping("searchByDate")
    public String searchByDate(HttpServletRequest request, Model model) {
        
        String date = request.getParameter("searchDate");
        LocalDate sightingDate;
        try {
            sightingDate = LocalDate.parse(date);
        } catch (Exception e) {
            sightingDate = null;
        }
        if (sightingDate != null) {
            List<Sighting> sightingsByDate = sightingDao.getSightingsFromDate(sightingDate);
            model.addAttribute("sightingsByDate", sightingsByDate);
            return "sightingsByDate";
        }
        System.out.println("AAAAAAA");
        return "redirect:/sightings";
    }

}
