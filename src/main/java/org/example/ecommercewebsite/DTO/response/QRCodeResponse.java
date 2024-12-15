/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.DTO.response;

/**
 *
 * @author ASUS
 */
public class QRCodeResponse {
    public String code;
    public String desc;
    public QRCodeDataResponse data;

    public QRCodeResponse() {
    }

    public QRCodeResponse(String code, String desc, QRCodeDataResponse data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public QRCodeDataResponse getData() {
        return data;
    }

    public void setData(QRCodeDataResponse data) {
        this.data = data;
    }
    
}
