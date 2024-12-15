package org.example.ecommercewebsite.business;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;

import javax.persistence.*;
import javax.servlet.http.Part;
import java.util.Base64;

import java.io.Serializable;

@Entity
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob // Chỉ định lưu trữ dữ liệu lớn
    private byte[] data;

    @Transient // Không lưu thuộc tính này vào cơ sở dữ liệu
    private String base64Data;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "furniture_id")
    private Furniture furniture;

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Image(){

    }
    public Image(byte[] data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    // Constructor, getters và setters
    public String getBase64Data() {
        if (this.data != null) {
            String base64String = Base64.getEncoder().encodeToString(this.data);
            return base64String;        }
        return null;  // Trả về null nếu không có dữ liệu
    }
    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return null;
    }

}