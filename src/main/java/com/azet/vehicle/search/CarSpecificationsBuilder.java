package com.azet.vehicle.search;

import com.azet.vehicle.model.Car;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

public class CarSpecificationsBuilder {

    public static Specification<Car> build(List<SearchCriteria> criteriaList) {
        if (criteriaList.isEmpty()) {
            return null;
        }

        List<Specification<Car>> specs = criteriaList.stream()
                .map(CarSpecification::new)
                .collect(Collectors.toList());

        Specification<Car> result = specs.get(0);

        for (int i = 1; i < criteriaList.size(); i++) {
            result = Specification.where(result).or(specs.get(i));
        }

        return result;
    }
}
