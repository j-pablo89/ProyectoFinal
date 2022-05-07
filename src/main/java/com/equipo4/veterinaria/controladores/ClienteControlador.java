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
    public String home() {
        return "home";
    }
    
    

    @GetMapping("/donar")
    public String donar() {
        return "donar";
    }

    @GetMapping("/registrouser")
    public String registro() {
        return "registrouser";
    }

    @GetMapping("/registrouser1")
    public String registro1() {
        return "registrouser1";
    }

    @GetMapping("/unoregBlog")
    public String blog() {
        return "unoregBlog";
    }

    @GetMapping("/unoregSumate")
    public String sumate() {
        return "unoregSumate";
    }

    @GetMapping("/unoregTienda")
    public String tienda() {
        return "unoregTienda";
    }
    
    
    
    
    @GetMapping("/registromascota")
    public String mascota(){
        return "registromascota";
    }

}
