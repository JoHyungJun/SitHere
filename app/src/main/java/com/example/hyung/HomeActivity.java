package com.example.hyung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {
    private ImageButton imgbtn_setting, imgbtn_bluetooth, imgbtn_howToUse;
    private Button btn_setting, btn_bluetooth, btn_howToUse;
  //  private TextView tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgbtn_setting = findViewById(R.id.imgbtn_setting);
        btn_setting = findViewById(R.id.btn_setting);
        imgbtn_bluetooth = findViewById(R.id.imgbtn_bluetooth);
        btn_bluetooth = findViewById(R.id.btn_bluetooth);

        imgbtn_howToUse = findViewById(R.id.imgbtn_howToUse);
        btn_howToUse = findViewById(R.id.btn_howToUse);
     //   tv_userName = findViewById(R.id.tv_userName);


        Intent getInfoIntent = getIntent();
        final String userID = getInfoIntent.getStringExtra("userID");
        final String userPass = getInfoIntent.getStringExtra("userPass");
        final String userName = getInfoIntent.getStringExtra("userName");
        final String userAge = getInfoIntent.getStringExtra("userAge");
        final String userStatus = getInfoIntent.getStringExtra("userStatus");

  //      tv_userName.setText("[" + userName + " 님]");

        imgbtn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("userAge", userAge);
                intent.putExtra("userStatus", userStatus);

                startActivity(intent);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("userPass", userPass);
                intent.putExtra("userName", userName);
                intent.putExtra("userAge", userAge);
                intent.putExtra("userStatus", userStatus);

                startActivity(intent);
            }
        });

        imgbtn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BeaconActivity.class);
                startActivity(intent);
            }
        });

        btn_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, BeaconActivity.class);
                startActivity(intent);
            }
        });

        imgbtn_howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HowtouseActivity.class);
                startActivity(intent);
            }
        });

        btn_howToUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HowtouseActivity.class);
                startActivity(intent);
            }
        });
    }
}
