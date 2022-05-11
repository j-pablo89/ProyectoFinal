/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.controladores;

import com.equipo4.veterinaria.entidades.Cliente;
import com.equipo4.veterinaria.errores.ErrorServicio;
import com.equipo4.veterinaria.repositorios.ClienteRepositorio;
import com.equipo4.veterinaria.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    
    @GetMapping("/homeuser")
    public String homeUser(){
        return "homeuser";
    }
    
    @GetMapping("/logout")
    public String logout(){
        return "home";
    }
    
    @GetMapping("/perfil")
    public String perfil(@RequestParam(name = "id", required = false) String id, ModelMap modelo){
        try {
            Cliente cliente = clienteServicio.getUsuario(id);
            modelo.addAttribute("perfil", cliente);
        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
        }
        return "perfil";
    }
    
    @GetMapping("/editarperfil")
    public String editarPerfil(){
        return "editarperfil";
    }
    
    @PostMapping("/editarperfil")
    public String editarPerfilOK(ModelMap modelo,
                           @RequestParam(name = "id", required = false) String id,
                           @RequestParam (required = false) MultipartFile file,
                           @RequestParam (name = "nombre", required = false)String nombre,
                           @RequestParam (name = "apellido", required = false)String apellido,
                           @RequestParam (name = "telefono", required = false)String telefono,
                           @RequestParam (name = "email", required = false)String email,
                           @RequestParam (name = "password", required = false)String password,
                           @RequestParam (name = "password2", required = false)String password2
                           
                           ) {
        Cliente cliente = null;
        try {
            cliente = clienteServicio.getUsuario(id);
            modelo.addAttribute("perfil", cliente);
            clienteServicio.modificarCliente(id,nombre, apellido, telefono, email, password, password2,file);
            return "redirect:/homeuser";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
            modelo.put("email", email);
            
            ex.getMessage();
            
            return "editarperfil";
            
        }
        
    }
    

}
