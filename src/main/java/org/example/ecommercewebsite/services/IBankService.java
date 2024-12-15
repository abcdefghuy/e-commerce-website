package org.example.ecommercewebsite.services;

import org.example.ecommercewebsite.DTO.request.QRCodeRequest;
import org.example.ecommercewebsite.DTO.request.RootRequest;
import org.example.ecommercewebsite.DTO.response.QRCodeResponse;

public interface IBankService {
    public RootRequest fetchBanks();
    public QRCodeResponse createQRCode(QRCodeRequest qrRequest) ;
    }
