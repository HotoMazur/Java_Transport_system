package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Destination", schema = "transport_system")
public class DestinationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "startCity")
    private String startCity;
    @Basic
    @Column(name = "startLn")
    private Long startLn;
    @Basic
    @Column(name = "startLat")
    private Long startLat;
    @Basic
    @Column(name = "endCity")
    private String endCity;
    @Basic
    @Column(name = "endLn")
    private Long endLn;
    @Basic
    @Column(name = "endLat")
    private Long endLat;
    @Basic
    @Column(name = "dateCreated")
    private Date dateCreated;
    @Basic
    @Column(name = "dateUpdated")
    private Date dateUpdated;
    @Basic
    @Column(name = "truck_id")
    private Integer truckId;
    @Basic
    @Column(name = "cargo_id")
    private Integer cargoId;
    @Basic
    @Column(name = "manager_id")
    private Integer managerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public Long getStartLn() {
        return startLn;
    }

    public void setStartLn(Long startLn) {
        this.startLn = startLn;
    }

    public Long getStartLat() {
        return startLat;
    }

    public void setStartLat(Long startLat) {
        this.startLat = startLat;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public Long getEndLn() {
        return endLn;
    }

    public void setEndLn(Long endLn) {
        this.endLn = endLn;
    }

    public Long getEndLat() {
        return endLat;
    }

    public void setEndLat(Long endLat) {
        this.endLat = endLat;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public Integer getCargoId() {
        return cargoId;
    }

    public void setCargoId(Integer cargoId) {
        this.cargoId = cargoId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinationEntity that = (DestinationEntity) o;
        return id == that.id && Objects.equals(startCity, that.startCity) && Objects.equals(startLn, that.startLn) && Objects.equals(startLat, that.startLat) && Objects.equals(endCity, that.endCity) && Objects.equals(endLn, that.endLn) && Objects.equals(endLat, that.endLat) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateUpdated, that.dateUpdated) && Objects.equals(truckId, that.truckId) && Objects.equals(cargoId, that.cargoId) && Objects.equals(managerId, that.managerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startCity, startLn, startLat, endCity, endLn, endLat, dateCreated, dateUpdated, truckId, cargoId, managerId);
    }
}
