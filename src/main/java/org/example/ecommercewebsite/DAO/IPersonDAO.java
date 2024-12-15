package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Person;

public interface IPersonDAO {
    public void savePerson(Person person);
    public Person getPersonByIdOrDefault(Long personID);
    public void updatePerson(Person person);
    public void deletePerson(Long personID);
    public Person getPersonById(Long personID);
    public boolean emailExists(String email);
    public boolean updatePassword(String email, String newPassword);
}
