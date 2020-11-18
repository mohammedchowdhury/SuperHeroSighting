/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Mohammed.SuperheroSightings.CONTROLLER;

import com.Mohammed.SuperheroSightings.DAO.SuperpowerDao;
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
public class SuperPowerController {

    @Autowired
    SuperpowerDao superpowerDao;

    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();

    @GetMapping("superpower")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("errors", violations);
        return "superpower";
    }

    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request) {
        String superpowerName = request.getParameter("superpowerName");
        Superpower superpower = new Superpower();
        superpower.setSuperPowerName(superpowerName);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);
        if (violations.isEmpty()) {
            superpowerDao.addSuperpower(superpower);
        }
        return "redirect:/superpower";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpower";
    }

    @GetMapping("editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerByID(id);
        model.addAttribute("Superpower", superpower);
        model.addAttribute("errors", violations);
        return "editSuperpower";
    }

    @PostMapping("updateSuperpower")
    public String updateSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("superpowerID"));
        Superpower superpower = superpowerDao.getSuperpowerByID(id);
        Superpower tempSuperpower = superpowerDao.getSuperpowerByID(id);

        String superpowerName = request.getParameter("superpowerName");
        superpower.setSuperPowerName(superpowerName);

        //validate here 
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);
        if (violations.isEmpty()) {
            superpowerDao.updateSuperpower(superpower);
        }
        return "redirect:/superpower";
    }

}
