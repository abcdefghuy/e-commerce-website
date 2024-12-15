/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.DTO.request;

/**
 *
 * @author ASUS
 */
public class DatumRequest {
    public int id;
    public String name;
    public String code;
    public String bin;
    public String shortName;
    public String logo;
    public int transferSupported;
    public int lookupSupported;
    public String short_name;
    public int support;
    public int isTransfer;
    public String swift_code;
    
    public DatumRequest() {
    }
    
    public DatumRequest(int id, String name, String code, String bin, String shortName, String logo, int transferSupported, int lookupSupported, String short_name, int support, int isTransfer, String swift_code) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.bin = bin;
        this.shortName = shortName;
        this.logo = logo;
        this.transferSupported = transferSupported;
        this.lookupSupported = lookupSupported;
        this.short_name = short_name;
        this.support = support;
        this.isTransfer = isTransfer;
        this.swift_code = swift_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getTransferSupported() {
        return transferSupported;
    }

    public void setTransferSupported(int transferSupported) {
        this.transferSupported = transferSupported;
    }

    public int getLookupSupported() {
        return lookupSupported;
    }

    public void setLookupSupported(int lookupSupported) {
        this.lookupSupported = lookupSupported;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public int getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(int isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getSwift_code() {
        return swift_code;
    }

    public void setSwift_code(String swift_code) {
        this.swift_code = swift_code;
    }
}
