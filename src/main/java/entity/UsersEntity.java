package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "transport_system")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "birth_date")
    private Date birthDate;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic
    @Column(name = "med_date")
    private Date medDate;
    @Basic
    @Column(name = "med_num")
    private String medNum;
    @Basic
    @Column(name = "driver_license")
    private String driverLicense;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "employment_date")
    private Date employmentDate;
    @Basic
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Basic
    @Column(name = "usertype")
    private String usertype;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getMedDate() {
        return medDate;
    }

    public void setMedDate(Date medDate) {
        this.medDate = medDate;
    }

    public String getMedNum() {
        return medNum;
    }

    public void setMedNum(String medNum) {
        this.medNum = medNum;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersEntity that = (UsersEntity) o;
        return id == that.id && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(birthDate, that.birthDate) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(medDate, that.medDate) && Objects.equals(medNum, that.medNum) && Objects.equals(driverLicense, that.driverLicense) && Objects.equals(email, that.email) && Objects.equals(employmentDate, that.employmentDate) && Objects.equals(isAdmin, that.isAdmin) && Objects.equals(usertype, that.usertype);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, surname, birthDate, phoneNumber, medDate, medNum, driverLicense, email, employmentDate, isAdmin, usertype);
    }
}
