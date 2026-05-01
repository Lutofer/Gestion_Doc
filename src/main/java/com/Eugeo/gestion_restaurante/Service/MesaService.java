package com.Eugeo.gestion_restaurante.Service;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Eugeo.gestion_restaurante.Entity.Mesa;
import com.Eugeo.gestion_restaurante.Repository.MesaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MesaService {
    
    private final MesaRepository repository;

    public List<Mesa> findAll() {
        return repository.findAll();
    }

    public Mesa findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Mesa no encontrada"));
    }

    public Mesa create(Mesa mesa) {

        if (mesa.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "La mesa ya tiene ID");
        }

        if (repository.existsByNumero(mesa.getNumero())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Número de mesa duplicado");
        }

        mesa.setEstado(EstadoMesa.LIBRE);
        return repository.save(mesa);
    }

    public Mesa update(Long id, Mesa mesa) {
        Mesa aux = findById(id);

        aux.setNumero(mesa.getNumero());
        aux.setCapacidad(mesa.getCapacidad());

        return repository.save(aux);
    }

    public void delete(Long id) {
        Mesa mesa = findById(id);

        if (mesa.getEstado() == EstadoMesa.OCUPADO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No puedes eliminar una mesa ocupada");
        }

        repository.deleteById(mesa.getId());
    }

    public void ocuparMesa(Mesa mesa) {
        if (mesa.getEstado() == EstadoMesa.OCUPADO) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Mesa ocupada");
        }
        mesa.setEstado(EstadoMesa.OCUPADO);
    }

    public void liberarMesa(Mesa mesa) {
        mesa.setEstado(EstadoMesa.LIBRE);
    }
}
