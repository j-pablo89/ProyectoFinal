/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.controladores;

import com.equipo4.veterinaria.entidades.Cliente;
import com.equipo4.veterinaria.errores.ErrorServicio;
import com.equipo4.veterinaria.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author juamp
 */
@Controller
@RequestMapping("")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;
    
    
    @GetMapping("")
    public String home() {
        return "home";
    }
    
    

    @GetMapping("/donar")
    public String donar() {
        return "donar";
    }
    
    @GetMapping("/registrouser")
    public String registrarUsuario(){
        return "registrouser";
    }

    @PostMapping("/registrouser")
    public String registro(ModelMap modelo,
                           @RequestParam String nombre,
                           @RequestParam String apellido,
                           @RequestParam String telefono,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String password2
                           
                           ) {
        try {
            Cliente cliente = clienteServicio.registrarCliente(nombre, apellido, telefono, email, password, password2);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            modelo.put("password",password);
            modelo.put("password2", password2);
            ex.getMessage();
            
            return "registrouser";
            
        }
        return "registromascota";
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
