package com.example.hyung;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SettingActivity extends AppCompatActivity {
    private Button btn_logout, btn_changeInfo, btn_deleteID, btn_sendImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_logout = findViewById(R.id.btn_logout);
        btn_changeInfo = findViewById(R.id.btn_changeInfo);
        btn_deleteID = findViewById(R.id.btn_deleteID);
        btn_sendImg = findViewById(R.id.btn_sendImg);

        Intent getInfoIntent = getIntent();
        final String userID = getInfoIntent.getStringExtra("userID");
        final String userPass = getInfoIntent.getStringExtra("userPass");
        final String userName = getInfoIntent.getStringExtra("userName");
        final String userAge = getInfoIntent.getStringExtra("userAge");
        final String userStatus = getInfoIntent.getStringExtra("userStatus");

        final int int_userStatus = Integer.parseInt(userStatus);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this).setMessage("정말 로그아웃 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).show();
            }
        });

        btn_changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, Setting_ChangeInfoActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("userAge", userAge);
                intent.putExtra("userStatus", userStatus);

                startActivity(intent);
            }
        });

        btn_deleteID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingActivity.this).setMessage("정말 계정을 삭제하시겠습니까?")
                        .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if(success){
                                        // 안 되면 밑에 주석 해제
                                        Toast.makeText(getApplicationContext(), "성공적으로 계정이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent(SettingActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        DeleteRequest deleteRequest = new DeleteRequest(userID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(SettingActivity.this);
                        queue.add(deleteRequest);

                        /*
                        Toast.makeText(getApplicationContext(), "성공적으로 계정이 삭제 되었습니다..", Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent(SettingActivity.this, LoginActivity.class);
                                        startActivity(intent);
                         */
                    }
                }).show();
            }
        });

        btn_sendImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, ImageUploadActivity.class);
                startActivity(intent);
            }
        });
    }
}