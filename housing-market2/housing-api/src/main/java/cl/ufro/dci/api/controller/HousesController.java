package cl.ufro.dci.api.controller;

import cl.ufro.dci.api.model.House;
import cl.ufro.dci.api.repository.HouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class HousesController {

    private final HouseRepository houseRepository;

    public HousesController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @PostMapping(value = "/houses")
    public House saveHouse(@RequestBody House house) {
        houseRepository.save(house);
        return house;
    }

    @GetMapping(value = "/houses")
    public Iterable<House> getHouses() {
        return houseRepository.findAll();
    }

    @GetMapping(value ="/houses/search")
    public ResponseEntity<List<House>> searchHouses(@RequestParam("query") String query){
        List<House> house = houseRepository.searchHouse(query);
        return ResponseEntity.ok(house);
    }
}
