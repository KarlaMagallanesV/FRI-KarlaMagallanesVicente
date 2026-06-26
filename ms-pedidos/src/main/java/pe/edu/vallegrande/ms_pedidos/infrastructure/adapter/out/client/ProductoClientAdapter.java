package pe.edu.vallegrande.ms_pedidos.infrastructure.adapter.out.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.vallegrande.ms_pedidos.application.port.out.IProductoClientPort;
import pe.edu.vallegrande.ms_pedidos.domain.model.Producto;
import reactor.core.publisher.Mono;

@Component
public class ProductoClientAdapter implements IProductoClientPort {

    private final WebClient webClient;

    public ProductoClientAdapter(@Value("${servicios.productos-url}") String productosUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(productosUrl)
                .build();
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return webClient.get()
                .uri("/api/productos/{id}", id)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado")))
                .bodyToMono(Producto.class);
    }

    @Override
    public Mono<Producto> decreaseStock(Long id, Integer quantity) {
        return webClient.patch()
                .uri("/api/productos/{id}/decrease-stock?quantity={quantity}", id, quantity)
                .retrieve()
                .onStatus(status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new ResponseStatusException(response.statusCode(), body))))
                .bodyToMono(Producto.class);
    }
}
