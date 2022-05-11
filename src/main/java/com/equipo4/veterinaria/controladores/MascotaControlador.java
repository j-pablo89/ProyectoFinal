/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.controladores;

import com.equipo4.veterinaria.entidades.Cliente;
import com.equipo4.veterinaria.entidades.Mascota;
import com.equipo4.veterinaria.enums.Sexo;
import com.equipo4.veterinaria.enums.Tipo;
import com.equipo4.veterinaria.errores.ErrorServicio;
import com.equipo4.veterinaria.repositorios.ClienteRepositorio;
import com.equipo4.veterinaria.servicios.MascotaServicio;
import java.awt.print.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/mascota")
public class MascotaControlador {
    
    @Autowired
    private MascotaServicio mascotaServicio;
    
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @GetMapping("/registromascota")
    public String registro(){
        return "registromascota";
    }

    @PostMapping("/registromascota")
    public String registroMascota(ModelMap modelo,
                                 @RequestParam String nombre,
                                 @RequestParam (name = "edad",required = false) Integer edad,
                                 @RequestParam String mascota,
                                 @RequestParam String sexo,
                                 @RequestParam String carnet){
        try {
            Tipo mascota2=null;
            Sexo sexo2 = null;
            if(mascota.equalsIgnoreCase("perro")){
                 mascota2 = Tipo.PERRO;
            }
            if(mascota.equalsIgnoreCase("gato")){
                 mascota2 = Tipo.GATO;
            }
            if(sexo.equalsIgnoreCase("macho")){
                sexo2 = Sexo.MACHO;
            }
            if(sexo.equalsIgnoreCase("hembra")){
                sexo2 = Sexo.HEMBRA;
            }
            String idCliente = clienteRepositorio.obtenerUltimoCliente().getId();
            Mascota nuevoMascota = mascotaServicio.crearMascota(idCliente,nombre,edad, mascota2, sexo2);
            
        } catch (ErrorServicio e) {
            e.getMessage();
            modelo.put("error", e);
            modelo.put("nombre", nombre);
            modelo.put("edad", edad);
            modelo.put("mascota", mascota);
            modelo.put("sexo", sexo);
            modelo.put("carnet", carnet);
            
            return "registromascota";
            
        }
        
        return "registroexitoso";
        
    }
    
    @GetMapping("/registroexitoso")
    public String registroExitoso(){
        return "registroexitoso";
    }
    
    
}
