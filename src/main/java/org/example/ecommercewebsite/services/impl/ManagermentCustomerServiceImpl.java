package org.example.ecommercewebsite.services.impl;

import org.example.ecommercewebsite.DAO.IManagermentCustomerDAO;
import org.example.ecommercewebsite.DAO.Impl.ManagermentCustomerDAOImpl;
import org.example.ecommercewebsite.DTO.request.CustomerRequest;
import org.example.ecommercewebsite.DTO.response.CustomerResponse;
import org.example.ecommercewebsite.business.Customer;
import org.example.ecommercewebsite.Mapper.CustomerMapper;
import org.example.ecommercewebsite.config.UtilsEmail;
import org.example.ecommercewebsite.services.IManagermentCustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManagermentCustomerServiceImpl implements IManagermentCustomerService {  // Đảm bảo tên lớp là CustomerServiceImpl

    private IManagermentCustomerDAO customerDAO = new ManagermentCustomerDAOImpl();
    private CustomerMapper customerMapper = new CustomerMapper();
    // Constructor với tên khớp với tên lớp

    @Override
    public List<CustomerResponse> getAllCustomers(CustomerRequest reqDTO) {
        List<Customer> customers = customerDAO.getAllCustomer(reqDTO);
        List<CustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerResponse dto = customerMapper.convertToDTO(customer);
            customerResponses.add(dto);
        }
        return customerResponses;
    }

    @Override
    public void lockCustomerStatus(List<Long> customerIds, String reason) {
        // Cập nhật trạng thái hàng loạt
        customerDAO.updateCustomerStatus(customerIds, "InActive");

        // Gửi email thông báo song song
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Pool với 10 luồng
        for (Long customerId : customerIds) {
            executorService.submit(() -> {
                Customer customer = customerDAO.findById(customerId);
                if (customer != null) {
                    String subject = "Furniture Shop";
                    String content = "<h3>Furniture Shop</h3>"
                            + "<p><b>Thông báo khóa tài khoản tại Furniture Shop</b></p>"
                            + "<p>Tài khoản của bạn đã bị khóa với lý do sau:</p>"
                            + "<p><i>" + reason + "</i></p>"
                            + "<p><b>Nếu có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi:</b></p>"
                            + "<ul>"
                            + "<li>Email: <a href='mailto:furnitureshop267@gmail.com'>furnitureshop267@gmail.com</a></li>"
                            + "<li>Số điện thoại: <a href='tel:0339263066'><b>0339263066</b></a></li>"
                            + "</ul>"
                            + "<p>Trân trọng,<br>Furniture Shop</p>";

                    UtilsEmail.sendEmail(customer.getEmail(), subject, content);
                }
            });
        }
        executorService.shutdown(); // Đảm bảo dừng ExecutorService sau khi hoàn thành
    }


    @Override
    public void unlockCustomerStatus(List<Long> customerIds) {
        customerDAO.updateCustomerStatus(customerIds, "Active");

        // Gửi email thông báo song song
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Pool với 10 luồng
        for (Long customerId : customerIds) {
            executorService.submit(() -> {
                Customer customer = customerDAO.findById(customerId);
                if (customer != null) {
                    String subject = "Furniture Shop";
                    String content = "<h3>Furniture Shop</h3>"
                            + "<p><b>Xin chào quý khách,</b></p>"
                            + "<p>Chúng tôi xin thông báo rằng tài khoản của bạn trên Furniture Shop đã được mở khóa thành công.</p>"
                            + "<p>Chúng tôi chân thành xin lỗi nếu quyết định khóa tài khoản trước đây đã gây ra bất kỳ bất tiện nào cho bạn. "
                            + "Sau khi xem xét lại, chúng tôi nhận thấy rằng việc khóa tài khoản có thể là do nhầm lẫn hoặc chưa đủ căn cứ.</p>"
                            + "<p>Tài khoản của bạn đã được kích hoạt lại, và bạn có thể tiếp tục truy cập và sử dụng tất cả các dịch vụ của Furniture Shop.</p>"
                            + "<p><b>Nếu bạn cần hỗ trợ thêm hoặc có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua:</b></p>"
                            + "<ul>"
                            + "<li>Email hỗ trợ: <a href='mailto:furnitureshop267@gmail.com'>furnitureshop267@gmail.com</a></li>"
                            + "<li>Số điện thoại hỗ trợ: <a href='tel:0339263066'><b>0339263066</b></a></li>"
                            + "</ul>"
                            + "<p>Chúng tôi rất mong nhận được sự thông cảm của bạn và hy vọng sẽ tiếp tục được phục vụ bạn trong tương lai.</p>"
                            + "<p>Trân trọng,<br>Furniture Shop</p>";
                    UtilsEmail.sendEmail(customer.getEmail(), subject, content);
                }
            });
        }
        executorService.shutdown(); // Đảm bảo dừng ExecutorService sau khi hoàn thành
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        Customer customer = customerDAO.findById(customerId);
        CustomerResponse responseDTO = new CustomerResponse();
        responseDTO= customerMapper.convertToDTO(customer);
        return responseDTO;
    }
}


