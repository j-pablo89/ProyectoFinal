/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.repositorios;

import com.equipo4.veterinaria.entidades.Cliente;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author juamp
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.email = :email")
    public Cliente buscarClientePorEmail(@Param("email") String email);

    @Query(value="SELECT * FROM Cliente ORDER BY id DESC LIMIT 1", nativeQuery = true)
    public Cliente obtenerUltimoCliente();
//    @Query("SELECT c FROM Cliente c ORDER BY c.id DESC")
//    List<Cliente> obtenerUltimoCliente(Pageable pageable);

}
