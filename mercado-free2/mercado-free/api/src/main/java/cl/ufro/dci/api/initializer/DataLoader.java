package cl.ufro.dci.api.initializer;

import cl.ufro.dci.api.domain.Product;
import cl.ufro.dci.api.repository.ProductRepository;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;


@Component
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Bean
    public CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            for (int i = 1; i <= 20; i++) {
                Product product = new Product();

                Faker faker = new Faker();
                String name = faker.commerce().productName();
                String description = faker.company().catchPhrase();
                long price = ThreadLocalRandom.current().nextLong(1L, 500L + 1);
                String brand = faker.company().buzzword();
                product.setProName(name);
                product.setProDescription(description);
                product.setProPrice(price);
                product.setProBrand(brand);
                product.setProImage("https://picsum.photos/seed/" + i + "/200/100");
                repository.save(product);
                log.info("Producto {} Creado", i);
            }
        };
    }

}