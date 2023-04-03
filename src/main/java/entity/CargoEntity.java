package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Cargo", schema = "transport_system")
public class CargoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "dateCreated")
    private Date dateCreated;
    @Basic
    @Column(name = "dateUpdated")
    private Date dateUpdated;
    @Basic
    @Column(name = "weight")
    private Double weight;
    @Basic
    @Column(name = "cargoType")
    private String cargoType;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "customer")
    private String customer;
    @Basic
    @Column(name = "destination_id")
    private Integer destinationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCargoType() {
        return cargoType;
    }

    public void setCargoType(String cargoType) {
        this.cargoType = cargoType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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
        CargoEntity that = (CargoEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(dateCreated, that.dateCreated) && Objects.equals(dateUpdated, that.dateUpdated) && Objects.equals(weight, that.weight) && Objects.equals(cargoType, that.cargoType) && Objects.equals(description, that.description) && Objects.equals(customer, that.customer) && Objects.equals(destinationId, that.destinationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, dateCreated, dateUpdated, weight, cargoType, description, customer, destinationId);
    }
}
