package org.example.ecommercewebsite.business;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long personID;

    private String name;
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESSID") // Tên cột khóa ngoại trùng với tên khóa chính của Address
    private Address address;
    private String email;
    private String password;
    private String phone;
    @Lob
    private byte[] avatar;

    public Person() {
    }

    public Person (String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    @Override
    public String toString() {
        return "{" +
                "\"personID\":" + personID +
                ", \"name\":\"" + name + "\"" +
                ", \"birthDate\":\"" + birthDate + "\"" +
                ", \"email\":\"" + email + "\"" +
                ", \"phone\":\"" + phone + "\"" +
                ", \"address\":{\"street\":\"" + (address != null ? address.getStreet() : "") +
                "\", \"city\":\"" + (address != null ? address.getCity() : "") +
                "\", \"state\":\"" + (address != null ? address.getProvince() : "") +
                "}";
    }

    public Person(String name, Date birthDate, Address address, String email, String password, String phone, byte[] avatar) {
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.avatar = avatar;
    }


    public Long getPersonID() {
        return personID;
    }

    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true; // Ký tự đặc biệt
            }
        }

        // Kiểm tra tất cả tiêu chí
        return hasLowercase && hasUppercase && hasDigit && hasSpecialChar;
    }

}