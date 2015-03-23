package com.suricapp.tools;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by maxence on 22/03/15.
 */
public class ImageManipulation {

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, Base64.DEFAULT);
    }

    /**
     * Return a array byte from bit map image
     * @param image
     * @return
     */
    public static byte[] transformBitmapToArrayByte(Bitmap image)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
