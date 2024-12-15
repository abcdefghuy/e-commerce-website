/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.ecommercewebsite.services.impl;

/**
 *
 * @author ASUS
 */
import org.example.ecommercewebsite.DTO.request.QRCodeRequest;
import org.example.ecommercewebsite.DTO.request.RootRequest;
import org.example.ecommercewebsite.DTO.response.QRCodeResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;

import java.io.IOException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.example.ecommercewebsite.services.IBankService;

public class BankServiceImpl implements IBankService {
   private static final String URL = "https://api.vietqr.io/v2/banks";
   private static final String QR_CODE_URL = "https://api.vietqr.io/v2/generate";

   @Override
   public RootRequest fetchBanks() {
        RootRequest rootRequest = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(URL);
            HttpResponse response = client.execute(request);
            String json = EntityUtils.toString(response.getEntity());

            Gson gson = new Gson();
            rootRequest = gson.fromJson(json, RootRequest.class); // Chuyển đổi JSON thành đối tượng Root
        } catch (IOException e) {
            e.printStackTrace();    
        }
        return rootRequest;
   }
   @Override
   public QRCodeResponse createQRCode(QRCodeRequest qrRequest) {
        QRCodeResponse qrResponse = null;

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(QR_CODE_URL);
            post.setHeader("Content-Type", "application/json");
            Gson gson = new Gson();

        // Chuyển đổi đối tượng yêu cầu thành JSON
            String jsonRequest = gson.toJson(qrRequest);
            post.setEntity(new StringEntity(jsonRequest));

            HttpResponse response = client.execute(post);
            String jsonResponse = EntityUtils.toString(response.getEntity());

        // Chuyển đổi JSON phản hồi thành đối tượng QRCodeResponse
            qrResponse = gson.fromJson(jsonResponse, QRCodeResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrResponse;
   }
}
