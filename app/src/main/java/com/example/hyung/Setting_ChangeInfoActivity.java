package com.example.hyung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Setting_ChangeInfoActivity extends AppCompatActivity {

    private EditText tv_id, tv_pass, tv_passCheck, tv_name, tv_age;
    private Button btn_changeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_main);

        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);
        tv_passCheck = findViewById(R.id.tv_passCheck);
        tv_name = findViewById(R.id.tv_name);
        tv_age = findViewById(R.id.tv_age);
        btn_changeInfo = findViewById(R.id.btn_changeInfo);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPass");
        String userName = intent.getStringExtra("userName");
        String userAge = intent.getStringExtra("userAge");
        final String userStatus = intent.getStringExtra("userStatus");

        tv_name.setText(userName);
        tv_age.setText(userAge);
        tv_id.setText(userID);
        //tv_pass.setText(userPass);

        btn_changeInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String user_id = tv_id.getText().toString();
                String user_pass = tv_pass.getText().toString();
                String user_passCheck = tv_passCheck.getText().toString();
                String user_age = tv_age.getText().toString();
                String user_name = tv_name.getText().toString();

                int user_intAge = Integer.parseInt(user_age);
                int user_intStatus = Integer.parseInt(userStatus);

                if(!(userID.equals(user_id))){
                    Toast.makeText(getApplicationContext(), "ID는 고유의 키값으로 바꾸실 수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(!(user_pass.equals(user_passCheck))){
                    Toast.makeText(getApplicationContext(), "두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                else{
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if(success){
                                    Toast.makeText(getApplicationContext(), "회원 정보 수정에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Setting_ChangeInfoActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else{   //회원 등록에 실패한 경우
                                    Toast.makeText(getApplicationContext(), "회원 정보 수정에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };



                    // 서버로 volley를 이용해서 요청을 함.
                    Setting_ChangeInfoRequest settingChangeInfoRequest = new Setting_ChangeInfoRequest(user_id, user_pass, user_name, user_intAge, user_intStatus, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Setting_ChangeInfoActivity.this);
                    queue.add(settingChangeInfoRequest);

                    // Request 객체 문제 나중에 지우고 수정할 부분
                    Toast.makeText(getApplicationContext(), "회원 정보 수정에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Setting_ChangeInfoActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}
