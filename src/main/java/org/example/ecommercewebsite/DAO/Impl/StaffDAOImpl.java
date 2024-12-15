package org.example.ecommercewebsite.DAO.Impl;

import org.example.ecommercewebsite.DAO.IStaffDAO;
import org.example.ecommercewebsite.data.DBUtil;
import org.example.ecommercewebsite.business.Shift;
import org.example.ecommercewebsite.business.Staff;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffDAOImpl implements IStaffDAO {

    //hàm lấy NV bằng ID
    @Override
    public  Staff getStaffById(Long staffId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try{
            Staff staff = em.find(Staff.class, staffId);
            return staff;
        }
        finally{
            em.close();
        }
    }

    //hàm thêm NV
    @Override
    public void insert(Staff staff) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        try{
            em.persist(staff);
            trans.commit();
        }
        catch(Exception e){
            trans.rollback();
            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }

    //hàm update NV
    @Override
    public void update(Staff staff) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.merge(staff); //merge = update
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    //hàm xóa NV
    @Override
    public void delete(Staff staff) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();
        try {
            em.remove(em.merge(staff)); //remove = delete
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    }

    //hàm lấy tất cả NV
    @Override
    public List<Staff> getAllStaffs() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT s FROM Staff s";
        return em.createQuery(query, Staff.class).getResultList();
    }

    //hàm tính số NV của mỗi ca
    @Override
    public int StaffPerShift(LocalDate date, String shiftName){
        Date shiftDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "SELECT COUNT(s) FROM Staff s JOIN s.listShift shift WHERE shift.shiftDate = :shiftDate and shift.shiftName = :shiftName";
        Long count = em.createQuery(query, Long.class).
                setParameter("shiftDate", shiftDate).
                setParameter("shiftName", shiftName).getSingleResult();
        return count.intValue();
    }

    //hàm lấy Map<ca ngày, số NV ca đó>
    @Override
    public Map<LocalDate, Integer> getStaffPerShiftInMonth(String shiftName, int month, int year) {

        Map<LocalDate, Integer> map = new HashMap<>();
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            map.put(date, StaffPerShift(date, shiftName));
        }
        return map;
    }

    //hàm kiểm tra nhân viên tồn tại hay chưa
    @Override
    public boolean isExisted(String name, String phone){
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "Select Count(s) From Staff s where s.name = :name and s.phone = :phone";
        Long empCount = em.createQuery(query, Long.class)
                .setParameter("name", name)
                .setParameter("phone", phone)
                .getSingleResult();
        return empCount > 0;
    }

    //hàm láy các ca làm của 1 NV trong tháng
    @Override
    public List<Shift> getShiftInMonth(Staff staff, int month, int year) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "Select shift From Staff s Join s.listShift shift Where s.personID = :staffId " +
                "And function('MONTH', shift.shiftDate) = :month " +
                "AND function('YEAR', shift.shiftDate) = :year";
        return em.createQuery(query, Shift.class).setParameter("staffId", staff.getPersonID()).
                setParameter("year", year).
                setParameter("month", month).getResultList();
    }

    //hàm lấy danh sách NV mỗi ca làm việc
    @Override
    public List<Staff> getStaffInShift(LocalDate date, String shiftName) {
        Date shiftDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String query = "Select s From Staff s Join s.listShift shift Where shift.shiftDate = :shiftDate and shift.shiftName = :shiftName";
        return em.createQuery(query, Staff.class).setParameter("shiftDate", shiftDate).
                setParameter("shiftName", shiftName).getResultList();
    }

    @Override
    public Staff getStaffByEmailPass(String email, String password) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String qString = "SELECT s FROM Staff s where s.email = :email and s.password = :password";
        TypedQuery<Staff> q = em.createQuery(qString, Staff.class);
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            Staff staff = q.getSingleResult();
            return staff;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}