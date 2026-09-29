package cl.ufro.dci.api.repository;

import cl.ufro.dci.api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Product repository.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Override
    Optional<Product> findById(Long id);
    List<Product> findAll();
    List<Product> findByProNameContainingIgnoreCaseOrProDescriptionContainingIgnoreCaseOrProBrandContainingIgnoreCase(String proName, String proDescription, String proBrand);
    Product save(Product product);


}