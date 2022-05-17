package example.micronaut.dtos;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record PopularProductResult(
        String productId,
        Long count) {
}
