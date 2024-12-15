/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.DTO.request;

/**
 *
 * @author ASUS
 */
public class QRCodeRequest {
    public long accountNo;
    public String accountName;
    public int acqId;
    public int amount;
    public String addInfo;
    public String format;
    public String template;

    public QRCodeRequest() {
    }

    public QRCodeRequest(long accountNo, String accountName, int acqId, int amount, String addInfo, String format, String template) {
        this.accountNo = accountNo;
        this.accountName = accountName;
        this.acqId = acqId;
        this.amount = amount;
        this.addInfo = addInfo;
        this.format = format;
        this.template = template;
    }

    public long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(long accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getAcqId() {
        return acqId;
    }

    public void setAcqId(int acqId) {
        this.acqId = acqId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
