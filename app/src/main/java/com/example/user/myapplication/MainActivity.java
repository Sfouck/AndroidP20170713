package com.example.user.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView myListView;
    private String[] items = {"Login","Signup","QueryMessage"};
    private Class<?>[] activitys = {LoginActivity.class,SignupActivity.class,QueryActivity.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);

        myListView = (ListView) findViewById(R.id.myListView);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = myListView.getItemAtPosition(position);
                Toast.makeText(view.getContext(), listItem.toString(),Toast.LENGTH_SHORT).
                        show();
                Intent intent = new Intent(MainActivity.this,activitys[position]);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
