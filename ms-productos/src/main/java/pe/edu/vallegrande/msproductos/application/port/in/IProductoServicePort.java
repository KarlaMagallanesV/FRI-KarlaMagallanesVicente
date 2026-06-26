package pe.edu.vallegrande.msproductos.application.port.in;

import pe.edu.vallegrande.msproductos.domain.model.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoServicePort {

    Flux<Producto> findAll();
    Mono<Producto> findById(Long id);
    Mono<Producto> create(Producto producto);
    Mono<Producto> update(Long id, Producto producto);
    Mono<Producto> decreaseStock(Long id, Integer quantity);
    Mono<Void> delete(Long id);
}
