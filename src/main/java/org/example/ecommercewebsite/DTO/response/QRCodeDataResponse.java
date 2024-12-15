/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.DTO.response;

/**
 *
 * @author ASUS
 */
public class QRCodeDataResponse {
    public int acpId;
    public String accountName;
    public String qrCode;
    public String qrDataURL;

    public QRCodeDataResponse() {
    }

    public QRCodeDataResponse(int acpId, String accountName, String qrCode, String qrDataURL) {
        this.acpId = acpId;
        this.accountName = accountName;
        this.qrCode = qrCode;
        this.qrDataURL = qrDataURL;
    }

    public int getAcpId() {
        return acpId;
    }

    public void setAcpId(int acpId) {
        this.acpId = acpId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrDataURL() {
        return qrDataURL;
    }

    public void setQrDataURL(String qrDataURL) {
        this.qrDataURL = qrDataURL;
    }
}
