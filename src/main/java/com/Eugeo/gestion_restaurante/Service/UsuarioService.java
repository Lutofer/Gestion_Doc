package com.Eugeo.gestion_restaurante.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Eugeo.gestion_restaurante.Entity.Usuario;
import com.Eugeo.gestion_restaurante.Repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Usuario findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public Usuario findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    public Usuario create(Usuario usuario) {
        if (usuario.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El usuario ya tiene ID");
        }

        if (repository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email ya registrado");
        }
        
        return repository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario aux = findById(id);

        aux.setNombre(usuario.getNombre());
        aux.setEmail(usuario.getEmail());
        aux.setDni(usuario.getDni());
        aux.setRol(usuario.getRol());

        return repository.save(aux);
    }

    public void delete(Long id) {
        Usuario usuario = findById(id); //posible cambio
        repository.deleteById(usuario.getId());
    }
}
