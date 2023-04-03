package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public abstract class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String phoneNumber;


    public User(String login, String password, String name, String surname, LocalDate birthdate, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "login='" + login +
                ", name='" + name +
                ", surname='" + surname;
    }
}
