package example.micronaut;

import example.micronaut.domain.View;
import example.micronaut.dtos.PopularProductResult;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JdbcRepository(dialect = Dialect.MYSQL) 
public interface ViewRepository extends PageableRepository<View, Long> { 

    View save(@NonNull @NotBlank String productId);

    @Transactional
    default View saveWithException(@NonNull @NotBlank String productId) {
        save(productId);
        throw new DataAccessException("test exception");
    }

    long update(@NonNull @NotNull @Id Long id, @NonNull @NotBlank String productId);

    @Query("SELECT product_id, COUNT(*) AS count FROM `view` GROUP BY product_id ORDER BY count DESC")
    List<PopularProductResult> popularViews();
}