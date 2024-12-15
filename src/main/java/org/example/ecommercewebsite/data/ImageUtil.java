package org.example.ecommercewebsite.data;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
public class ImageUtil {
    public static String DisplayImage(byte[] imageByte){
        if (imageByte == null) return "";
        return Base64.getEncoder().encodeToString(imageByte);
    }
    public static byte[] renderImage(Part filePart) throws IOException {
        byte[] avatarBytes = null;
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                avatarBytes = inputStream.readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return avatarBytes;
    }
}
