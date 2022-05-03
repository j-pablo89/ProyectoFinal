/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.servicios;

import com.equipo4.veterinaria.entidades.Foto;
import com.equipo4.veterinaria.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author juamp
 */
@Service
public class FotoServicio {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    
    public Foto guardar(MultipartFile file){
       if(file != null){
           try {
               Foto foto = new Foto();
               foto.setMime(file.getContentType());
               foto.setNombre(file.getName());
               foto.setContenido(file.getBytes());
               return fotoRepositorio.save(foto);
           } catch (IOException ex) {
               System.out.println(ex.getMessage());
           }
        }
        return null;
            
    }
    
    public Foto actualizarFoto(String id, MultipartFile file){
        if(file != null){
           try {
               Foto foto = new Foto();
               if(id != null){
                   Optional<Foto> resultado = fotoRepositorio.findById(id);
                   if(resultado.isPresent()){
                       foto = resultado.get();
                   }
               }                  
               foto.setMime(file.getContentType());
               foto.setNombre(file.getName());
               foto.setContenido(file.getBytes());
               return fotoRepositorio.save(foto);
           } catch (IOException ex) {
               System.out.println(ex.getMessage());
           }
        }
        return null;
    }
}
