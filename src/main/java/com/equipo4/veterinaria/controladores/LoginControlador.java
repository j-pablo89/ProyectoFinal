/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author juamp
 */
@Controller
@RequestMapping("/login")
public class LoginControlador {
    
    @GetMapping("")
    public String login(@RequestParam (required = false) String error, ModelMap modelo) {
        if(error != null){
            modelo.put("error", "usuario o clave incorrecto");
        }
        return "reginicio";
    }
}
