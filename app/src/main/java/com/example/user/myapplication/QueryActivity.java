package com.example.user.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class QueryActivity extends AppCompatActivity {
    private ListView queryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ininCompent();
    }

    private void ininCompent(){
        queryList = (ListView) findViewById(R.id.queryListveiw);
    }
}