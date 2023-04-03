package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Truck {

    private int id;
    private String make;
    private String model;
    private int year;
    private double odometer;
    private double fuelTankCapacity;
    private TyreType tyreType;
    private Integer destinationId;
    private boolean displayModel;


    public Truck(String make, String model, int year, double odometer, double fuelTankCapacity, TyreType tyreType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.fuelTankCapacity = fuelTankCapacity;
        this.tyreType = tyreType;
        this.displayModel = true;
    }

    public Truck(int id, String make, String model, int year, double odometer, double fuelTankCapacity, TyreType tyreType) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.fuelTankCapacity = fuelTankCapacity;
        this.tyreType = tyreType;
        this.displayModel = true;
    }

    public void setDisplayModel(boolean displayModel){
        this.displayModel = displayModel;
    }

    @Override
    public String toString() {
        if (displayModel){
            return "make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    ", year=" + year +
                    ", odometer=" + odometer +
                    ", fuelTankCapacity=" + fuelTankCapacity +
                    ", tyreType=" + tyreType;
        } else {
            return "make = " + make +
                    "model = " + model;
        }
    }

}
