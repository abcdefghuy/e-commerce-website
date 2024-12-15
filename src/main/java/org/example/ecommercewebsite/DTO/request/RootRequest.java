/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.DTO.request;

import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class RootRequest {
    public String code;
    public String desc;
    public ArrayList<DatumRequest> data;

    public RootRequest() {
    }

    public RootRequest(String code, String desc, ArrayList<DatumRequest> data) {
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

    public ArrayList<DatumRequest> getData() {
        return data;
    }

    public void setData(ArrayList<DatumRequest> data) {
        this.data = data;
    }
}