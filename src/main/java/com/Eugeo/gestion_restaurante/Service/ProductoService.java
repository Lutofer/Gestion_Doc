package com.Eugeo.gestion_restaurante.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Eugeo.gestion_restaurante.Entity.Producto;
import com.Eugeo.gestion_restaurante.Repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;

    public List<Producto> findAll() {
        return repository.findAll();
    }

    public Producto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Producto create(Producto producto) {
        if (producto.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El producto ya tiene ID");
        }
        return repository.save(producto);
    }

    public Producto update(Long id, Producto producto) {
        Producto aux = findById(id);
        aux.setNombre(producto.getNombre());
        aux.setTipo(producto.getTipo());
        aux.setPrecio(producto.getPrecio());
        aux.setStock(producto.getStock());
        return repository.save(aux);
    }

    public void delete(Long id) {
        Producto producto = findById(id);
        repository.deleteById(producto.getId());
    }

    //proceso de actualizar stock (en prueba)
    public void descontarStock(Long id, int cantidad) {
        Producto p = findById(id);

        if (p.getStock() < cantidad) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        p.setStock(p.getStock() - cantidad);
        repository.save(p);
    }
}