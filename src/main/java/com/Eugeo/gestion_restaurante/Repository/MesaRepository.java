package com.Eugeo.gestion_restaurante.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Eugeo.gestion_restaurante.Entity.Mesa;


@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long>{

    Optional<Mesa> findByNumero(Integer numero);

    List<Mesa> findByEstado(EstadoMesa estado);

    boolean existsByNumero(Integer numero);
}