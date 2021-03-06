package com.example.usager.mobile;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 1253250 on 26/02/2016.
 */
public class Shared {
    public static String username;
    public static String password;
    public static int restoID;
    public static int billID;
    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";

    public static List<String> provinces = new ArrayList<String>();
    public static List<Integer> provincesID = new ArrayList<Integer>();
    public static List<String> cities = new ArrayList<String>();
    public static List<Integer> citiesID = new ArrayList<Integer>();
    public static List<String> restos = new ArrayList<String>();
    public static List<Integer> restosID = new ArrayList<Integer>();

    public static int UserMatch() throws IOException, JSONException {
        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()

                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url("http://projetdeweb.azurewebsites.net/API/Connection/Connect")
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());

        int Rights = -1;
        JSONArray jsonArray = new JSONArray(response.body().string());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Rights = jsonObject.optInt("Rights");
        }

        return Rights;
    }
}
