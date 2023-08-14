package com.example.hyung;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private Activity parentActivity;

    public UserListAdapter(Context context, List<User> userList, Activity parentActivity){
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.user, null);

        final TextView userID = (TextView) v.findViewById(R.id.userID);
        TextView userPassword = (TextView) v.findViewById(R.id.userPassword);
        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView userAge = (TextView) v.findViewById(R.id.userAge);
        final TextView userStatus = (TextView) v.findViewById(R.id.userStatus);
        //ImageView userPermission = (ImageView) v.findViewById(R.id.img_permission);

        userID.setText(userList.get(position).getUserID());
        userPassword.setText(userList.get(position).getUserPassword());
        userName.setText(userList.get(position).getUserName());
        userAge.setText(userList.get(position).getUserAge());
        userStatus.setText(userList.get(position).getUserStatus());
        //userPermission.setImageResource(userList.get(position).getUserPermission());

        v.setTag(userList.get(position).getUserID());

        Button btn_delete = (Button) v.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            /*
                            if(success){
                                userList.remove(position);
                                notifyDataSetChanged();
                            }
                            */

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        userList.remove(position);
                        notifyDataSetChanged();
                    }
                };
                // 삭제할 구문
                userList.remove(position);
                notifyDataSetChanged();
                /*
                DeleteRequest deleteRequest = new DeleteRequest(userID.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(deleteRequest);
                */
            }
        });

        Button btn_permission = (Button) v.findViewById(R.id.btn_permission);
        btn_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList.get(position).userStatus = "1";
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            /*
                            if(success){
                                userList.remove(position);
                                notifyDataSetChanged();
                            }
                            */
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                };
                // 삭제할 구문
                //userList.remove(position);
                notifyDataSetChanged();
                /*
                PermissionRequest permissionRequest = new PermissionRequest(userID.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parentActivity);
                queue.add(permissionRequest);
                */
            }
        });

        return v;
    }
}