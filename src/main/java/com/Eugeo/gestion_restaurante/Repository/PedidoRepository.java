package com.Eugeo.gestion_restaurante.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Eugeo.gestion_restaurante.Entity.EstadoPedido;
import com.Eugeo.gestion_restaurante.Entity.Pedido;
import com.Eugeo.gestion_restaurante.Entity.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    //filtrar por estado   
    List<Pedido> findByEstado(EstadoPedido estado);
    
    //filtrar por mesero
    List<Pedido> findByUsuarioId(Usuario usuario);

    //Pedidos por fechas
    List<Pedido> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    //Total de ventas diarias
    @Query("SELECT COALESCE(SUM(p.total),0) FROM Pedido p WHERE DATE(p.fecha) = CURRENT_DATE")
    Double totalVentasHoy();

    //Pedidos del día
    @Query("SELECT p FROM Pedido p WHERE DATE(p.fecha) = CURRENT_DATE")
    List<Pedido> pedidosDelDia();
}
