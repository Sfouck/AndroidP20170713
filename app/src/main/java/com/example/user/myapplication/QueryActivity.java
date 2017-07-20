package com.example.user.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryActivity extends AppCompatActivity implements AsyncResponse<String> {
    private ListView msgListview;
    private List<HashMap<String, String>> messageList;
    private String phoneNumber = "11";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        ininCompent();
        reloadListView(phoneNumber);

    }

    private void ininCompent() {
        msgListview = (ListView) findViewById(R.id.queryListveiw);
    }

    private void reloadListView(String phone){
        new queryMessage(this, this).execute(phone);
    }
    private void deleteMessagebyID(String ID){
        new deleteMessage(this).execute(ID);
    }

    private void jsonToList(String jsonStr) {
        //使用List存入HashMap，用來顯示ListView上面的文字。
        messageList = new ArrayList<>();
        try {
            JSONArray msgArray = new JSONArray(jsonStr);

            for (int i = 0; i < msgArray.length(); i++) {
                JSONObject row = msgArray.getJSONObject(i);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", row.getString("msg_id"));
                hashMap.put("msg", row.getString("msg_text"));
                hashMap.put("date", row.getString("created_date"));
                messageList.add(hashMap);
            }
            Log.d("jsonToList", messageList.toString());
        } catch (Exception e) {
            Log.d("jsonToList", "error" + e);
        }
    }

    private void setListViewContent() {
        ListAdapter listAdapter = new SimpleAdapter(
                this,
                messageList,
                R.layout.list_msg_item,
                new String[]{"id", "msg", "date"},
                new int[]{R.id.tvMsg_id, R.id.tvMessage, R.id.tvDate});

        msgListview.setAdapter(listAdapter);
    }

    private void setListViewListner(){
        msgListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View seletedView, int position, long id) {
                final TextView tvMsg_id = (TextView) seletedView.findViewById(R.id.tvMsg_id);
                final String msg_id = tvMsg_id.getText().toString();
                Toast.makeText(QueryActivity.this, msg_id, Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert=new AlertDialog.Builder(QueryActivity.this);
                alert.setTitle("Delete Message?");
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMessagebyID(msg_id);
                        Toast.makeText(QueryActivity.this,
                                "Message Deleted. ID=" + msg_id, Toast.LENGTH_SHORT).show();
                        reloadListView(phoneNumber);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
    }
    @Override
    public void taskFinish(String result) {
        jsonToList(result);
        setListViewContent();
        setListViewListner();
    }
}