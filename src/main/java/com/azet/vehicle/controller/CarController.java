package com.azet.vehicle.controller;

import com.azet.vehicle.dto.CarCreateDTO;
import com.azet.vehicle.dto.CarDTO;
import com.azet.vehicle.dto.CarPatchDTO;
import com.azet.vehicle.repository.CarRepository;
import com.azet.vehicle.service.CarService;
import com.azet.vehicle.util.DTOBuilder;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"","v1"})
public class CarController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarService carService;

    @GetMapping("/car")
    List<CarDTO> getAllCars() {
        return DTOBuilder.toCarDTO(carRepository.findAll());
    }

    @GetMapping("/car/{id}")
    CarDTO getCarById(@PathVariable("id") long carId) {
        return DTOBuilder.toCarDTO(carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car Not Found")));
    }

    @PostMapping(value = "/car", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    CarDTO createCar(@Validated @RequestBody CarCreateDTO carDTO) {
        return carService.saveCar(carDTO);
    }

    @PatchMapping(path = "/car/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<CarPatchDTO> updateCustomer(@PathVariable long id, @Valid  @RequestBody JsonPatch patch) {
        CarPatchDTO car = carService.patch(id, patch);
        return ResponseEntity.ok(car);
    }

    @PostMapping(path = "/sortedCars", consumes = "application/json", produces = "application/json")
    public Page<CarDTO> sortedCars(Pageable pageable) {
        return carService.sorted(pageable);
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public List<CarDTO> search(@RequestParam(value = "filters") String search) {
        return carService.search(search);
    }
}
