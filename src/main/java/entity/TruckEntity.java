package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Truck", schema = "transport_system")
public class TruckEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "make")
    private String make;
    @Basic
    @Column(name = "model")
    private String model;
    @Basic
    @Column(name = "year")
    private Integer year;
    @Basic
    @Column(name = "odometer")
    private Double odometer;
    @Basic
    @Column(name = "fuelTankCapacity")
    private Double fuelTankCapacity;
    @Basic
    @Column(name = "tyreType")
    private String tyreType;
    @Basic
    @Column(name = "destination_id")
    private Integer destinationId;

    public TruckEntity(String make, String model, Integer year, Double odometer, Double fuelTankCapacity, String tyreType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.fuelTankCapacity = fuelTankCapacity;
        this.tyreType = tyreType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getOdometer() {
        return odometer;
    }

    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    public Double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(Double fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public String getTyreType() {
        return tyreType;
    }

    public void setTyreType(String tyreType) {
        this.tyreType = tyreType;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckEntity that = (TruckEntity) o;
        return id == that.id && Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects.equals(year, that.year) && Objects.equals(odometer, that.odometer) && Objects.equals(fuelTankCapacity, that.fuelTankCapacity) && Objects.equals(tyreType, that.tyreType) && Objects.equals(destinationId, that.destinationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, year, odometer, fuelTankCapacity, tyreType, destinationId);
    }
}
