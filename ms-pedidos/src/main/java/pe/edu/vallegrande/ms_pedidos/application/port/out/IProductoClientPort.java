package pe.edu.vallegrande.ms_pedidos.application.port.out;

import org.springframework.stereotype.Repository;

import pe.edu.vallegrande.ms_pedidos.domain.model.Producto;
import reactor.core.publisher.Mono;

@Repository
public interface IProductoClientPort {

    Mono<Producto> findById(Long id);

    Mono<Producto> decreaseStock(Long id, Integer quantity);
}
