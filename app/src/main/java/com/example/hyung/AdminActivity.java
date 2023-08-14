package com.example.hyung;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;

    // 수정 요
    private ImageView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        userList = new ArrayList<User>();


        adapter = new UserListAdapter(getApplicationContext(), userList, this);
        listView.setAdapter(adapter);


        // 수정(삭제) 요

        //Drawable drawable = getResources().getDrawable(R.drawable.permissionex);
        User u1 = new User("user", "pass", "user", "20", "0");
        userList.add(u1);
        //u1.userPermission = (ImageView)findViewById(R.id.img_permission);
        //u1.userPermission.setImageDrawable(drawable);
        //userList.add(u1);



        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response"); // 문제
            int count = 0;  // 회원 숫자
            String userID, userPassword, userName, userAge, userStatus;

            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userPassword =object.getString("userPassword");
                userName = object.getString("userName");
                userAge =object.getString("userAge");
                userStatus = object.getString("userStatus");


                User user = new User(userID, userPassword, userName, userAge, userStatus /*null*/);
                if(!(userID.equals("admin"))) {
                    userList.add(user);
                }
                count++;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
