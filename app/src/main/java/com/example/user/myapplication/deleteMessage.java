package com.example.user.myapplication;

import android.app.ProgressDialog;
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

/**
 * Created by user on 2017/7/20.
 */

public class deleteMessage extends AsyncTask<String, Void, String> {
    private Context context;

    deleteMessage(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String serverAdress = "140.130.33.143:80";
        String targetPlugin = "deleteMessage.php";

        String msg_id = arg0[0];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "msg_id=" + URLEncoder.encode(msg_id, "UTF-8");

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
                switch (query_result) {
                    case "SUCCESS":
                        Toast.makeText(context, "刪除成功", Toast.LENGTH_SHORT).show();
                        break;
                    case "FAILURE":
                        Toast.makeText(context, "刪除失敗", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "讀取錯誤", Toast.LENGTH_SHORT).show();
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