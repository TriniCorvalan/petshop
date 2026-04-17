package com.example.petshop.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.petshop.model.Product;
import com.example.petshop.repository.ProductRepository;

/**
 * Carga datos iniciales de productos y ordenes al arrancar la aplicación.
 * <p>
 * Usa {@link ApplicationReadyEvent} en lugar de {@link org.springframework.boot.ApplicationRunner}
 * para ejecutarse cuando el contexto está listo y JPA/Hibernate ya ha aplicado el esquema
 * ({@code ddl-auto}), evitando ORA-00942 al consultar tablas aún inexistentes (p. ej. con Oracle
 * o reinicios de DevTools).
 * <p>
 * Si la tabla {@code products} ya contiene filas, no inserta nada para evitar duplicados
 * (útil si se cambia {@code spring.jpa.hibernate.ddl-auto} de {@code create} a
 * {@code update} y se conservan datos entre reinicios).
 */
@Component
@DependsOn("entityManagerFactory")
public class ProductDataLoader {

    private final ProductRepository productRepository;

    public ProductDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Crea 10 productos por defecto (números {@code 1}–{@code 10}) solo si el
     * repositorio está vacío. Precio base 1.999–10.999 según producto.
     */
    @Order(Ordered.LOWEST_PRECEDENCE)
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void seedProducts() {
        if (productRepository.count() > 0) {
            return;
        }
        for (int i = 1; i <= 10; i++) {
            Long productId = Long.valueOf(i);
            String productName = "Product " + i;
            double productPrice = 1_000 * i + 999;
            Product product = new Product();
            product.setId(productId);
            product.setName(productName);
            product.setPrice(productPrice);
            productRepository.save(product);
        }
    }
}
