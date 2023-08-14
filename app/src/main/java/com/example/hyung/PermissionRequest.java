package com.example.hyung;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PermissionRequest extends StringRequest {
    final static private String URL = "http://jhj950912.dothome.co.kr/ChangeStatus.php";
    private Map<String, String> parameters;

    public PermissionRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    public Map <String, String> getParams(){
        return parameters;
    }
}