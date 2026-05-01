package com.Eugeo.gestion_restaurante.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Eugeo.gestion_restaurante.Entity.Usuario;
import com.Eugeo.gestion_restaurante.Service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<Usuario> findAll() {
        return service.findAll();
    }

}