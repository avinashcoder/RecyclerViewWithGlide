package com.xcelcorp.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements EmployeeListAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    String url="http://dummy.restapiexample.com/api/v1/employees";
    ImageView addRecord;
    private Adapter adapter;
    private List<EmployeeModelClass> employeeModelClasses;
    private String empId;
    private TextView tvName,tvId,tvAge,tvSalary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addRecord=findViewById(R.id.add_record);
        recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        employeeModelClasses = new ArrayList<>();

        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,InsertData.class);
                startActivity(intent);
            }
        });

        getEmployeeList();
    }

    private void getEmployeeList() {
        employeeModelClasses = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    if(jsonArray.length()>0){
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            String profileUrl=jsonObject.getString("profile_image");
                            String name=jsonObject.getString("employee_name");
                            String id=jsonObject.getString("id");
                            String age=jsonObject.getString("employee_age");
                            String salary=jsonObject.getString("employee_salary");

                            employeeModelClasses.add(new EmployeeModelClass(profileUrl,name,id,age,salary));
                        }
                        recyclerView.setVisibility(View.VISIBLE);
                        EmployeeListAdapter adapter = new EmployeeListAdapter(MainActivity.this,employeeModelClasses);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(MainActivity.this);
                        //adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,""+error,Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        EmployeeModelClass clickedItem = employeeModelClasses.get(position);
        empId=clickedItem.getId();
        String empUrl = "http://dummy.restapiexample.com/api/v1/employee/"+empId;
        getSpecificEmployeeData(empUrl);
        //Toast.makeText(MainActivity.this,clickedItem.getId(),Toast.LENGTH_SHORT).show();
    }

    private void getSpecificEmployeeData(String getEmployeeUrl){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, getEmployeeUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String profileUrl=jsonObject.getString("profile_image");
                    String name=jsonObject.getString("employee_name");
                    String id=jsonObject.getString("id");
                    String age=jsonObject.getString("employee_age");
                    String salary=jsonObject.getString("employee_salary");

                    showEmployeeData(profileUrl,id,name,age,salary);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,""+error,Toast.LENGTH_LONG).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showEmployeeData(String profileUrl,String id,String name,String age,String salary){
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.employee_display_layout,null);
        tvName=view.findViewById(R.id.emp_display_name);
        tvId=view.findViewById(R.id.emp_display_id);
        tvAge=view.findViewById(R.id.emp_display_age);
        tvSalary=view.findViewById(R.id.emp_display_salary);

        tvName.setText(name);
        tvId.setText(id);
        tvAge.setText(age);
        tvSalary.setText(salary);

        alert.setView(view);
        //alert.setTitle("Employee Details");

        alert.show();
    }
}
