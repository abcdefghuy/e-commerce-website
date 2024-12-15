package org.example.ecommercewebsite.controller;

import org.example.ecommercewebsite.DAO.*;
import org.example.ecommercewebsite.DAO.Impl.*;
import org.example.ecommercewebsite.ENumeration.EOrderStatus;
import org.example.ecommercewebsite.services.impl.BankServiceImpl;
import org.example.ecommercewebsite.DTO.request.QRCodeRequest;
import org.example.ecommercewebsite.DTO.response.QRCodeResponse;
import org.example.ecommercewebsite.business.*;
import org.example.ecommercewebsite.config.UtilsEmail;

import javax.servlet.*;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet(name = "PurchaseServlet", value = "/PurchaseServlet")
public class PurchaseController extends HttpServlet {
    ICartDAO cartDAO = new CartDAOImpl();
    ICouponDAO couponDAO = new CouponDAOImpl();
    IPaymentDAO paymentDAO = new PaymentDAOImpl();
    IFurnitureDAO furnitureDAO = new FurnitureDAOImpl();
    IOrderDAO orderDAO = new OrderDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/KhachHang/cart.jsp";
        ServletContext sc = getServletContext();
        String action = request.getParameter("action");
        if(action == null) {
            action = "viewcart";
        }
        HttpSession session = request.getSession(); // không tạo sesion mới nếu có
        Customer customer = (Customer) session.getAttribute("customer");
        if(customer == null) {
            url = "/KhachHang/login.jsp";
            sc.getRequestDispatcher(url).forward(request, response);
            return;  // Dừng thực thi ở đây
        }

