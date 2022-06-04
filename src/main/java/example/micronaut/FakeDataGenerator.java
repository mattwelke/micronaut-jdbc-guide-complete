package example.micronaut;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.micronaut.domain.View;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;

/**
 * Creates fake popular product data and writes it to the database. This
 * simulates a stream of events coming in
 * constantly as the application runs. Either the application itself or a
 * separate service could be responsible for
 * streaming the events into the database.
 */
@Singleton
public class FakeDataGenerator {

    private static List<String> availableProductIds;

    private static final Logger LOG = LoggerFactory.getLogger(FakeDataGenerator.class);

    protected final ViewRepository repository;

    public FakeDataGenerator(ViewRepository repository) {
        availableProductIds = new ArrayList<>();
        availableProductIds.add("abc123");
        availableProductIds.add("def456");
        availableProductIds.add("ghi789");

        this.repository = repository;
    }

    @Scheduled(fixedDelay = "1s")
    public void run() {
        LOG.info("Inserting fake view.");

        var view = new View();
        view.setProductId(generateProductId());
        repository.save(view);

        LOG.info("Inserted fake view:" + view);
    }

    private static String generateProductId() {
        Random rand = new Random();
        return availableProductIds.get(rand.nextInt(availableProductIds.size()));
    }
}