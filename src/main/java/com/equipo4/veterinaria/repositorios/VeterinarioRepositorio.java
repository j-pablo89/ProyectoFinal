/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.repositorios;

import com.equipo4.veterinaria.entidades.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author juamp
 */
@Repository
public interface VeterinarioRepositorio extends JpaRepository<Veterinario, String>{
    
    @Query("SELECT v FROM Veterinario v WHERE v.email =:email")
    public Veterinario buscarPorEmail(@Param("email") String email);
}
