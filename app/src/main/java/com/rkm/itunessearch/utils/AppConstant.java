package com.rkm.itunessearch.utils;

import android.content.Context;

import com.rkm.itunessearch.model.Result;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppConstant {

    public static final String SERVER_URL = "https://itunes.apple.com/";

    public static List<Result> listOfcart=new ArrayList<>();


    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }
}
