/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.servicios;

import com.equipo4.veterinaria.entidades.Cliente;
import com.equipo4.veterinaria.entidades.Foto;
import com.equipo4.veterinaria.entidades.Mascota;
import com.equipo4.veterinaria.enums.Sexo;
import com.equipo4.veterinaria.enums.Tipo;
import com.equipo4.veterinaria.errores.ErrorServicio;
import com.equipo4.veterinaria.repositorios.ClienteRepositorio;
import com.equipo4.veterinaria.repositorios.MascotaRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author juamp
 */
@Service
public class MascotaServicio {

    @Autowired
    private MascotaRepositorio mascotaRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    public Mascota crearMascota(String idCliente, String nombre, Integer edad, Tipo tipo, Sexo sexo) throws ErrorServicio {

        Cliente cliente = clienteRepositorio.findById(idCliente).get();

        Mascota mascota = new Mascota();
        if (nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (edad == null) {
            throw new ErrorServicio("Debe especificar la edad");
        }
        if (tipo == null) {
            throw new ErrorServicio("Debe especificar un tipo");
        }
        if (sexo == null) {
            throw new ErrorServicio("Debe especificar el sexo");
        }
        mascota.setNombre(nombre);
        mascota.setEdad(edad);
        mascota.setSexo(sexo);
        mascota.setTipo(tipo);
        mascota.setCliente(cliente);
//        if (file != null) {
//            Foto foto = fotoServicio.guardar(file);
//            mascota.setFoto(foto);
//        } else {
//            throw new ErrorServicio("Debe cargar una imagen");
//        }

        return mascotaRepositorio.save(mascota);
    }

    public void modificarMascota(String idCliente, String idMascota, String nombre, Integer edad) throws ErrorServicio {
        if (nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (edad == null) {
            throw new ErrorServicio("Debe especificar la edad");
        }

        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getCliente().getId().equals(idCliente)) {
                mascota.setNombre(nombre);
                mascota.setEdad(edad);

//                String idFoto = null;
//                if (mascota.getFoto() != null) {
//                    idFoto = mascota.getFoto().getId();
//                }
//                Foto foto = fotoServicio.actualizarFoto(idFoto, file);
                mascotaRepositorio.save(mascota);
            } else {
                throw new ErrorServicio("No Puede realizar esta accion");
            }

        } else {
            throw new ErrorServicio("No existe mascota");
        }

    }

    public void eliminarMascota(String idCliente, String idMascota) throws ErrorServicio {
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota);
        if (respuesta.isPresent()) {
            Mascota mascota = respuesta.get();
            if (mascota.getCliente().getId().equals(idCliente)) {
                mascotaRepositorio.delete(mascota);
            } else {
                throw new ErrorServicio("No Puede realizar esta accion");
            }
        } else {
            throw new ErrorServicio("No existe mascota");
        }
    }
    
    public List<Mascota> findAll(){
        return mascotaRepositorio.findAll();
    }
}
