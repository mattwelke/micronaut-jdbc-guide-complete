package example.micronaut;

import example.micronaut.domain.View;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@ExecuteOn(TaskExecutors.IO)  
@Controller("/views")  
public class ViewController {

    protected final ViewRepository repository;

    public ViewController(ViewRepository repository) { 
        this.repository = repository;
    }

    @Get("/{id}") 
    public Optional<View> show(Long id) {
        return repository
                .findById(id); 
    }

    @Put 
    public HttpResponse<?> update(@Body @Valid ViewUpdateCommand command) { 
        repository.update(command.getId(), command.getProductId());
        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getId()).getPath()); 
    }

    @Get("/list") 
    public List<View> list(@Valid Pageable pageable) { 
        return repository.findAll(pageable).getContent();
    }

    @Post 
    public HttpResponse<View> save(@Body("name") @NotBlank String name) {
        View view = repository.save(name);

        return HttpResponse
                .created(view)
                .headers(headers -> headers.location(location(view.getId())));
    }

    @Post("/ex") 
    public HttpResponse<View> saveExceptions(@Body @NotBlank String name) {
        try {
            View view = repository.saveWithException(name);
            return HttpResponse
                    .created(view)
                    .headers(headers -> headers.location(location(view.getId())));
        } catch(DataAccessException e) {
            return HttpResponse.noContent();
        }
    }

    @Delete("/{id}") 
    @Status(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    protected URI location(Long id) {
        return URI.create("/views/" + id);
    }

    protected URI location(View view) {
        return location(view.getId());
    }
}