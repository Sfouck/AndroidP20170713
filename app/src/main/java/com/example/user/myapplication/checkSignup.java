package com.example.user.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by user on 2017/7/18.
 */

public class checkSignup extends AsyncTask<String, Void, String> {

    private Context context;


    checkSignup(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    private int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private String getID() {
        String code = "";
        for (int i = 0; i < 200; i++) {
            switch (randInt(0, 2)) {
                case 0:
                    code += Character.toString((char) randInt(65, 90));
                    break;
                case 1:
                    code += Character.toString((char) randInt(97, 122));
                    break;
                case 2:
                    code += Character.toString((char) randInt(48, 57));
                    break;
            }
        }
        return code;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String serverAdress = "140.130.33.143:80";
        String targetPlugin = "signup.php";

        String fullName = arg0[0];
        String userName = arg0[1];
        String passWord = arg0[2];
        String phoneNumber = arg0[3];
        String emailAddress = arg0[4];
        String regID = getID();

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "fullname=" + URLEncoder.encode(fullName, "UTF-8");
            data += "&username=" + URLEncoder.encode(userName, "UTF-8");
            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");
            data += "&phonenumber=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data += "&emailaddress=" + URLEncoder.encode(emailAddress, "UTF-8");
            data += "&regid=" + URLEncoder.encode(regID, "UTF-8");

            link = String.format("http://%s/%s?%s", serverAdress,targetPlugin, data);
            Log.d("doInBackground", link);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //final String basicAuth = "Basic " + Base64.encodeToString("admin:admin".getBytes(), Base64.NO_WRAP);
            //con.setUseCaches(false);
            //con.setRequestProperty("Authorization", basicAuth);
            //con.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            Log.d("doInBackground", result);
            return result;
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String jsonStr) {
        Log.d("PostExecute", jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                switch (query_result) {
                    case "SUCCESS":
                        Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
                        break;
                    case "FAILURE":
                        Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                        break;
                    case "DUPLICATED":
                        Toast.makeText(context, "User already exists.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}
