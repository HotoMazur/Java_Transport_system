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
public class Driver extends User implements Serializable {
    private LocalDate medCertificateDate;
    private String medCertificateNumber;
    private String driverLicense;

    public Driver(String login, String password, String name, String surname, LocalDate birthdate, String phoneNumber, LocalDate medCertificateDate, String medCertificateNumber, String driverLicense) {
        super(login, password, name, surname, birthdate, phoneNumber);
        this.medCertificateDate = medCertificateDate;
        this.medCertificateNumber = medCertificateNumber;
        this.driverLicense = driverLicense;
    }

    public Driver(int id, String login, String password, String name, String surname, LocalDate birthdate, String phoneNumber, LocalDate medCertificateDate, String medCertificateNumber, String driverLicense) {
        super(id, login, password, name, surname, birthdate, phoneNumber);
        this.medCertificateDate = medCertificateDate;
        this.medCertificateNumber = medCertificateNumber;
        this.driverLicense = driverLicense;
    }


}
