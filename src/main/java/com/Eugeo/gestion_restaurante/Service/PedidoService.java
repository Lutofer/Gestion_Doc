package com.Eugeo.gestion_restaurante.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Eugeo.gestion_restaurante.Entity.DetallePedido;
import com.Eugeo.gestion_restaurante.Entity.EstadoPedido;
import com.Eugeo.gestion_restaurante.Entity.Mesa;
import com.Eugeo.gestion_restaurante.Entity.Pedido;
import com.Eugeo.gestion_restaurante.Entity.Producto;
import com.Eugeo.gestion_restaurante.Repository.PedidoRepository;
import com.Eugeo.gestion_restaurante.Repository.ProductoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final MesaService mesaService;

    @Transactional
    public Pedido crearPedido(Pedido pedido) {

        Mesa mesa = mesaService.findById(pedido.getMesa().getId());
        mesaService.ocuparMesa(mesa);

        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal(0.0);

        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido agregarProducto(Long pedidoId, Long productoId, Integer cantidad) {

        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Pedido no editable");
        }

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }

        producto.setStock(producto.getStock() - cantidad);

        BigDecimal subtotal = producto.getPrecio()
                .multiply(BigDecimal.valueOf(cantidad));

        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(subtotal);

        pedido.getDetalles().add(detalle);

        recalcularTotal(pedido);

        return pedidoRepository.save(pedido);
    }

    public Pedido cambiarEstado(Long id, EstadoPedido estado) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        pedido.setEstado(estado);

        if (estado == EstadoPedido.PAGADO) {
            mesaService.liberarMesa(pedido.getMesa());
        }

        return pedidoRepository.save(pedido);
    }

    private void recalcularTotal(Pedido pedido) {

        BigDecimal total = pedido.getDetalles().stream()
                .map(DetallePedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total.doubleValue());
    }

    public Double ventasHoy() {
        return pedidoRepository.totalVentasHoy();
    }
}