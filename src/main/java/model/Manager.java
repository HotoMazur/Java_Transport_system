package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends User implements Serializable {
    private String email;
    private LocalDate employmentDate;
    private boolean isAdmin;


    public Manager(String login, String password, String name, String surname, LocalDate birthdate, String phoneNumber, String email) {
        super(login, password, name, surname, birthdate, phoneNumber);
        this.email = email;
        this.employmentDate = LocalDate.now();
    }

    public Manager(String login, String password, String name, String surname, LocalDate birthdate, String phoneNumber, String email, boolean isAdmin) {
        super(login, password, name, surname, birthdate, phoneNumber);
        this.email = email;
        this.employmentDate = LocalDate.now();
        this.isAdmin = isAdmin;
    }

    public Manager(String login, String password, String name, String surname, LocalDate birthdate, String phoneNumber, String email, LocalDate employmentDate, boolean isAdmin) {
        super(login, password, name, surname, birthdate, phoneNumber);
        this.email = email;
        this.employmentDate = employmentDate;
        this.isAdmin = isAdmin;
    }

    public Manager(int id, String login, String password, String name, String surname, LocalDate birthdate, String phoneNumber, String email, LocalDate employmentDate, boolean isAdmin) {
        super(id, login, password, name, surname, birthdate, phoneNumber);
        this.email = email;
        this.employmentDate = employmentDate;
        this.isAdmin = isAdmin;
    }

}
