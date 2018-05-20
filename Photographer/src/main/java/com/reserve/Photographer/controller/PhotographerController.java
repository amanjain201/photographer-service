/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.reserve.Photographer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PhotographerController {

    @RequestMapping(value = "/photographer", method = RequestMethod.GET)
    public String getPage1(Model model) {
        return "photographer";
    }

}
