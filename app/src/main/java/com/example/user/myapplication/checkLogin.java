package com.example.user.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class checkLogin extends AsyncTask<String, Void, String> {
    private Context context;
    private AsyncResponse<String> loginDelegate = null;

    checkLogin(Context context,AsyncResponse<String> delegate) {
        this.context = context;
        this.loginDelegate = delegate;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String serverAdress = "140.130.33.143:80";
        String targetPlugin = "checkLogin.php";

        String phoneNumber = arg0[0];
        String passWord = arg0[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "phonenumber=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");

            link = String.format("http://%s/%s?%s", serverAdress,targetPlugin, data);
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

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
                String userName = jsonObj.getString("userName");
                String phone = jsonObj.getString("phone");
                switch (query_result) {
                    case "SUCCESS":
                        Toast.makeText(context, "登入成功! 歡迎使用者  " + userName, Toast.LENGTH_SHORT).show();
                        loginDelegate.taskFinish(phone);
                        break;
                    case "FAILURE":
                        Toast.makeText(context, "登入失敗!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "無法連到資料庫", Toast.LENGTH_SHORT).show();
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
