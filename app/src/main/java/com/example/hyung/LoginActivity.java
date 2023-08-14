package com.example.hyung;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private String sub_id, sub_pass;
    private boolean sub_checked;

    private Button btn_login, btn_register;

    private CheckBox cbbtn_autoLogin;
    boolean checkFlag = false;
    boolean checkBoxFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        cbbtn_autoLogin = findViewById(R.id.cbbtn_autoLogin);

        // 자동 로그인을 위한 SharedPreference 불러오기와 아이디 비번 세팅, 박스 체크.
        SharedPreferences sf = getSharedPreferences("sFile",MODE_PRIVATE);
        sub_id = sf.getString("sub_id","");
        sub_pass = sf.getString("sub_pass","");
        sub_checked = sf.getBoolean("checkBoxFlag",false);

        if(sub_checked) {
            et_id.setText(sub_id);
            et_pass.setText(sub_pass);
        }
        //cbbtn_autoLogin.setChecked(sf.getBoolean("checkBoxFlag",false));
        cbbtn_autoLogin.setChecked(false);

        // 회원가입 버튼을 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //EditText에 현재 입력되어있는 값을 get 해온다.
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                if(userID.equals("admin")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                final String userID = jsonObject.getString("userID");
                                final String userPass = jsonObject.getString("userPassword");
                                final String userName = jsonObject.getString("userName");
                                final String userAge = jsonObject.getString("userAge");
                                final String userStatus = jsonObject.getString("userStatus");

                                final int int_userStatus = Integer.parseInt(userStatus);

                                // 관리자 권한 로그인
                                if(userID.equals("admin")) {
                                    /*
                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    Toast.makeText(getApplicationContext(), "관리자 권한으로 로그인 하셨습니다.", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    */

                                    Toast.makeText(getApplicationContext(), "관리자 권한으로 로그인 하셨습니다.", Toast.LENGTH_SHORT).show();

                                    class BackgroundTask extends AsyncTask<Void, Void, String> {
                                        String target;

                                        protected void onPreExecute(){
                                            target = "http://jhj950912.dothome.co.kr/List.php";
                                        }

                                        @Override
                                        protected String doInBackground(Void... voids) {
                                            try {
                                                URL url = new URL(target);
                                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                InputStream inputStream = httpURLConnection.getInputStream();
                                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                                String temp;
                                                StringBuilder stringBuilder = new StringBuilder();
                                                while((temp = bufferedReader.readLine()) != null){
                                                    stringBuilder.append(temp+"\n");
                                                }

                                                bufferedReader.close();
                                                inputStream.close();
                                                httpURLConnection.disconnect();
                                                return stringBuilder.toString().trim();
                                            }
                                            catch(Exception e){
                                                e.printStackTrace();
                                            }

                                            return null;
                                        }

                                        @Override
                                        public void onProgressUpdate(Void... values) {
                                            super.onProgressUpdate(values);
                                        }

                                        @Override
                                        public void onPostExecute(String result){
                                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                            intent.putExtra("userList", result);
                                            startActivity(intent);
                                        }

                                    }

                                    new BackgroundTask().execute();
                                }

                                else if(int_userStatus == 0){
                                    new AlertDialog.Builder(LoginActivity.this).setMessage("아직 승인되지 않은 ID 입니다.\n서비스 이용이 불가합니다.")
                                            .setPositiveButton("뒤로 가기", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    return;
                                                }
                                            }).setNegativeButton("환경 설정", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(LoginActivity.this, SettingActivity.class);
                                            intent.putExtra("userID", userID);
                                            intent.putExtra("userPass", userPass);
                                            intent.putExtra("userName", userName);
                                            intent.putExtra("userAge", userAge);
                                            intent.putExtra("userStatus", userStatus);

                                            startActivity(intent);
                                        }
                                    }).show();
                                }

                                else {
                                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.\n환영합니다, " + userName + "(" + userID + ")님.", Toast.LENGTH_SHORT).show();

                                    // 정보 보내기용 intent
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPass", userPass);
                                    intent.putExtra("userName", userName);
                                    intent.putExtra("userAge", userAge);
                                    intent.putExtra("userStatus", userStatus);

                                    sub_id = userID;
                                    sub_pass = userPass;

                                    startActivity(intent);
                                }
                            }
                            else{   //회원 등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "등록되지 않은 회원이거나,\n아이디 혹은 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });



        // 체크를 먼저하고
        cbbtn_autoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFlag = true;
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();

        if(checkFlag) {
            SharedPreferences sp = getSharedPreferences("sFile", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            String put_id = et_id.getText().toString();
            String put_pass = et_pass.getText().toString();

            editor.putString("sub_id",put_id);
            editor.putString("sub_pass",put_pass);
            checkBoxFlag = true;
            editor.putBoolean("checkBoxFlag",checkBoxFlag);

            editor.commit();
        }
    }
    /*
    cbbtn_autoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("sFile", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                String put_id = et_id.getText().toString();
                String put_pass = et_pass.getText().toString();

                editor.putString("sub_id",put_id);
                editor.putString("sub_pass",put_pass);
                //editor.putBoolean("checkBoxFlag",checkBoxFlag);

                editor.commit();

            }
        });
    */

}