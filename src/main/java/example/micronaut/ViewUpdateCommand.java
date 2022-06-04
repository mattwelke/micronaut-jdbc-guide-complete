package example.micronaut;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Introspected 
public class ViewUpdateCommand {
    @NotNull
    private final Long id;

    @NotBlank
    private final String productId;

    public ViewUpdateCommand(Long id, String productId) {
        this.id = id;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

}