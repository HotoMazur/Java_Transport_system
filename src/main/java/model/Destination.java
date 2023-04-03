package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Destination {
    private int id;
    private String startCity;
    private long startLn;
    private long startLat;
    private String endCity;
    private long endLn;
    private long endLat;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private List<CheckPoint> checkPointList;
    private Truck truck;
    private Manager responsibleManager;
    private Cargo cargo;

    public Destination(String startCity, long startLn, long startLat, String endCity, long endLn, long endLat) {
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
        //this.responsibleManager = responsibleManager;  List<Manager> responsibleManager
    }

    public Destination(String startCity, long startLn, long startLat, String endCity, long endLn, long endLat, Truck truck, Manager responsibleManager, Cargo cargo) {
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
        //this.checkPointList = checkPointList; //  List<CheckPoint> checkPointList,
        this.truck = truck;
        this.responsibleManager = responsibleManager;
        this.cargo = cargo;
    }

    public Destination(int id, String startCity, long startLn, long startLat, String endCity, long endLn, long endLat) {
        this.id = id;
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                ", startCity='" + startCity + '\'' +
                ", startLn=" + startLn +
                ", startLat=" + startLat +
                ", endCity='" + endCity + '\'' +
                ", endLn=" + endLn +
                ", endLat=" + endLat;

    }
}
