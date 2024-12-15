package org.example.ecommercewebsite.DAO;

import org.example.ecommercewebsite.business.Shift;
import org.example.ecommercewebsite.business.Staff;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IStaffDAO {
    public Staff getStaffById(Long staffId);
    public void insert(Staff staff);
    public void update(Staff staff);
    public void delete(Staff staff);
    public List<Staff> getAllStaffs();
    public int StaffPerShift(LocalDate date, String shiftName);
    public Map<LocalDate, Integer> getStaffPerShiftInMonth(String shiftName, int month, int year);
    public boolean isExisted(String name, String phone);
    public List<Shift> getShiftInMonth(Staff staff, int month, int year);
    public List<Staff> getStaffInShift(LocalDate date, String shiftName);
    public Staff getStaffByEmailPass(String email, String password);
}
