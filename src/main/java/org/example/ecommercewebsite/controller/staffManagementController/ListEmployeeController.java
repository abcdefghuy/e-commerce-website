package org.example.ecommercewebsite.controller.staffManagementController;
import org.example.ecommercewebsite.DAO.IStaffDAO;
import org.example.ecommercewebsite.DAO.Impl.StaffDAOImpl;
import org.example.ecommercewebsite.business.Staff;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet("/listStaff")
public class ListEmployeeController extends HttpServlet {
    IStaffDAO staffDAO = new StaffDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<Staff> listStaff = (List<Staff>) session.getAttribute("listStaff");
        if (listStaff == null) {
            listStaff = staffDAO.getAllStaffs();
            session.setAttribute("listStaff", listStaff);
        }
        request.setAttribute("listStaff", listStaff);
        request.getRequestDispatcher("/Admin/listStaff.jsp").forward(request, response);
    }
}
