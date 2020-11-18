/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.CONTROLLER;

import com.Mohammed.SuperheroSightings.DAO.HeroDao;
import com.Mohammed.SuperheroSightings.DAO.LocationDao;
import com.Mohammed.SuperheroSightings.DAO.OrganizationDao;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Location;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import java.util.ArrayList;
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
public class OrganizationController {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Location> allLocation = locationDao.getAllLocations();
        List<Hero> allHeroes = heroDao.getAllHero();
        List<Organization> allOrganization = organizationDao.getAllOrganizations();

        model.addAttribute("heroes", allHeroes);
        model.addAttribute("locations", allLocation);
        model.addAttribute("organizations", allOrganization);

        model.addAttribute("errors", violations);
        return "organizations";
    }

    @PostMapping("addOrginization")
    public String addOrginization(HttpServletRequest request) {

        String OrganizationName = request.getParameter("OrganizationName");
        String OrganizationDescription = request.getParameter("OrganizationDescription");
        String ContactInformationPhone = request.getParameter("ContactInformationPhone");

        //String locationID = request.getParameter("LocationId");
        Location location = null;
        try {
            int locationID = Integer.parseInt(request.getParameter("LocationId"));
            location = locationDao.getLocationById(locationID);
        } catch (Exception ex) {

        }

        String[] listOfHeroesID = null;
        try {
            listOfHeroesID = request.getParameterValues("HeroID");
        } catch (Exception e) {

        }
        List<Hero> listOfHeroes = new ArrayList<Hero>();

        if (listOfHeroesID != null) {
            for (int i = 0; i < listOfHeroesID.length; i++) {
                int id = Integer.parseInt(listOfHeroesID[i]);
                listOfHeroes.add(heroDao.getHeroById(id));
            }
        } else {
            listOfHeroes = null;
        }
        Organization organization = new Organization();
        organization.setOrganizationName(OrganizationName);
        organization.setOrganizationDescription(OrganizationDescription);
        organization.setLocation(location);
        organization.setContactInformationPhone(ContactInformationPhone);
        organization.setListOfHeros(listOfHeroes);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        if (violations.isEmpty()) {
            organizationDao.addOrganization(organization);
        }

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("OrganizationToDelete"));
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));

        Organization organization = organizationDao.getOrganizationByID(id);
        model.addAttribute("organizationToEdit", organization);

        List<Location> allLocation = locationDao.getAllLocations();
        List<Hero> allHeroes = heroDao.getAllHero();

        model.addAttribute("heroes", allHeroes);
        model.addAttribute("locations", allLocation);

        // model.addAttribute("errors", violations);
        return "editOrganization";
    }

    @PostMapping("updateOrganization")
    public String updateOrganization(HttpServletRequest request, Model model) {

        String OrganizationName = request.getParameter("OrganizationName");
        String OrganizationDescription = request.getParameter("OrganizationDescription");
        String ContactInformationPhone = request.getParameter("ContactInformationPhone");
        
         Location location = null;
        try {
            int locationID = Integer.parseInt(request.getParameter("LocationId"));
            location = locationDao.getLocationById(locationID);
        } catch (Exception ex) {

        }
        
        String[] listOfHeroesID = null;
        try {
            listOfHeroesID = request.getParameterValues("HeroID");
        } catch (Exception e) {

        }
        List<Hero> listOfHeroes = new ArrayList<Hero>();

        if (listOfHeroesID != null) {
            for (int i = 0; i < listOfHeroesID.length; i++) {
                int id = Integer.parseInt(listOfHeroesID[i]);
                listOfHeroes.add(heroDao.getHeroById(id));
            }
        } else {
            listOfHeroes = null;
        }
        int OrganizationID = Integer.parseInt(request.getParameter("OrganizationID"));
        Organization organization = organizationDao.getOrganizationByID(OrganizationID);
        organization.setOrganizationName(OrganizationName);
        organization.setOrganizationDescription(OrganizationDescription);
        organization.setLocation(location);
        organization.setContactInformationPhone(ContactInformationPhone);
        organization.setListOfHeros(listOfHeroes);

        //validate organization
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        if (violations.isEmpty()) {
            organizationDao.updateOrganization(organization);
        }

        return "redirect:/organizations";
    }

    @GetMapping("singleOrganizationInfo")
    public String singleOrganizationInfo(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("SingleOrganizationid"));
        Organization organization = organizationDao.getOrganizationByID(id);
        List<Hero> listOfHeroes = organization.getListOfHeros();

        model.addAttribute("organization", organization);
        model.addAttribute("heroes", listOfHeroes);
        return "singleOrganizationInfo";
    }

}
