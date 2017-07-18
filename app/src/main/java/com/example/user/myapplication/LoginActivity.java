package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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
    }

    private void goCheckLogin(String user,String password) {
        Toast.makeText(this, "登入中......", Toast.LENGTH_SHORT).show();
        new checkLogin(this).execute(user, password);
    }
}
