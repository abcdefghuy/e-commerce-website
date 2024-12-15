package org.example.ecommercewebsite.controller.staffManagementController;
import org.example.ecommercewebsite.business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchStaff")
public class SearchEmployeeController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("search-action");
        List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
        List<Staff> searchStaff = new ArrayList<>();

        if(action.equals("search-name")) {
            String searchName = request.getParameter("search-name");
            searchStaff = getStaffByName(listStaff, searchName);
            request.setAttribute("listStaff", searchStaff);
            request.setAttribute("searchName", searchName);
        }
        else if(action.equals("search-filters")) {
            String searchSalary = request.getParameter("search-salary");
            String searchAge = request.getParameter("search-age");
            String searchWorkTime = request.getParameter("search-workTime");
            String searchStatus = request.getParameter("search-status");

            searchStaff = getStaffByFilter(listStaff, searchStatus, searchSalary, searchAge, searchWorkTime);
            request.setAttribute("listStaff", searchStaff);
        }

        request.getRequestDispatcher("/Admin/listStaff.jsp").forward(request, response);
    }

    private List<Staff> getStaffByName(List<Staff> listStaff, String searchName){
        List<Staff> searchStaff = new ArrayList<>();
        for (Staff s : listStaff) {
            if (s.getName().toLowerCase().contains(searchName.toLowerCase())) {
                searchStaff.add(s);
            }
        }
        return searchStaff;
    }

    private List<Staff> getStaffByFilter(List<Staff> listStaff, String searchStatus, String searchSalary, String searchAge, String searchWorkTime) {
        List<Staff> searchStaff = new ArrayList<>();
        double salaryMin=0;
        double salaryMax=999999999;
        switch (searchSalary) {
            case "under10" -> {
                salaryMin = 0.0;
                salaryMax = 10000000;
            }
            case "10to20" -> {
                salaryMin = 10000000;
                salaryMax = 20000000;
            }
            case "over20" -> {
                salaryMin = 20000000;
                salaryMax = 99999999;
            }
        }

        int ageMin=0;
        int ageMax=99;
        switch (searchAge) {
            case "18to25" -> {
                ageMin = 18;
                ageMax = 25;
            }
            case "25to30" -> {
                ageMin = 25;
                ageMax = 30;
            }
            case "over30" -> {
                ageMin = 30;
            }
        }

        int workTimeMin=0;
        int workTimeMax=99;
        switch (searchWorkTime) {
            case "under1" -> {
                workTimeMax = 1;
            }
            case "1to3" -> {
                workTimeMin = 1;
                workTimeMax = 3;
            }
            case "3to5" -> {
                workTimeMin = 3;
                workTimeMax = 5;
            }
            case "over5" -> {
                workTimeMin = 5;
            }
        }

        if(searchStatus.equals("all")){
            for(Staff s : listStaff) {
                if(s.getSalary() >= salaryMin && s.getSalary() <= salaryMax &&
                        s.getAge() >= ageMin && s.getAge() <= ageMax &&
                        s.getWorkTime() >= workTimeMin && s.getWorkTime() <= workTimeMax) {

                    searchStaff.add(s);
                }
            }
        }
        else{
            for(Staff s : listStaff) {
                if( s.getStatus().equals(searchStatus) && s.getSalary() >= salaryMin && s.getSalary() <= salaryMax &&
                        s.getAge() >= ageMin && s.getAge() <= ageMax &&
                        s.getWorkTime() >= workTimeMin && s.getWorkTime() <= workTimeMax) {

                    searchStaff.add(s);
                }
            }
        }

        return searchStaff;
    }
}
