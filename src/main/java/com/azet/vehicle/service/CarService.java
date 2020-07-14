package com.azet.vehicle.service;

import com.azet.vehicle.dto.CarCreateDTO;
import com.azet.vehicle.dto.CarDTO;
import com.azet.vehicle.dto.CarPatchDTO;
import com.azet.vehicle.model.Auction;
import com.azet.vehicle.model.Car;
import com.azet.vehicle.model.Manufacturer;
import com.azet.vehicle.model.Model;
import com.azet.vehicle.repository.AuctionRepository;
import com.azet.vehicle.repository.CarRepository;
import com.azet.vehicle.repository.ManufacturerRepository;
import com.azet.vehicle.repository.ModelRepository;
import com.azet.vehicle.search.CarSpecificationsBuilder;
import com.azet.vehicle.search.SearchCriteria;
import com.azet.vehicle.search.SearchCriteriaBuilder;
import com.azet.vehicle.util.*;
import com.azet.vehicle.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService {
    @Autowired
    ModelRepository modelRepository;

    @Autowired
    AuctionRepository auctionRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ObjectMapper objectMapper;

    public CarDTO saveCar(CarCreateDTO carCreateDTO) {
        log.debug("carCreateDTO {}", carCreateDTO);

        Manufacturer manufacturer = manufacturerRepository
                .findById(carCreateDTO.getManufacturer())
                .orElseThrow(NotFoundException::new);

        Auction auction = auctionRepository
                .findById(carCreateDTO.getAuction())
                .orElseThrow(NotFoundException::new);

        Model model = modelRepository
                .findById(carCreateDTO.getModel())
                .orElseThrow(NotFoundException::new);

        Car car = Car.builder()
                .auction(auction)
                .manufacturer(manufacturer)
                .model(model)
                .type(Car.CarType.of(carCreateDTO.getType()))
                .manufactureYear(carCreateDTO.getManufactureYear())
                .mileage(carCreateDTO.getMileage())
                .fuelType(Car.FuelType.of(carCreateDTO.getFuelType()))
                .vin(carCreateDTO.getVin())
                .basicPrice(carCreateDTO.getBasicPrice())
                .engineSize(carCreateDTO.getEngineSize())
                .country(carCreateDTO.getCountry())
                .build();

        return DTOBuilder.toCarDTO(carRepository.saveAndFlush(car));
    }

    public CarPatchDTO patch(long carId, JsonPatch patch) {
        Optional<Car> car = carRepository.findById(carId);

        car.orElseThrow(NotFoundException::new);
        Car carEntity = car.get();
        CarPatchDTO carPatchDTO = car.map(DTOBuilder::toCarCreateDTO).get();

        return PatchUtil.apply(patch, carPatchDTO, patchedValue -> {
            carEntity.setManufactureYear(patchedValue.getManufactureYear());
            carRepository.saveAndFlush(carEntity);
        });
    }

    public Page<CarDTO> sorted(Pageable pageable) {
        return carRepository.findAll(pageable).map(DTOBuilder::toCarDTO);
    }

    public List<CarDTO> search(String search) {
        List<SearchCriteria> criteriaList = SearchCriteriaBuilder.build(search);
        Specification<Car> specification = CarSpecificationsBuilder.build(criteriaList);

        return carRepository.findAll(specification)
                .stream()
                .map(DTOBuilder::toCarDTO)
                .collect(Collectors.toList());
    }
}
