package org.example.ecommercewebsite.config;

import org.modelmapper.ModelMapper;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ModelMapperConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Khởi tạo ModelMapper và lưu trữ vào ServletContext
        ModelMapper modelMapper = new ModelMapper();
        ServletContext context = sce.getServletContext();
        context.setAttribute("modelMapper", modelMapper);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Giải phóng tài nguyên nếu cần
    }
}
