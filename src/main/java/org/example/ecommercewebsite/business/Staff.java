/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this
 */
package org.example.ecommercewebsite.business;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
@Entity
public class Staff extends Person {

    private String status;
    private double salary;

    @Temporal(TemporalType.DATE)
    private Date workDate;

    @ManyToMany
    private List<Shift> listShift;
    public Staff() {
        super();
    }
    public Staff(String name, Date birthDate, Address address, String email, String password, String phone, byte[] avatar, double salary, Date workDate) {
        super(name, birthDate, address, email, password, phone, avatar);
        this.salary = salary;
        this.workDate = workDate;
        this.status = "Active";
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public List<Shift> getListShift() {
        return listShift;
    }

    public void setListShift(List<Shift> listShift) {
        this.listShift = listShift;
    }

    public int getAge(){
        LocalDate birthDateLocal = getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDateLocal, currentDate).getYears();
    }

    public int getWorkTime(){
        LocalDate birthDateLocal = getWorkDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDateLocal, currentDate).getYears();
    }

    @Override
    public String toString() {
        return getPersonID() + " " + getName() + " " + getBirthDate() + " " + getAddress() + " " + getEmail();
    }
}