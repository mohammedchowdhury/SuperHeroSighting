/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.CONTROLLER;

import com.Mohammed.SuperheroSightings.DAO.HeroDao;
import com.Mohammed.SuperheroSightings.DAO.LocationDao;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
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
public class LocationController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    HeroDao heroDao;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String LocationName = request.getParameter("LocationName");
        String LocationDescription = request.getParameter("LocationDescription");
        String AddressInformation = request.getParameter("AddressInformation");
        String Latitude = request.getParameter("Latitude");
        String Longitude = request.getParameter("Longitude");
        Location location = new Location(LocationName, LocationDescription, AddressInformation, Latitude, Longitude);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (violations.isEmpty()) {
            locationDao.addLocation(location);
        }
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("LocationId"));
        System.out.println(id);
        Location location = locationDao.getLocationById(id);
        model.addAttribute("Location", location);
        // model.addAttribute("errors", violations);
        return "editLocation";
    }

    @PostMapping("updateLocation")
    public String updateLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("LocationId"));
        String LocationName = request.getParameter("LocationName");
        String LocationDescription = request.getParameter("LocationDescription");
        String AddressInformation = request.getParameter("AddressInformation");
        String Latitude = request.getParameter("Latitude");
        String Longitude = request.getParameter("Longitude");

        Location location = locationDao.getLocationById(id);
        location.setLocationName(LocationName);
        location.setLocationDescription(LocationDescription);
        location.setAddressInformation(AddressInformation);
        location.setLatitude(Latitude);
        location.setLongitude(Longitude);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if (violations.isEmpty()) {
            locationDao.updateLocation(location);
        }

        return "redirect:/locations";
    }

    
    @GetMapping("singleLocationInfo")
    public String singleLocationInfo(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("singleLocationid"));
        Location location = locationDao.getLocationById(id);
        List<Hero> listOfHero = heroDao.getHeroForLocation(location);
        model.addAttribute("Location", location);
        model.addAttribute("heroes", listOfHero);
        return "singleLocationInfo";
    }

}
