package com.Eugeo.gestion_restaurante.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Eugeo.gestion_restaurante.Entity.Producto;
import com.Eugeo.gestion_restaurante.Service.ProductoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor

public class ProductoController {
    private final ProductoService service;

    @GetMapping
    public List<Producto> findAll() {
        return service.findAll();
    }
}