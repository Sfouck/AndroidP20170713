package com.example.user.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements AsyncResponse<String> {

    private EditText etPassword, etPhone;
    private Button btSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("LoginActitvity","OnCreate");
        initCompent();

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                goCheckLogin(phoneNumber,password);
            }
        });
    }

    private void initCompent(){
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btSubmit = (Button) findViewById(R.id.btnLogin);
        //query_task  = new checkLogin(this);

    }

    public void goCheckLogin(String user,String password) {
        Toast.makeText(this, "登入中......", Toast.LENGTH_SHORT).show();
        new checkLogin(this,this).execute(user,password);
    }

    @Override
    public void taskFinish(String result){
        Toast.makeText(this,"Your PhoneNumber is " + result, Toast.LENGTH_SHORT).show();
    }
}
