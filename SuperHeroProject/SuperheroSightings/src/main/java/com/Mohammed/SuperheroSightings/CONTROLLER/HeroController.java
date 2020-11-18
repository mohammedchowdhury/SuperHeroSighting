/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.CONTROLLER;

import com.Mohammed.SuperheroSightings.DAO.HeroDao;
import com.Mohammed.SuperheroSightings.DAO.OrganizationDao;
import com.Mohammed.SuperheroSightings.DAO.SightingDao;
import com.Mohammed.SuperheroSightings.DAO.SuperpowerDao;
import com.Mohammed.SuperheroSightings.DTO.Hero;
import com.Mohammed.SuperheroSightings.DTO.Organization;
import com.Mohammed.SuperheroSightings.DTO.Sighting;
import com.Mohammed.SuperheroSightings.DTO.Superpower;
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
public class HeroController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    OrganizationDao organozationDao;

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @GetMapping("heroes")
    public String displayLocations(Model model) {
        List<Hero> heroes = heroDao.getAllHero();
        List<Superpower> listOfSuperpower = superpowerDao.getAllSuperpowers();
        model.addAttribute("Heroes", heroes);
        model.addAttribute("listOfSuperpower", listOfSuperpower);
        model.addAttribute("errors", violations);

        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {
        String HeroName = request.getParameter("HeroName");
        String HeroDescription = request.getParameter("HeroDescription");

        Superpower superPower = null;
        try {
            int SuperpowerID = Integer.parseInt(request.getParameter("SuperpowerID"));
            superPower = superpowerDao.getSuperpowerByID(SuperpowerID);
        } catch (Exception ex) {

        }

        Hero hero = new Hero();
        hero.setHeroName(HeroName);
        hero.setHeroDescription(HeroDescription);
        hero.setSuperPower(superPower);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty()) {
            heroDao.addHero(hero);
        }
        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("HeroID"));
        heroDao.deleteHero(id);
        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("HeroID"));
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("Hero", hero);

        List<Superpower> listOfSuperpower = superpowerDao.getAllSuperpowers();
        model.addAttribute("listOfSuperpower", listOfSuperpower);
        return "editHero";
    }

    @PostMapping("updateHero")
    public String updateHero(HttpServletRequest request, Model model) {
        String HeroName = request.getParameter("HeroName");
        String HeroDescription = request.getParameter("HeroDescription");

        Superpower superPower = null;
        try {
            int SuperpowerID = Integer.parseInt(request.getParameter("SuperpowerID"));
            superPower = superpowerDao.getSuperpowerByID(SuperpowerID);
        } catch (Exception ex) {

        }

        //int SuperpowerID = Integer.parseInt(request.getParameter("SuperpowerID"));

        //Superpower superPower = superpowerDao.getSuperpowerByID(SuperpowerID);

        int heroID = Integer.parseInt(request.getParameter("HeroID"));

        Hero hero = heroDao.getHeroById(heroID);
        hero.setHeroName(HeroName);
        hero.setHeroDescription(HeroDescription);
        hero.setSuperPower(superPower);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty()) {
            heroDao.UpdateHero(hero);
        }
        return "redirect:/heroes";
    }

    @GetMapping("singleHeroInfo")
    public String singleHeroInfo(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);
        List<Sighting> sightings = sightingDao.getAllSightings();

        List<Organization> organizations = organozationDao.getOrganizationsByHero(hero);

        model.addAttribute("hero", hero);
        model.addAttribute("sightings", sightings);
        model.addAttribute("organizations", organizations);

        return "singleHeroInfo";
    }
}
