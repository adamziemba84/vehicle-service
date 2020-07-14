package com.azet.vehicle.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cars")
@Builder
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="auction_id", nullable=false)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name="manufacturer_id", nullable=false)
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name="model_id", nullable=false)
    private Model model;

    @Column(name = "car_type")
    @Enumerated(EnumType.ORDINAL)
    private CarType type;

    @Column(name = "manufacture_year")
    private int manufactureYear; // 2014

    @Column(name = "mileage")
    private int mileage; // 15000

    @Column(name = "fuel_type")
    @Enumerated(EnumType.ORDINAL)
    private FuelType fuelType;

    @Column(name = "vin")
    private String vin;

    @Column(name = "basic_price")
    private int basicPrice;

    @Column(name = "engine_size")
    private int engineSize;

    @Column(name = "country")
    private String country;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @RequiredArgsConstructor
    public enum CarType {
        COMPACT(0),
        SEDAN(1),
        COMBI(2),
        SUV(3);

        @Getter
        private final int value;

        public static CarType of(int value) {
            return Arrays.stream(values())
                    .filter(reason -> reason.getValue() == value)
                    .findFirst()
                    .orElse(null);
        }
    }

    @RequiredArgsConstructor
    public enum FuelType {
        DIESEL(0),
        petrol(1),
        GAS(2);

        @Getter
        private final int value;

        public static FuelType of(int value) {
            return Arrays.stream(values())
                    .filter(reason -> reason.getValue() == value)
                    .findFirst()
                    .orElse(null);
        }
    }
}
