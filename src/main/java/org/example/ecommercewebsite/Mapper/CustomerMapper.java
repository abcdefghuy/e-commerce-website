package org.example.ecommercewebsite.Mapper;


import org.example.ecommercewebsite.DTO.response.CustomerByIdResponse;
import org.example.ecommercewebsite.DTO.response.CustomerResponse;
import org.example.ecommercewebsite.business.Address;
import org.example.ecommercewebsite.business.Customer;
import org.modelmapper.ModelMapper;

import java.util.Base64;

public class CustomerMapper {

    private static ModelMapper modelMapper = new ModelMapper();

    // Phương thức chuyển đổi từ Customer Entity sang CustomerResponseDTO
    public static CustomerResponse convertToDTO(Customer customer) {
        CustomerResponse dto = modelMapper.map(customer, CustomerResponse.class);
        if (customer.getAddress() != null) {
            Address address = customer.getAddress();
            String fullAddress = address.getStreet() + ", " + address.getCity() + ", " + address.getProvince() + ", " + address.getCountry();
            dto.setAddress(fullAddress);
        }
        // Chuyển đổi mảng byte của feedbackImage thành chuỗi Base64
        if (customer.getAvatar() != null) {
            String base64Image = Base64.getEncoder().encodeToString(customer.getAvatar());
            dto.setAvatar(base64Image);
        }
        return dto;
    }
    public static CustomerByIdResponse convertToDTOCustomer (Customer customer) {
        CustomerByIdResponse dto = modelMapper.map(customer, CustomerByIdResponse.class);
        // Chuyển đổi mảng byte của feedbackImage thành chuỗi Base64
        if (customer.getAvatar() != null) {
            String base64Image = Base64.getEncoder().encodeToString(customer.getAvatar());
            dto.setAvatar(base64Image);
        }
        return dto;
    }
}

