package cl.ufro.dci.api.repository;

import cl.ufro.dci.api.model.House;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Transactional
public interface HouseRepository extends CrudRepository<House, Long> {

    @Query("SELECT p FROM House p WHERE " +
            "p.houTitle LIKE CONCAT('%',:query, '%')" +
            "Or p.houDescription LIKE CONCAT('%', :query, '%')")
    List<House> searchHouse(String query);
}
