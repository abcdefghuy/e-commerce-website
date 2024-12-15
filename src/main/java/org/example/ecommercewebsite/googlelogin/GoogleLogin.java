package org.example.ecommercewebsite.googlelogin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.http.client.ClientProtocolException;
import org.apache.hc.client5.http.fluent.Request;
import org.example.ecommercewebsite.constant.constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.example.ecommercewebsite.business.Customer;

import java.io.IOException;

public class GoogleLogin {
    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.post(constant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", constant.GOOGLE_CLIENT_ID)
                                .add("client_secret", constant.GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", constant.GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", constant.GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static Customer getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = constant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.get(link).execute().returnContent().asString();
        JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
        String email = jsonResponse.has("email") ? jsonResponse.get("email").getAsString() : null;
        Customer customer = new Customer();
        customer.setGoogleLogin(email);
        return customer;
    }
}
