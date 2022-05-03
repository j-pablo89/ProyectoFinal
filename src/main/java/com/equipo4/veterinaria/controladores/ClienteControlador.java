/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author juamp
 */
@Controller
@RequestMapping("")
public class ClienteControlador {
    
    @GetMapping("")
    public String home(){
        return "Home";
    }
    
    @GetMapping("/Reginicio.html")
    public String login(){
        return "Reginicio.html";
    }
}
