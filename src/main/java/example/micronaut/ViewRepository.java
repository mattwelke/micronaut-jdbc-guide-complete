package example.micronaut;

import example.micronaut.domain.View;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

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
}