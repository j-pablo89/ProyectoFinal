/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.servicios;

import com.equipo4.veterinaria.entidades.Consulta;
import com.equipo4.veterinaria.entidades.Mascota;
import com.equipo4.veterinaria.entidades.Veterinario;
import com.equipo4.veterinaria.repositorios.ConsultaRepositorio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author juamp
 */
@Service
public class ConsultaServicio {
    
    @Autowired
    private ConsultaRepositorio consultaRepositorio;
    
    public Consulta crearConsulta(Date fechaConsulta, Mascota mascota, Veterinario veterinario){
        Consulta consulta = new Consulta();
        consulta.setFechaConsulta(new Date());
        consulta.setMascota(mascota);
        consulta.setVeterinario(veterinario);
        
        return consultaRepositorio.save(consulta);
    
    }
    
    public List<Consulta> findAll(){
        return consultaRepositorio.findAll();
    }
}
