package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CheckPoint {
    private int id;
    private String title;
    private boolean longStop;
    private LocalDate dateArrived;



    public CheckPoint(String title, boolean longStop, LocalDate dateArrived) {
        this.title = title;
        this.longStop = longStop;
        this.dateArrived = dateArrived;

    }

    @Override
    public String toString() {
        String stop;
        if (!longStop) {
            stop = ", Short stop";
        } else {
            stop = ", Long stop";
        }
        return "title='" + title + '\'' + stop + ", dateArrived=" + dateArrived;

    }

}
