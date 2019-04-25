package com.xcelcorp.recyclerview;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InsertData extends AppCompatActivity {

    EditText edId,edName,edAge,edSalary,edProfileUrl;
    String empId,empName,empAge,empSalary,empProfileUrl;
    Button btnSave;
    String url="http://dummy.restapiexample.com/api/v1/create";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        edId=findViewById(R.id.insert_data_id);
        edName=findViewById(R.id.insert_employee_name);
        edAge=findViewById(R.id.insert_employee_age);
        edSalary=findViewById(R.id.insert_employee_salary);
        edProfileUrl=findViewById(R.id.insert_employee_profile_url);
        btnSave=findViewById(R.id.save_data_btn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empId=edId.getText().toString().trim();
                empName=edName.getText().toString().trim();
                empAge=edAge.getText().toString().trim();
                empSalary=edSalary.getText().toString().trim();
                empProfileUrl=edProfileUrl.getText().toString().trim();

                insertData();
            }
        });


    }

    private void insertData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(InsertData.this,response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InsertData.this,""+error,Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",empId);
                params.put("name",empName);
                params.put("salary",empSalary);
                params.put("age",empAge);
                params.put("profile_image",empProfileUrl);
                Log.d("Data", "getParams: "+params);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
