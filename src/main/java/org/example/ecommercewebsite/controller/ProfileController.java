package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DAO.IPersonDAO;
import org.example.ecommercewebsite.DAO.Impl.PersonDAOImpl;
import org.example.ecommercewebsite.business.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.sql.Date;

@WebServlet(name = "ProfileControl", value = "/profile")
@MultipartConfig
public class ProfileController extends HttpServlet {
    private final IPersonDAO personDAO = new PersonDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person person = getPersonFromSession(session);

        if (person == null) {
            response.sendRedirect("login");
            return;
        }

        if (person.getBirthDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(person.getBirthDate());
            request.setAttribute("birthDate", formattedDate);
        }

        request.setAttribute("person", person);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person person = getPersonFromSession(session);

        if (person == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String addressInput = request.getParameter("address");
            String birthDate = request.getParameter("birthDate");

            Part part = request.getPart("profileImage");
            if (part != null && part.getSize() > 0) {
                if (!part.getContentType().startsWith("image/")) {
                    throw new IllegalArgumentException("Invalid file type.");
                }
                person.setAvatar(toByteArray(part.getInputStream()));
            }

            person.setName(name);
            person.setEmail(email);
            person.setPhone(phone);
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setStreet(addressInput);

            if (birthDate != null && !birthDate.isEmpty()) {
                person.setBirthDate(Date.valueOf(birthDate));
            }

            personDAO.updatePerson(person);
            session.setAttribute("person", personDAO.getPersonById(person.getPersonID()));
            response.sendRedirect("profile");
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    private Person getPersonFromSession(HttpSession session) {
        Long personID = (Long) session.getAttribute("personID");
        return personID != null ? personDAO.getPersonById(personID) : null;
    }

    private byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(data)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toByteArray();
    }
}
