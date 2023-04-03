package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Checkpoint", schema = "transport_system")
public class CheckpointEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "longStop")
    private Byte longStop;
    @Basic
    @Column(name = "dateArrived")
    private Date dateArrived;
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

    public Byte getLongStop() {
        return longStop;
    }

    public void setLongStop(Byte longStop) {
        this.longStop = longStop;
    }

    public Date getDateArrived() {
        return dateArrived;
    }

    public void setDateArrived(Date dateArrived) {
        this.dateArrived = dateArrived;
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
        CheckpointEntity that = (CheckpointEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(longStop, that.longStop) && Objects.equals(dateArrived, that.dateArrived) && Objects.equals(destinationId, that.destinationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, longStop, dateArrived, destinationId);
    }
}
