/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.servicios;


import com.equipo4.veterinaria.entidades.Foto;
import com.equipo4.veterinaria.entidades.Veterinario;
import com.equipo4.veterinaria.enums.Rol;
import com.equipo4.veterinaria.errores.ErrorServicio;
import com.equipo4.veterinaria.repositorios.VeterinarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author juamp
 */
public class VeterinarioServicio implements UserDetailsService{
    
    @Autowired
    private VeterinarioRepositorio veterinarioRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;
    
    public Veterinario crearVeterinario(MultipartFile file, String nombre, String apellido, Integer matriculaProfesional,
                                        Integer telefono, String email, String password, String password2) throws ErrorServicio{
        if(nombre.isEmpty()){
            throw new ErrorServicio("El campo nombre no puede estar vacio");
        }
        if(apellido.isEmpty()){
            throw new ErrorServicio("El campo apellido no puede estar vacio");
        }
        if(matriculaProfesional == null){
            throw new ErrorServicio("El campo MP no puede estar vacio");
        }
        if(telefono == null){
            throw new ErrorServicio("El campo telefono no puede estar vacio");
        }
        if(email.isEmpty()){
            throw new ErrorServicio("El campo email no puede estar vacio");
        }
        if(password.isEmpty()){
            throw new ErrorServicio("El campo contraseña no puede estar vacio");
        }
        if(password2.isEmpty()){
            throw new ErrorServicio("Debe repetir contraseña");
        }
        if(!password2.equals(password)){
            throw new ErrorServicio("Las contraseñas no coinciden");
        }
               
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(nombre);
        veterinario.setApellido(apellido);
        veterinario.setMatriculaProfesional(matriculaProfesional);
        veterinario.setTelefono(telefono);
        veterinario.setEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        veterinario.setPassword(encoder.encode(password));
        veterinario.setRol(Rol.VETERINARIO);
        return veterinarioRepositorio.save(veterinario);
    }
    
    public void modificarVeterinario(String id, MultipartFile file, String nombre, String apellido, Integer matriculaProfesional,
                                     Integer telefono, String email, String password, String password2) throws ErrorServicio{
    
        if(nombre.isEmpty()){
            throw new ErrorServicio("El campo nombre no puede estar vacio");
        }
        if(apellido.isEmpty()){
            throw new ErrorServicio("El campo apellido no puede estar vacio");
        }
        if(matriculaProfesional == null){
            throw new ErrorServicio("El campo MP no puede estar vacio");
        }
        if(telefono == null){
            throw new ErrorServicio("El campo telefono no puede estar vacio");
        }
        if(email.isEmpty()){
            throw new ErrorServicio("El campo email no puede estar vacio");
        }
        if(password.isEmpty()){
            throw new ErrorServicio("El campo contraseña no puede estar vacio");
        }
        if(password2.isEmpty()){
            throw new ErrorServicio("Debe repetir contraseña");
        }
        if(!password2.equals(password)){
            throw new ErrorServicio("Las contraseñas no coinciden");
        }
        
        Optional<Veterinario> respuesta = veterinarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            Veterinario veterinario = respuesta.get();
            veterinario.setNombre(nombre);
            veterinario.setApellido(apellido);
            veterinario.setMatriculaProfesional(matriculaProfesional);
            veterinario.setTelefono(telefono);
            veterinario.setEmail(email);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            veterinario.setPassword(encoder.encode(password));
            String idFoto = null;
            
            if (veterinario.getFoto() != null) {
                idFoto = veterinario.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizarFoto(idFoto, file);
            veterinario.setFoto(foto);
            veterinarioRepositorio.save(veterinario);
        } else {
            throw new ErrorServicio("Veterinario NO ENCONTRADO");
        }
        
    }
    
    public void eliminarVeterinario(String id) throws ErrorServicio{
        Veterinario veterinario = veterinarioRepositorio.findById(id).get();
        if(veterinario != null){
            veterinarioRepositorio.delete(veterinario);
        } else {
            throw new ErrorServicio("Veterinario NO ENCONTRADO");
        }
    }
    
    public List<Veterinario> findAll(){
        return veterinarioRepositorio.findAll();
    }
    public void agregarUsuarioALaSesion(Veterinario veterinario) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("veterinario", veterinario);
    }
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Veterinario veterinario = veterinarioRepositorio.buscarPorEmail(email);
            List<GrantedAuthority> privilegios = new ArrayList<>();
            privilegios.add(new SimpleGrantedAuthority("ROLE_"+veterinario.getRol()));
            agregarUsuarioALaSesion(veterinario);
            return new User(email,veterinario.getPassword(),privilegios);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
    }
}