        if (action.equals("addtocart")) {
            url = "/shopServlet";
            Long furnitureID = Long.parseLong(request.getParameter("furnitureID"));
            Furniture fur = furnitureDAO.getFurnitureByID(furnitureID);
            boolean check = cartDAO.addToCart(customer, fur);
            if (check) {
                request.setAttribute("message", "Thêm thành công!");
            } else {
                request.setAttribute("message", "Sản phẩm đã có trong giỏ hàng!");
            }
            sc.getRequestDispatcher(url).forward(request, response);
        } else if (action.equals("viewcart")) {
            Cart cart = cartDAO.getCart(customer);

            if(cart == null) {
                cart = new Cart();
                cart.setCustomer(customer);
                cartDAO.insert(cart);
            }
            request.setAttribute("listFurniture", cart.getListFurniture());
            sc.getRequestDispatcher(url).forward(request, response);

        } else if(action.equals("removefromcart")) {
            Long furnitureID = Long.parseLong(request.getParameter("furnitureID"));
            Furniture fur = furnitureDAO.getFurnitureByID(furnitureID);
            Cart cart = cartDAO.removeToCart(customer,fur);
            request.setAttribute("listFurniture", cart.getListFurniture());
            sc.getRequestDispatcher(url).forward(request, response);
        } else if (action.equals("purchase")) { // thiếu coupon !! sửa ở đây
            url = "/KhachHang/checkout.jsp";
            String listID = request.getParameter("listFurnitureID"); //Toàn bộ tên được chọn
            if (listID != null && !listID.isEmpty()) {
                // Chuyển chuỗi thành mảng các ID sản phẩm
                String[] listFurID = listID.split(",");
                List<Furniture> listFurniture= new ArrayList<>();
                int total = 0;
                for (int i = 0; i < listFurID.length; i++) {
                    Furniture fur = furnitureDAO.getFurnitureByID(Long.parseLong(listFurID[i]));
                    listFurniture.add(fur);
                    total += fur.getFurniturePrice();
                }
                List<Coupon> listCoupon = couponDAO.getListCoupon(listFurniture);
                session.setAttribute("listCoupon", listCoupon);
                request.setAttribute("total", total);
                request.setAttribute("listFurniture", listFurniture);
                request.setAttribute("customer", customer);
                sc.getRequestDispatcher(url).forward(request, response);
            }
        } else if (action.equals("QRCODE")) {
            String[] listCategoryID = request.getParameterValues("listCategoryID"); //Toàn bộ tên được chọn
            String[] quantityStrings = request.getParameterValues("quantity");
            int[] quantities = new int[quantityStrings.length];
            for (int i = 0; i < quantityStrings.length; i++) {
                quantities[i] = Integer.parseInt(quantityStrings[i]);
            }
            boolean check = false;
            if (listCategoryID != null && quantities != null) {
                for (int i = 0; i < listCategoryID.length; i++) {
                    List<Furniture> furnitures = furnitureDAO.getFurnitureQuantity(listCategoryID[i], quantities[i]);
                    if (furnitures == null) {
                        check = true; // không đủ hàng
                        break;
                    }
                }
            }
            if (check == true) {
                String flag = "False";
                response.setContentType("text/plain");  // Đảm bảo kiểu phản hồi là văn bản
                response.getWriter().write(flag);  // Trả về kết quả sai báo không đủ hàng
            }
            else
            {
                String accountName = "DANG BA HIEN";
                long accountNumber = 797322626;
                int amount = Integer.parseInt(request.getParameter("amount"));
                String description = request.getParameter("description");
                int acqId = 970422;
                QRCodeRequest QRrequest = new QRCodeRequest(accountNumber, accountName, acqId, amount, description, "text", "print");
                BankServiceImpl bankService = new BankServiceImpl();
                QRCodeResponse QRresponse = bankService.createQRCode(QRrequest);
                QRresponse.data.setAcpId(acqId);
                QRresponse.data.setAccountName(accountName);
                response.setContentType("text/plain");  // Đảm bảo kiểu phản hồi là văn bản
                response.getWriter().write(QRresponse.getData().qrDataURL);  // Trả về URL mã QR
            }

        }else if (action.equals("coupon")){
            String couponCode = request.getParameter("couponCode");
            int total = Integer.parseInt(request.getParameter("total"));
            List<Coupon> listCoupon = (List<Coupon>) session.getAttribute("listCoupon");
            String[] listCategoryID = request.getParameterValues("listCategoryID"); //Toàn bộ tên được chọn
            String[] quantityStrings = request.getParameterValues("quantity");
            int[] quantities = new int[quantityStrings.length];
            for (int i = 0; i < quantityStrings.length; i++) {
                quantities[i] = Integer.parseInt(quantityStrings[i]);
            }
            List<Furniture> list = new ArrayList<>();

            if (listCategoryID != null && quantities != null) {
                for (int i = 0; i < listCategoryID.length; i++) {
                    list.add(furnitureDAO.getFurnitureDiscount(listCategoryID[i]));
                }
            }

            Coupon selectedCoupon = null;
            if(couponCode!=null && !couponCode.isEmpty()) {
                if (listCoupon != null) {
                    for (Coupon coupon : listCoupon) {
                        if (coupon.getCouponID().equals(couponCode)) {
                            selectedCoupon = coupon;
                            break;
                        }
                    }
                }
            }

            double Giamgia = 0;
            if(selectedCoupon != null)
            {
                Giamgia = selectedCoupon.calculateDiscount(list,quantities,total);
            }

            String GiamgiaString = String.valueOf(Giamgia);
            session.setAttribute("giamgia",GiamgiaString);
            System.out.println(GiamgiaString);
            String flag;
            if (selectedCoupon != null) {
                if (selectedCoupon.getUseLimit() <= selectedCoupon.getCurrentUsage()) {
                    flag = "FullUse";
                } else {
                    request.setAttribute("selectedCoupon", selectedCoupon);
                    flag = GiamgiaString;
                }
            } else {
                flag = "NoCoupon";
            }
            response.setContentType("text/plain");
            response.getWriter().write(flag);

        }
        else if (action.equals("payment")) {
            String paymentMethod = request.getParameter("paymentMethod");
            String couponCode = request.getParameter("couponCode");
            String[] listCategoryID = request.getParameterValues("listCategoryID"); //Toàn bộ tên được chọn
            String[] quantityStrings = request.getParameterValues("quantity");
            int[] quantities = new int[quantityStrings.length];
            for (int i = 0; i < quantityStrings.length; i++) {
                quantities[i] = Integer.parseInt(quantityStrings[i]);
            }
            List<Furniture> list = new ArrayList<>();
            boolean check = false;
            if (listCategoryID != null && quantities != null) {
                for (int i = 0; i < listCategoryID.length; i++) {
                    List<Furniture> furnitures = furnitureDAO.getFurnitureQuantity(listCategoryID[i], quantities[i]);
                    if (furnitures != null) {

                        list.addAll(furnitures);
                    }
                    else {
                        check = true; // không đủ hàng
                        break;
                    }
                }
            }
            if (check == true) {
                String flag = "False";
                response.getWriter().write(flag);  // Trả về kết quả sai báo không đủ hàng
            }
            else
            {
                Date orderDate = new Date();
                EOrderStatus orderStatus = EOrderStatus.WAITING_PROCESS;
                Date paymentDate = null;
                Order order = new Order(list,customer,orderDate,orderStatus);
                Payment payment;
                Coupon coupon = null;
                String giamgia = (String) session.getAttribute("giamgia");
                if(couponCode!=null &&!couponCode.isEmpty())
                {
                    coupon = couponDAO.getCouponById(couponCode);
                }
                else
                {
                    giamgia = "0";
                }
                System.out.println(couponCode);
                Double money = Double.parseDouble(request.getParameter("amount"));
                if(paymentMethod.equals("bank"))
                {
                    paymentDate = orderDate;
                }
                payment = new Payment(coupon,paymentDate,money,paymentMethod,order);
                if(orderDAO.insertOrder(order))
                {
                    if (paymentDAO.insert(payment)) {
                        StringBuilder hoadon = new StringBuilder();
                        Object[][] listFur = order.getFurnitureQuantity();
                        for (int i = 0; i < listFur.length; i++) {
                            Furniture furniture = (Furniture) listFur[i][0];
                            int quantity = (int) listFur[i][1];
                            hoadon.append("Tên sản phẩm ").append(furniture.getCategory().getCategoryName())
                                    .append(" - Màu sắc ").append(furniture.getFurnitureColor())
                                    .append(" - Nhà sản xuất ").append(furniture.getCategory().getManufacture())
                                    .append(" - Giá tiền ").append(furniture.getFurniturePrice())
                                    .append(" - Số lượng ").append(quantity).append("<br>");
                        }
                        long discount = 0;
                        if (giamgia != null && !giamgia.isEmpty()) {
                            try {
                                discount = Math.round(Double.parseDouble(giamgia));
                            } catch (NumberFormatException e) {
                                discount = 0;
                            }
                        }
                        long totalPayment = payment.getMoney().longValue();
                        long total = discount + totalPayment;

                        hoadon.append("Tổng cộng: ").append(total).append(" VNĐ <br>");
                        hoadon.append("Giảm giá: ").append(discount).append(" VNĐ <br>");
                        hoadon.append("Tổng thanh toán: ").append(totalPayment).append(" VNĐ");
                        System.out.println(hoadon);

                        ExecutorService executorService = Executors.newFixedThreadPool(1);
                        executorService.submit(() -> {
                            String subject = "Hóa đơn mua hàng!";
                            String content = "Xin chào " + customer.getName() + "<br>"
                                    + "Đây là hóa đơn mua hàng của bạn.<br>" + hoadon;
                            UtilsEmail.sendEmail(customer.getEmail(), subject, content);
                        });
                        executorService.shutdown();

                        String flag = "True";
                        response.getWriter().write(flag);
                    } else {
                        String flag = "Full";
                        response.getWriter().write(flag);
                    }
                } else {
                    String flag = "False";
                    response.getWriter().write(flag);
                }
            }
        }
    }
}