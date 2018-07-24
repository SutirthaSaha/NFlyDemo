package in.nfly.dell.nflydemo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.nfly.dell.nflydemo.MySingleton;
import in.nfly.dell.nflydemo.R;
import in.nfly.dell.nflydemo.User;

public class OnBoardRegisterActivity extends AppCompatActivity {


    private AutoCompleteTextView collegeRegister;
    private Spinner courseRegister,branchRegister,passingYearRegister;

    private String urlCollege="http://nfly.in/gapi/load_all_rows";
    private String userDetails="http://nfly.in/gapi/get_details_one";
    private String urlOnBoardRegister="http://nfly.in/api/onboard";


    private ArrayList<String> collegeList=new ArrayList<String>(){};
    private String[] courseList={"Select Course","Bachelor of Engineering/Technology","Master of Engineering/Technology","Bachelor in Computer Applications","Master in Computer Applications"};
    private String[] branchList={"Select Branch","Computer Science and Engineering","Information Technology","Electronics & Communication Engineering","Electrical & Electronics Engineering","Applied Electronics & Instrumentation","Mechanical Engineering","Civil Engineering","Other"};
    private String[] passingYearList={"Select Passing Year","2013","2014","2015","2016","2017","2017","2018","2019","2020","2021","2022"};

    private String email;
    private String  user_id;
    private int status;
    private String urlRegister="http://nfly.in/gapi/insert";

    private ArrayAdapter<String> courseAdapter,branchAdapter,passingYearAdapter;

    private String college,course,branch,passing_year;
    @Override
    public void onBackPressed() //asksir
    {
        super.onBackPressed();
        Intent i= new Intent(OnBoardRegisterActivity.this,RegisterActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board_register);

        collegeRegister=findViewById(R.id.collegeRegister);
        courseRegister=findViewById(R.id.courseRegister);
        branchRegister=findViewById(R.id.branchRegister);
        passingYearRegister=findViewById(R.id.passingYearRegsiter);

        Intent intent=getIntent();
        email=intent.getStringExtra("email");


        courseAdapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,R.layout.custom_spinner_onboard_textview,courseList);
        courseRegister.setAdapter(courseAdapter);


        branchAdapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,R.layout.custom_spinner_onboard_textview,branchList);
        branchRegister.setAdapter(branchAdapter);


        passingYearAdapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,R.layout.custom_spinner_onboard_textview,passingYearList);
        passingYearRegister.setAdapter(passingYearAdapter);

        setValues();
    }

    private void setValues() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlCollege, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(OnBoardRegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        collegeList.add(arrayObject.getString("college_name"));
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(OnBoardRegisterActivity.this,android.R.layout.simple_dropdown_item_1line,collegeList.toArray(new String[collegeList.size()]));
                    collegeRegister.setThreshold(1);
                    collegeRegister.setAdapter(adapter);
                    getUserDetails(email);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", "college_id");
                params.put("order", "ASC");
                params.put("table", "college_name");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }

    private void getUserDetails(final String  email) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject;
                    arrayObject=new JSONObject(response);
                    user_id=arrayObject.getString("user_id");
                    String fname=arrayObject.getString("fname");

                    SharedPreferences sharedPreferences=getSharedPreferences("Login Details", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user_id",user_id);
                    editor.putString("fname",fname);
                    editor.apply();

                    User user=new User(OnBoardRegisterActivity.this);
                    Toast.makeText(OnBoardRegisterActivity.this,user.getEmail()+"\n"+user.getFname()+"\n"+user.getUser_id(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("key","email");
                params.put("value",email);
                params.put("table","user");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }

    public void onCompleteRegistrationclick(View view) {
        college=collegeRegister.getText().toString().trim();
        course=courseRegister.getSelectedItem().toString().trim();
        branch=branchRegister.getSelectedItem().toString().trim();
        passing_year=passingYearRegister.getSelectedItem().toString().trim();

        Toast.makeText(this, user_id+"\n"+college+"\n"+course+"\n"+branch+"\n"+passing_year, Toast.LENGTH_SHORT).show();
        onBoardUser();
    }

    private void onBoardUser() {
        Toast.makeText(this, "Am Here", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        //Toast.makeText(OnBoardRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        insertAdditionalDetails();
                        /*
                        Intent intent=new Intent(OnBoardRegisterActivity.this,MainActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);*/
                    }
                    else{
                        Toast.makeText(OnBoardRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("insert_array[user_id]", user_id);
                params.put("insert_array[user_college]", college);
                params.put("insert_array[user_course]",course);
                params.put("insert_array[user_branch]",branch);
                params.put("insert_array[user_passing_year]",passing_year);
                params.put("table","user_graduation_details");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }

    private void insertAdditionalDetails() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){

                        insertSocialDetails();
                        /*
                        Intent intent=new Intent(OnBoardRegisterActivity.this,MainActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);*/
                    }
                    else{
                        Toast.makeText(OnBoardRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("insert_array[user_id]", user_id);
                params.put("table","user_additional_details");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }
    private void insertSocialDetails() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){

                        insertSchoolDetails();
                        /*
                        Intent intent=new Intent(OnBoardRegisterActivity.this,MainActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);*/
                    }
                    else{
                        Toast.makeText(OnBoardRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("insert_array[user_id]", user_id);
                params.put("table","user_social_profile");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }

    private void insertSchoolDetails() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){

                        insertWorkSample();
                        /*
                        Intent intent=new Intent(OnBoardRegisterActivity.this,MainActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);*/
                    }
                    else{
                        Toast.makeText(OnBoardRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("insert_array[user_id]", user_id);
                params.put("table","user_school_details");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }

    private void insertWorkSample() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlRegister, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){

                        Toast.makeText(OnBoardRegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(OnBoardRegisterActivity.this,MainActivity.class);
                        intent.putExtra("email",email);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(OnBoardRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OnBoardRegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-Api-Key", "59671596837f42d974c7e9dcf38d17e8");
                return headers;
            }

            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("insert_array[user_id]", user_id);
                params.put("table","user_work_sample");
                return params;
            }
        };
        MySingleton.getmInstance(OnBoardRegisterActivity.this).addToRequestQueue(stringRequest);
    }
}
