/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipo4.veterinaria.servicios;

import com.equipo4.veterinaria.entidades.Cliente;
import com.equipo4.veterinaria.entidades.Foto;
import com.equipo4.veterinaria.enums.Rol;
import com.equipo4.veterinaria.errores.ErrorServicio;
import com.equipo4.veterinaria.repositorios.ClienteRepositorio;
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
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author juamp
 */
@Service
public class ClienteServicio implements UserDetailsService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    public Cliente registrarCliente(String nombre, String apellido, String telefono,
            String email, String password, String password2) throws ErrorServicio {
        if (nombre.isEmpty()) {
            throw new ErrorServicio("El campo nombre no puede estar vacio");
        }
        if (apellido.isEmpty()) {
            throw new ErrorServicio("El campo apellido no puede estar vacio");
        }
        
        if (telefono == null) {
            throw new ErrorServicio("El campo telefono no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new ErrorServicio("El campo email no puede estar vacio");
        }

        Cliente cliente = clienteRepositorio.buscarClientePorEmail(email);

        if (cliente != null) {
            throw new ErrorServicio("El usuario YA EXISTE");
        }

        if (password.isEmpty() || password.length() < 6) {
            throw new ErrorServicio("El campo password no puede estar vacio y no puede tener menos de 6 caracteres");
        }

        if (!password.equals(password2)) {
            throw new ErrorServicio("Las contraseñas no coinciden");
        }

        cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        cliente.setPassword(encoder.encode(password));
        cliente.setRol(Rol.REGISTRADO);
//        if (file != null) {
//            Foto foto = fotoServicio.guardar(file);
//            cliente.setFoto(foto);
//        }
        return clienteRepositorio.save(cliente);
    }

    public void modificarCliente(String id, String nombre, String apellido, String telefono,
            String email, String password, String password2, MultipartFile file) throws ErrorServicio {

        if (nombre.isEmpty()) {
            throw new ErrorServicio("El campo nombre no puede estar vacio");
        }
        if (apellido.isEmpty()) {
            throw new ErrorServicio("El campo apellido no puede estar vacio");
        }
        
        if (telefono == null) {
            throw new ErrorServicio("El campo telefono no puede estar vacio");
        }
        if (email.isEmpty()) {
            throw new ErrorServicio("El campo email no puede estar vacio");
        }

        if (password.isEmpty() || password.length() < 6) {
            throw new ErrorServicio("El campo password no puede estar vacio y no puede tener menos de 6 caracteres");
        }

        if (!password.equals(password2)) {
            throw new ErrorServicio("Las contraseñas no coinciden");
        }

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            
            cliente.setTelefono(telefono);
            cliente.setEmail(email);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            cliente.setPassword(encoder.encode(password));
            String idFoto = null;
            if (cliente.getFoto() != null) {
                idFoto = cliente.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizarFoto(idFoto, file);
            cliente.setFoto(foto);
            cliente.setRol(Rol.REGISTRADO);
            clienteRepositorio.save(cliente);
        } else {
            throw new ErrorServicio("Usuario No Encontrado");
        }
        
    }

    public void eliminarCliente(String idCliente) throws ErrorServicio {
        Cliente cliente = clienteRepositorio.findById(idCliente).get();
        if (cliente == null) {
            throw new ErrorServicio("Cliente no encontrado");
        }
        clienteRepositorio.delete(cliente);
    }
    
    public List<Cliente> findAll() {
        return clienteRepositorio.findAll();
    }

    public Cliente getUsuario(String id) {
        return clienteRepositorio.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Cliente cliente = clienteRepositorio.buscarClientePorEmail(email);
            List<GrantedAuthority> privilegios = new ArrayList<>();
            agregarUsuarioALaSesion(cliente);
            privilegios.add(new SimpleGrantedAuthority("ROLE_" + cliente.getRol()));
            return new User(email, cliente.getPassword(), privilegios);
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }

    public void agregarUsuarioALaSesion(Cliente cliente) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("usuariosession", cliente);
    }

}
