package com.Eugeo.gestion_restaurante.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Eugeo.gestion_restaurante.Entity.Producto;
import com.Eugeo.gestion_restaurante.Entity.TipoProducto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByTipo(TipoProducto tipo);

    List<Producto> findByStockGreaterThan(Integer stock);
}