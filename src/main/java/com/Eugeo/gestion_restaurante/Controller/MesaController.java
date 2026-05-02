package com.Eugeo.gestion_restaurante.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Eugeo.gestion_restaurante.Entity.Mesa;
import com.Eugeo.gestion_restaurante.Service.MesaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mesas")
@RequiredArgsConstructor
public class MesaController {
    
    private final MesaService service;

    @GetMapping
    public List<Mesa> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mesa findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Mesa create(@RequestBody Mesa mesa) {
        return service.create(mesa);
    }

    @PutMapping("/{id}")
    public Mesa update(@PathVariable Long id, @RequestBody Mesa mesa) {
        return service.update(id, mesa);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}