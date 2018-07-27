package in.nfly.dell.nflydemo.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.nfly.dell.nflydemo.MySingleton;
import in.nfly.dell.nflydemo.R;
import in.nfly.dell.nflydemo.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileAcademicFragment extends Fragment {

    public RecyclerView workExperienceRecyclerView;
    public RecyclerView trainingsRecyclerView;
    public RecyclerView projectsRecyclerView;
    public RecyclerView achievementsRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private String urlAcademic="http://nfly.in/profileapi/academic_details";
    private String oneRow="http://nfly.in/gapi/load_rows_one";
    private String urlUpdate="http://nfly.in/gapi/update";
    private String urlInsert="http://nfly.in/gapi/insert";
    private String urlDelete="http://nfly.in/gapi/delete_with_one";
    private String urlDataExists="http://nfly.in/gapi/data_exists_one";


    private String user_id;
    private int status;
    private int xcount=0,xiicount=0;
    private String date;

    private TextView profileWorkExperienceTextDialog,profileTrainingsTextDialog,profileProjectsTextDialog,profileAchievementsTextDialog;
    private TextView profileCollegeName,profileCourse,profileBranch,profileClassOf,profileCGPA;
    private TextView profileXIISchool,profileXIIBoard,profileXIIStream,profileXIIPassingyear,profileXIIPercentage;
    private TextView profileXSchool,profileXBoard,profileXStream,profileXPassingyear,profileXPercentage;
    private TextView profileGitHub,profilePlayStore,profileBehance,profileBlogLink;

    private ImageView workExperienceAddBtn, collegeEducationEditBtn, schoolEducationEditBtn, trainingsAddBtn,
    projectsAddBtn, achievementsAddBtn, workSampleEditBtn;
    private ImageView editWorkExperienceStartDateCalendarBtn,editWorkExperienceLastDateCalendarBtn;

    private EditText editSchoolEducationXIISchoolName,editSchoolEducationXIIBoard,editSchoolEducationXIIStream,editSchoolEducationXIIPassingYear,editSchoolEducationXIIPercentage;
    private EditText editSchoolEducationXSchoolName,editSchoolEducationXBoard,editSchoolEducationXStream,editSchoolEducationXPassingYear,editSchoolEducationXPercentage;

    private EditText editCollegeEducationCollegeName,editCollegeEducationCourse,editCollegeEducationBranch,editCollegeEducationClassOf,editCollegeEducationCGPA;

    private EditText editWorkSampleGithub,editWorkSamplePlayStore,editWorkSampleBehance,editWorkSampleBlogLink;

    private EditText editWorkExperienceJobType,editWorkExperienceCompanyName,editWorkExperiencePosition,editWorkExperienceStartDate,editWorkExperienceLastDate,editWorkExperienceJobDescription;
    private EditText editTrainingsCourse,editTrainingsCertifiedBy,editTrainingsDuration,editTrainingsDetails;
    private EditText editProjectsTitle,editProjectsDescription,editProjectsLink;
    private EditText editAchievementsName,editAchievementsDescription;

    private ArrayList<String> DataSet=new ArrayList<String>(){
        {  add("blahblah");
            add("blah");
         }};

    private ArrayList<String> workExpIdDataSet=new ArrayList<String>(){};
    private ArrayList<String> companyNameDataSet=new ArrayList<String>(){};
    private ArrayList<String> postionDataSet=new ArrayList<String>(){};
    private ArrayList<String> jobTypeDataSet=new ArrayList<String>(){};
    private ArrayList<String> startDateDataSet=new ArrayList<String>(){};
    private ArrayList<String> endDateDataSet=new ArrayList<String>(){};
    private ArrayList<String> jobDecriptionDataSet=new ArrayList<String>(){};

    private ArrayList<String> trainingIdDataSet=new ArrayList<String>(){};
    private ArrayList<String> courseDataSet=new ArrayList<String>(){};
    private ArrayList<String> certDataSet=new ArrayList<String>(){};
    private ArrayList<String> durationDataSet=new ArrayList<String>(){};
    private ArrayList<String> detailsDataSet=new ArrayList<String>(){};

    private ArrayList<String> projectIdDataSet=new ArrayList<String>(){};
    private ArrayList<String> projectTitleDataSet=new ArrayList<String>(){};
    private ArrayList<String> projectDescriptionDataSet=new ArrayList<String>(){};
    private ArrayList<String> projectLinkDataSet=new ArrayList<String>(){};

    private ArrayList<String> achievementIdDataSet=new ArrayList<String>(){};
    private ArrayList<String> achievementNameDataSet=new ArrayList<String>(){};
    private ArrayList<String> achievementDescriptionDataSet=new ArrayList<String>(){};

    public ProfileAcademicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile_academic, container, false);

        User user=new User(getActivity());
        user_id=user.getUser_id();

        profileCollegeName=view.findViewById(R.id.profileCollege);
        profileCourse=view.findViewById(R.id.profileCourse);
        profileBranch=view.findViewById(R.id.profileBranch);
        profileClassOf=view.findViewById(R.id.profileClassOf);
        profileCGPA=view.findViewById(R.id.profileCGPA);

        profileXIISchool=view.findViewById(R.id.profileXIISchool);
        profileXIIBoard=view.findViewById(R.id.profileXIIBoard);
        profileXIIStream=view.findViewById(R.id.profileXIIStream);
        profileXIIPercentage=view.findViewById(R.id.profileXIIPercentage);
        profileXIIPassingyear=view.findViewById(R.id.profileXIIPassingYear);

        profileXSchool=view.findViewById(R.id.profileXSchool);
        profileXBoard=view.findViewById(R.id.profileXBoard);
        profileXPercentage=view.findViewById(R.id.profileXPercentage);
        profileXPassingyear=view.findViewById(R.id.profileXPassingYear);

        profileGitHub=view.findViewById(R.id.profileGitHub);
        profileBehance=view.findViewById(R.id.profileBehance);
        profileBlogLink=view.findViewById(R.id.profileBlogLink);
        profilePlayStore=view.findViewById(R.id.profilePlayStore);

        profileWorkExperienceTextDialog=view.findViewById(R.id.profileWorkExperienceTextDialog);
        profileTrainingsTextDialog=view.findViewById(R.id.profileTrainingsTextDialog);
        profileProjectsTextDialog=view.findViewById(R.id.profileProjectsTextDialog);
        profileAchievementsTextDialog=view.findViewById(R.id.profileAchievementsTextDialog);

        workExperienceAddBtn=view.findViewById(R.id.workExperienceAddBtn);
        collegeEducationEditBtn=view.findViewById(R.id.collegeEducationEditBtn);
        schoolEducationEditBtn=view.findViewById(R.id.schoolEducationEditBtn);
        trainingsAddBtn=view.findViewById(R.id.trainingsAddBtn);
        projectsAddBtn=view.findViewById(R.id.projectsAddBtn);
        achievementsAddBtn=view.findViewById(R.id.achievementsAddBtn);
        workSampleEditBtn=view.findViewById(R.id.workSampleEditBtn);

        setWorkExperience(view);
        setTrainings(view);
        setProjects(view);
        setAchievements(view);

        checkSchoolDetails();
        workExperienceAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View workExperienceAddLayout=layoutInflater.inflate(R.layout.dialog_edit_work_experience,null);

                editWorkExperienceCompanyName=workExperienceAddLayout.findViewById(R.id.editWorkExperienceCompanyName);
                editWorkExperiencePosition=workExperienceAddLayout.findViewById(R.id.editWorkExperiencePosition);
                editWorkExperienceJobType=workExperienceAddLayout.findViewById(R.id.editWorkExperienceJobType);
                editWorkExperienceStartDate=workExperienceAddLayout.findViewById(R.id.editWorkExperienceStartDate);
                editWorkExperienceLastDate=workExperienceAddLayout.findViewById(R.id.editWorkExperienceLastDate);
                editWorkExperienceJobDescription=workExperienceAddLayout.findViewById(R.id.editWorkExperienceJobDescription);

                editWorkExperienceStartDateCalendarBtn=workExperienceAddLayout.findViewById(R.id.editWorkExperienceStartDateIcon);
                editWorkExperienceLastDateCalendarBtn=workExperienceAddLayout.findViewById(R.id.editWorkExperienceLastDateIcon);





                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setView(workExperienceAddLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        workExperienceRecyclerView.removeAllViewsInLayout();
                        addWorkExperience();
                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

                final Calendar calendarStartDate = Calendar.getInstance();
                final  DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        calendarStartDate.set(Calendar.YEAR, year);
                        calendarStartDate.set(Calendar.MONTH, monthOfYear);
                        calendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(editWorkExperienceStartDate,calendarStartDate);
                    }
                };

                editWorkExperienceStartDateCalendarBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(getContext(), startDate, calendarStartDate.get(Calendar.YEAR), calendarStartDate.get(Calendar.MONTH),
                                calendarStartDate.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });



                final Calendar calendarLastDate = Calendar.getInstance();
                final  DatePickerDialog.OnDateSetListener lastDate = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        calendarLastDate.set(Calendar.YEAR, year);
                        calendarLastDate.set(Calendar.MONTH, monthOfYear);
                        calendarLastDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(editWorkExperienceLastDate,calendarLastDate);
                    }
                };

                editWorkExperienceLastDateCalendarBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(getContext(), lastDate, calendarLastDate
                                .get(Calendar.YEAR), calendarLastDate.get(Calendar.MONTH),
                                calendarLastDate.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });



    }
        });

        collegeEducationEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View collegeEducationEditLayout=layoutInflater.inflate(R.layout.dialog_edit_college_education,null);

                editCollegeEducationCollegeName=collegeEducationEditLayout.findViewById(R.id.editCollegeEducationCollegeName);
                editCollegeEducationBranch=collegeEducationEditLayout.findViewById(R.id.editCollegeEducationBranch);
                editCollegeEducationCourse=collegeEducationEditLayout.findViewById(R.id.editCollegeEducationCourse);
                editCollegeEducationClassOf=collegeEducationEditLayout.findViewById(R.id.editCollegeEducationClassOf);
                editCollegeEducationCGPA=collegeEducationEditLayout.findViewById(R.id.editCollegeEducationCgpa);

                editCollegeEducationCollegeName.setText(profileCollegeName.getText().toString().trim());
                editCollegeEducationBranch.setText(profileBranch.getText().toString().trim());
                editCollegeEducationCourse.setText(profileCourse.getText().toString().trim());
                editCollegeEducationClassOf.setText(profileClassOf.getText().toString().trim());
                if(!profileCGPA.getText().toString().trim().equals("CGPA not provided")) {
                    editCollegeEducationCGPA.setText(profileCGPA.getText().toString().trim());
                }
                else{
                    editCollegeEducationCGPA.setText("");
                }

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setView(collegeEducationEditLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editCollegeEducation();
                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

            }
        });

        schoolEducationEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View schoolEducationEditLayout=layoutInflater.inflate(R.layout.dialog_edit_school_education,null);

                editSchoolEducationXIISchoolName=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXIISchoolName);
                editSchoolEducationXIIBoard=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXIIBoard);
                editSchoolEducationXIIStream=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXIIStream);
                editSchoolEducationXIIPassingYear=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXIIPassingYear);
                editSchoolEducationXIIPercentage=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXIIPercentage);

                editSchoolEducationXSchoolName=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXSchoolName);
                editSchoolEducationXBoard=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXBoard);
                editSchoolEducationXPassingYear=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXPassingYear);
                editSchoolEducationXPercentage=schoolEducationEditLayout.findViewById(R.id.editSchoolEducationXPercentage);

                editSchoolEducationXIISchoolName.setText(profileXIISchool.getText().toString());
                editSchoolEducationXIIBoard.setText(profileXIIBoard.getText().toString());
                if(!profileXIIStream.getText().toString().equals("You have not added any class XII details")) {
                    editSchoolEducationXIIStream.setText(profileXIIStream.getText().toString());
                }
                else{
                    editSchoolEducationXIIStream.setText("");
                }
                editSchoolEducationXIIPassingYear.setText(profileXIIPassingyear.getText().toString());
                editSchoolEducationXIIPercentage.setText(profileXIIPercentage.getText().toString());

                editSchoolEducationXSchoolName.setText(profileXSchool.getText().toString());
                editSchoolEducationXBoard.setText(profileXBoard.getText().toString());
                if (!profileXPassingyear.getText().toString().equals("You have not added any class X details")) {
                    editSchoolEducationXPassingYear.setText(profileXPassingyear.getText().toString());
                }
                else{
                    editSchoolEducationXPassingYear.setText("");
                }
                editSchoolEducationXPercentage.setText(profileXPercentage.getText().toString());

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setView(schoolEducationEditLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editSchoolEducation();

                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

            }
        });

        trainingsAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View trainingsAddLayout=layoutInflater.inflate(R.layout.dialog_edit_trainings,null);

                editTrainingsCourse=trainingsAddLayout.findViewById(R.id.editTrainingsCourse);
                editTrainingsDetails=trainingsAddLayout.findViewById(R.id.editTrainingsDetails);
                editTrainingsDuration=trainingsAddLayout.findViewById(R.id.editTrainingsDuration);
                editTrainingsCertifiedBy=trainingsAddLayout.findViewById(R.id.editTrainingsCertifiedBy);

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setView(trainingsAddLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addTrainings();
                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

            }
        });

        projectsAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View projectsAddLayout=layoutInflater.inflate(R.layout.dialog_edit_projects,null);

                editProjectsDescription=projectsAddLayout.findViewById(R.id.editProjectDescription);
                editProjectsLink=projectsAddLayout.findViewById(R.id.editProjectsLink);
                editProjectsTitle=projectsAddLayout.findViewById(R.id.editProjectsTitle);

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setView(projectsAddLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        projectsRecyclerView.removeAllViewsInLayout();
                        addProjects();
                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

            }
        });

        achievementsAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View achievementsAddLayout=layoutInflater.inflate(R.layout.dialog_edit_achievements,null);

                editAchievementsName=achievementsAddLayout.findViewById(R.id.editAchievementsName);
                editAchievementsDescription=achievementsAddLayout.findViewById(R.id.editAchievementsDescription);

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setView(achievementsAddLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        achievementsRecyclerView.removeAllViewsInLayout();
                        addAchievements();

                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

            }
        });

        workSampleEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater=getLayoutInflater();
                View workSampleEditLayout=layoutInflater.inflate(R.layout.dialog_edit_work_sample,null);

                editWorkSampleGithub=workSampleEditLayout.findViewById(R.id.editWorkSampleGithub);
                editWorkSampleBehance=workSampleEditLayout.findViewById(R.id.editWorkSampleBehance);
                editWorkSamplePlayStore=workSampleEditLayout.findViewById(R.id.editWorkSamplePlayStore);
                editWorkSampleBlogLink=workSampleEditLayout.findViewById(R.id.editWorkSampleBlogLink);

                if(!profileGitHub.getText().toString().equals("Not Provided")) {
                    editWorkSampleGithub.setText(profileGitHub.getText().toString());
                }
                if(!profileBehance.getText().toString().equals("Not Provided")) {
                    editWorkSampleBehance.setText(profileBehance.getText().toString());
                }
                if(!profilePlayStore.getText().toString().equals("Not Provided")) {
                    editWorkSamplePlayStore.setText(profilePlayStore.getText().toString());
                }
                if(!profileBlogLink.getText().toString().equals("Not Provided")) {
                    editWorkSampleBlogLink.setText(profileBlogLink.getText().toString());
                }

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
                alertDialog.setView(workSampleEditLayout);
                alertDialog.setCancelable(false);

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editWorkSample();

                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

            }
        });
        return view;
    }

    private void checkSchoolDetails() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlDataExists, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        checkWorkSampleDetails();
                    }
                    else{
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        insertSchoolDetails();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Exists", Toast.LENGTH_SHORT).show();
                insertSchoolDetails();
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
                params.put("key","user_id");
                params.put("value",user_id);
                params.put("table","user_school_details");
                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void insertSchoolDetails() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){

                        checkWorkSampleDetails();
                    }
                    else{
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void checkWorkSampleDetails() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlDataExists, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        setValues();
                    }
                    else{
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        insertWorkSampleDetails();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Exists", Toast.LENGTH_SHORT).show();
                insertWorkSampleDetails();
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
                params.put("key","user_id");
                params.put("value",user_id);
                params.put("table","user_work_sample");
                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void insertWorkSampleDetails() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){

                        checkWorkSampleDetails();
                    }
                    else{
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void addProjects() {
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        setProjects();
                    }
                    else{
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
                params.put("insert_array[course_id]", "0");
                params.put("insert_array[project_id]", "0");
                params.put("insert_array[user_project_name]",editProjectsTitle.getText().toString().trim());
                params.put("insert_array[user_project_details]",editProjectsDescription.getText().toString().trim());
                params.put("insert_array[user_project_link]",editProjectsLink.getText().toString().trim());
                params.put("insert_array[user_project_type]","0");
                params.put("insert_array[user_project_remark]","0");
                params.put("insert_array[user_project_uploaded_on]",date);
                params.put("table","user_projects");
                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void addAchievements() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        setAchievements();
                    }
                    else{
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
                params.put("insert_array[accolade_title]",editAchievementsName.getText().toString().trim());
                params.put("insert_array[accolade_details]",editAchievementsDescription.getText().toString().trim());
                params.put("table","user_accolades");
                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void addTrainings() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        trainingIdDataSet.clear();
                        courseDataSet.clear();
                        durationDataSet.clear();
                        certDataSet.clear();
                        detailsDataSet.clear();
                        setTrainings();
                    }
                    else{
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
                params.put("insert_array[user_training_course]",editTrainingsCourse.getText().toString().trim());
                params.put("insert_array[user_training_company]",editTrainingsCertifiedBy.getText().toString().trim());
                params.put("insert_array[user_training_duration]",editTrainingsDuration.getText().toString().trim());
                params.put("insert_array[user_training_details]",editTrainingsDetails.getText().toString().trim());
                params.put("table","user_training_details");
                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void addWorkExperience() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    status=arrayObject.getInt("status");
                    if(status==200){
                        setWorkExp();
                    }
                    else{
                        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
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
                params.put("insert_array[user_job_type]",editWorkExperienceJobType.getText().toString().trim());
                params.put("insert_array[user_company]",editWorkExperienceCompanyName.getText().toString().trim());
                params.put("insert_array[user_job_profile]",editWorkExperiencePosition.getText().toString().trim());
                params.put("insert_array[user_job_start_date]",editWorkExperienceStartDate.getText().toString());
                params.put("insert_array[user_job_end_date]",editWorkExperienceLastDate.getText().toString().trim());
                params.put("insert_array[user_job_desc]",editWorkExperienceJobDescription.getText().toString());
                params.put("table","user_employment_details");
                return params;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    private void editCollegeEducation() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setValues();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("update_array[user_college]",editCollegeEducationCollegeName.getText().toString());
                params.put("update_array[user_course]",editCollegeEducationCourse.getText().toString());
                params.put("update_array[user_branch]",editCollegeEducationBranch.getText().toString());
                params.put("update_array[user_passing_year]",editCollegeEducationClassOf.getText().toString());
                params.put("update_array[user_cgpa]",editCollegeEducationCGPA.getText().toString());

                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table","user_graduation_details");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void editSchoolEducation() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setValues();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("update_array[user_xii_school_name]",editSchoolEducationXIISchoolName.getText().toString());
                params.put("update_array[user_xii_board]",editSchoolEducationXIIBoard.getText().toString());
                params.put("update_array[user_xii_stream]",editSchoolEducationXIIStream.getText().toString());
                params.put("update_array[user_xii_marks]",editSchoolEducationXIIPercentage.getText().toString());
                params.put("update_array[user_xii_passing_year]",editSchoolEducationXIIPassingYear.getText().toString());

                params.put("update_array[user_x_school_name]",editSchoolEducationXSchoolName.getText().toString());
                params.put("update_array[user_x_board]",editSchoolEducationXBoard.getText().toString());
                params.put("update_array[user_x_marks]",editSchoolEducationXPercentage.getText().toString());
                params.put("update_array[user_x_passing_year]",editSchoolEducationXPassingYear.getText().toString());


                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table","user_school_details");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void editWorkSample() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                setValues();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("update_array[github_profile]",editWorkSampleGithub.getText().toString());
                params.put("update_array[playstore_profile]",editWorkSamplePlayStore.getText().toString());
                params.put("update_array[design_profile]",editWorkSampleBehance.getText().toString());
                params.put("update_array[blog_profile]",editWorkSampleBlogLink.getText().toString());
                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table","user_work_sample");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void setValues() {
        xcount=0;
        xiicount=0;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlAcademic, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONObject gradDetails=jsonObject.getJSONObject("user_graduation_details");
                    profileCollegeName.setText(gradDetails.getString("user_college"));
                    profileCourse.setText(gradDetails.getString("user_course"));
                    profileBranch.setText(gradDetails.getString("user_branch"));
                    profileClassOf.setText(gradDetails.getString("user_passing_year"));
                    if(!gradDetails.getString("user_cgpa").equals("0.00")) {
                        profileCGPA.setText(gradDetails.getString("user_cgpa"));
                    }
                    else{
                        profileCGPA.setText("CGPA not provided");
                    }

                    JSONObject schoolDetails=jsonObject.getJSONObject("user_school_details");

                    if(!schoolDetails.getString("user_xii_school_name").isEmpty()){
                        profileXIISchool.setText(schoolDetails.getString("user_xii_school_name"));
                    }
                    else{
                        profileXIISchool.setText("");
                        xiicount++;
                    }
                    if(!schoolDetails.getString("user_xii_board").isEmpty()) {
                        profileXIIBoard.setText(schoolDetails.getString("user_xii_board"));
                    }
                    else{
                        profileXIIBoard.setText("");
                        xiicount++;
                    }
                    if(!schoolDetails.getString("user_xii_stream").isEmpty()) {
                        profileXIIStream.setText("Stream: " + schoolDetails.getString("user_xii_stream"));
                    }
                    else{
                        profileXIIStream.setText("");
                        xiicount++;
                    }
                    if(!schoolDetails.getString("user_xii_marks").isEmpty()&& !schoolDetails.getString("user_xii_marks").equals("0")) {
                        profileXIIPercentage.setText("Percentage: " + schoolDetails.getString("user_xii_marks"));
                    }
                    else{
                        profileXIIPercentage.setText("");
                        xiicount++;
                    }
                    if(!schoolDetails.getString("user_xii_passing_year").isEmpty() && !schoolDetails.getString("user_xii_passing_year").equals("0000")) {
                        profileXIIPassingyear.setText("Passing Year: " + schoolDetails.getString("user_xii_passing_year"));
                    }
                    else{
                        profileXIIPassingyear.setText("");
                        xiicount++;
                    }

                    if(xiicount==5){
                        profileXIIStream.setText("You have not added any class XII details");
                    }

                    if(!schoolDetails.getString("user_x_school_name").isEmpty()) {
                        profileXSchool.setText(schoolDetails.getString("user_x_school_name"));
                    }
                    else{
                        profileXSchool.setText("");
                        xcount++;
                    }
                    if(!schoolDetails.getString("user_x_board").isEmpty()) {
                        profileXBoard.setText(schoolDetails.getString("user_x_board"));
                    }
                    else{
                        profileXBoard.setText("");
                        xcount++;
                    }
                    if(!schoolDetails.getString("user_x_marks").isEmpty() && !schoolDetails.getString("user_x_marks").equals("0")){
                        profileXPercentage.setText("Percentage: "+schoolDetails.getString("user_x_marks"));
                    }
                    else{
                        profileXPercentage.setText("");
                        xcount++;
                    }
                    if(!schoolDetails.getString("user_x_passing_year").isEmpty() && !schoolDetails.getString("user_x_passing_year").equals("0000")) {
                        profileXPassingyear.setText("Passing Year: " + schoolDetails.getString("user_x_passing_year"));
                    }
                    else{
                        profileXPassingyear.setText("");
                        xcount++;
                    }

                    if(xcount==4){
                        profileXPassingyear.setText("You have not added any class X details");
                    }
                    JSONObject workSample=jsonObject.getJSONObject("user_work_sample");
                    if(!workSample.getString("github_profile").isEmpty()) {
                        profileGitHub.setText(workSample.getString("github_profile"));
                    }
                    else{
                        profileGitHub.setText("Not Provided");
                    }
                    if(!workSample.getString("playstore_profile").isEmpty()) {
                        profilePlayStore.setText(workSample.getString("playstore_profile"));
                    }
                    else{
                        profilePlayStore.setText("Not Provided");
                    }
                    if(!workSample.getString("design_profile").isEmpty()) {
                        profileBehance.setText(workSample.getString("design_profile"));
                    }
                    else{
                        profileBehance.setText("Not Provided");
                    }
                    if(!workSample.getString("blog_profile").isEmpty()) {
                        profileBlogLink.setText(workSample.getString("blog_profile"));
                    }
                    else{
                        profileBlogLink.setText("Not Provided");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "user_id");
                params.put("value", user_id);
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void setAchievements(View v)
    {

        achievementsRecyclerView=v.findViewById(R.id.profileAcademicAchievementsRecyclerView);
        layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        achievementsRecyclerView.setLayoutManager(layoutManager);

        setAchievements();
    }

    public void setAchievements() {
        profileAchievementsTextDialog.setVisibility(View.INVISIBLE);
        achievementIdDataSet.clear();
        achievementNameDataSet.clear();
        achievementDescriptionDataSet.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, oneRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    if(parentArray.length()==0){
                        profileAchievementsTextDialog.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        achievementIdDataSet.add(arrayObject.getString("ua_id"));
                        achievementNameDataSet.add(arrayObject.getString("accolade_title"));
                        achievementDescriptionDataSet.add(arrayObject.getString("accolade_details"));
                    }
                    adapter= new ProfileAcademicAchievementsAdapter(getActivity(),achievementIdDataSet,achievementNameDataSet,achievementDescriptionDataSet);
                    achievementsRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table", "user_accolades");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }
    private void setWorkExperience(View v)
    {

        workExperienceRecyclerView=v.findViewById(R.id.profileAcademicWorkExperienceRecyclerView);
        layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        workExperienceRecyclerView.setLayoutManager(layoutManager);

        setWorkExp();


    }
    public void setWorkExp() {
        profileWorkExperienceTextDialog.setVisibility(View.INVISIBLE);
        workExpIdDataSet.clear();
        companyNameDataSet.clear();
        postionDataSet.clear();
        jobTypeDataSet.clear();
        startDateDataSet.clear();
        endDateDataSet.clear();
        jobDecriptionDataSet.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, oneRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    if(parentArray.length()==0){
                        profileWorkExperienceTextDialog.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        workExpIdDataSet.add(arrayObject.getString("ued_id"));
                        companyNameDataSet.add(arrayObject.getString("user_company"));
                        postionDataSet.add(arrayObject.getString("user_job_profile"));
                        jobTypeDataSet.add(arrayObject.getString("user_job_type"));
                        startDateDataSet.add(arrayObject.getString("user_job_start_date"));
                        endDateDataSet.add(arrayObject.getString("user_job_end_date"));
                        jobDecriptionDataSet.add(arrayObject.getString("user_job_desc"));
                    }

                    adapter= new ProfileAcademicWorkExperienceAdapter(getActivity(),workExpIdDataSet,companyNameDataSet,postionDataSet,jobTypeDataSet,startDateDataSet,endDateDataSet,jobDecriptionDataSet);
                    workExperienceRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table", "user_employment_details");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }
    private void setTrainings(View v)
    {

        trainingsRecyclerView=v.findViewById(R.id.profileAcademicTrainingsRecyclerView);
        layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        trainingsRecyclerView.setLayoutManager(layoutManager);

        setTrainings();

    }

    public void setTrainings() {
        profileTrainingsTextDialog.setVisibility(View.INVISIBLE);
        trainingIdDataSet.clear();
        courseDataSet.clear();
        durationDataSet.clear();
        certDataSet.clear();
        detailsDataSet.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, oneRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    if(parentArray.length()==0){
                        profileTrainingsTextDialog.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<parentArray.length();i++) {
                        arrayObject = parentArray.getJSONObject(i);
                        trainingIdDataSet.add(arrayObject.getString("utd_id"));
                        courseDataSet.add(arrayObject.getString("user_training_course"));
                        certDataSet.add(arrayObject.getString("user_training_company"));
                        durationDataSet.add(arrayObject.getString("user_training_duration"));
                        detailsDataSet.add(arrayObject.getString("user_training_details"));
                    }
                    adapter= new ProfileAcademicTrainingsAdapter(getActivity(),trainingIdDataSet,courseDataSet,certDataSet,durationDataSet,detailsDataSet);
                    trainingsRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table", "user_training_details");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }


    private void setProjects(View v)
    {
        projectsRecyclerView=v.findViewById(R.id.profileAcademicProjectsRecyclerView);
        layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        projectsRecyclerView.setLayoutManager(layoutManager);

        setProjects();

    }

    public void setProjects(){
        profileProjectsTextDialog.setVisibility(View.INVISIBLE);
        projectIdDataSet.clear();
        projectTitleDataSet.clear();
        projectDescriptionDataSet.clear();
        projectLinkDataSet.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, oneRow, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    if(parentArray.length()==0){
                        profileProjectsTextDialog.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        projectIdDataSet.add(arrayObject.getString("up_id"));
                        projectTitleDataSet.add(arrayObject.getString("user_project_name"));
                        projectDescriptionDataSet.add(arrayObject.getString("user_project_details"));
                        projectLinkDataSet.add(arrayObject.getString("user_project_link"));
                    }
                    adapter= new ProfileAcademicProjectsAdapter(getActivity(),projectIdDataSet,projectTitleDataSet,projectLinkDataSet,projectDescriptionDataSet);
                    projectsRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "user_id");
                params.put("value", user_id);
                params.put("table", "user_projects");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void editAchievements(final String achievementId, String achievementName, String achievementDetails){
        LayoutInflater layoutInflater=getLayoutInflater();
        View achievementsAddLayout=layoutInflater.inflate(R.layout.dialog_edit_achievements,null);

        editAchievementsName=achievementsAddLayout.findViewById(R.id.editAchievementsName);
        editAchievementsDescription=achievementsAddLayout.findViewById(R.id.editAchievementsDescription);

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setView(achievementsAddLayout);
        alertDialog.setCancelable(false);

        editAchievementsName.setText(achievementName);
        editAchievementsDescription.setText(achievementDetails);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject arrayObject=new JSONObject(response);
                            status=arrayObject.getInt("status");
                            if(status==200){
                                setAchievements();
                            }
                            else{
                                Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                        params.put("update_array[accolade_title]",editAchievementsName.getText().toString());
                        params.put("update_array[accolade_details]",editAchievementsDescription.getText().toString());
                        params.put("key", "ua_id");
                        params.put("value", achievementId);
                        params.put("table","user_accolades");
                        return params;
                    }
                };
                MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    public class ProfileAcademicAchievementsAdapter extends RecyclerView.Adapter<ProfileAcademicAchievementsAdapter.ProfileAcademicAchievementsHolder> {

        private Context context;
        private ArrayList<String> idDataSet,nameDataSet,descriptionDataSet;

        public ProfileAcademicAchievementsAdapter(Context context, ArrayList<String> idDataSet, ArrayList<String> nameDataSet, ArrayList<String> descriptionDataSet) {
            this.context = context;
            this.idDataSet = idDataSet;
            this.nameDataSet = nameDataSet;
            this.descriptionDataSet = descriptionDataSet;
        }

        @NonNull
        @Override
        public ProfileAcademicAchievementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_profile_academic_achievements,parent,false);
            ProfileAcademicAchievementsHolder holder=new ProfileAcademicAchievementsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileAcademicAchievementsHolder holder, final int position) {
            holder.ProfileAcademicAchievementsName.setText(nameDataSet.get(position));
            holder.ProfileAcademicAchievementsDescription.setText(descriptionDataSet.get(position));
            holder.profileAchievementsEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editAchievements(idDataSet.get(position),nameDataSet.get(position),descriptionDataSet.get(position));
                }
            });
            holder.profileAchievementsDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            getActivity());
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here
                            deleteAchievements(idDataSet.get(position));
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return nameDataSet.size();
        }

        public class ProfileAcademicAchievementsHolder extends RecyclerView.ViewHolder{

            public TextView ProfileAcademicAchievementsName,ProfileAcademicAchievementsDescription;
            public ImageView profileAchievementsEditBtn,profileAchievementsDeleteBtn;

            public ProfileAcademicAchievementsHolder(View itemView) {
                super(itemView);
                ProfileAcademicAchievementsName=itemView.findViewById(R.id.profileAchievementsName);
                ProfileAcademicAchievementsDescription=itemView.findViewById(R.id.profileAchievementsDescription);
                profileAchievementsDeleteBtn=itemView.findViewById(R.id.profileAchievementsDeleteBtn);
                profileAchievementsEditBtn=itemView.findViewById(R.id.profileAchievementsEditBtn);
            }
        }
    }

    private void deleteAchievements(final String ua_id) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    //status=arrayObject.getInt("status");
                    if(arrayObject.getString("status").equals("deleted")){
                        setAchievements();
                        Toast.makeText(getActivity(), "Deletion done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "ua_id");
                params.put("value", ua_id);
                params.put("table", "user_accolades");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void editProjects(final String projectId, String projectTitle, String projectDescription,String projectLink){

        LayoutInflater layoutInflater=getLayoutInflater();
        View projectsAddLayout=layoutInflater.inflate(R.layout.dialog_edit_projects,null);

        editProjectsDescription=projectsAddLayout.findViewById(R.id.editProjectDescription);
        editProjectsLink=projectsAddLayout.findViewById(R.id.editProjectsLink);
        editProjectsTitle=projectsAddLayout.findViewById(R.id.editProjectsTitle);

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setView(projectsAddLayout);
        alertDialog.setCancelable(false);

        editProjectsTitle.setText(projectTitle);
        editProjectsDescription.setText(projectDescription);
        editProjectsLink.setText(projectLink);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject arrayObject=new JSONObject(response);
                            status=arrayObject.getInt("status");
                            if(status==200){
                                setProjects();
                            }
                            else{
                                Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                        params.put("update_array[user_project_name]",editProjectsTitle.getText().toString());
                        params.put("update_array[user_project_details]",editProjectsDescription.getText().toString());
                        params.put("update_array[user_project_link]",editProjectsLink.getText().toString());
                        params.put("key", "up_id");
                        params.put("value", projectId);
                        params.put("table","user_projects");
                        return params;
                    }
                };
                MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    public class ProfileAcademicProjectsAdapter extends RecyclerView.Adapter<ProfileAcademicProjectsAdapter.ProfileAcademicProjectsHolder> {

        private Context context;
        private ArrayList<String> projectIdDataSet,projectTitleDataSet,projectLinkDataSet,descriptionDataSet;

        public ProfileAcademicProjectsAdapter(Context context, ArrayList<String> projectIdDataSet, ArrayList<String> projectTitleDataSet, ArrayList<String> projectLinkDataSet, ArrayList<String> descriptionDataSet) {
            this.context = context;
            this.projectIdDataSet = projectIdDataSet;
            this.projectTitleDataSet = projectTitleDataSet;
            this.projectLinkDataSet = projectLinkDataSet;
            this.descriptionDataSet = descriptionDataSet;
        }

        @NonNull
        @Override
        public ProfileAcademicProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_profile_academic_projects,parent,false);
            ProfileAcademicProjectsHolder holder=new ProfileAcademicProjectsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileAcademicProjectsHolder holder, final int position) {
            holder.ProfileAcademicProjectsTitle.setText(projectTitleDataSet.get(position));
            holder.ProfileAcademicProjectsLink.setText(projectLinkDataSet.get(position));
            holder.ProfileAcademicProjectsDescription.setText(descriptionDataSet.get(position));

            holder.profileProjectsEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editProjects(projectIdDataSet.get(position),projectTitleDataSet.get(position),projectDescriptionDataSet.get(position),projectLinkDataSet.get(position));
                }
            });
            holder.profileProjectsDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            getActivity());
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here
                            deleteProject(projectIdDataSet.get(position));
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return projectTitleDataSet.size();
        }

        public class ProfileAcademicProjectsHolder extends RecyclerView.ViewHolder{

            public TextView ProfileAcademicProjectsTitle,ProfileAcademicProjectsLink,ProfileAcademicProjectsDescription;

            public ImageView profileProjectsEditBtn,profileProjectsDeleteBtn;

            public ProfileAcademicProjectsHolder(View itemView) {
                super(itemView);

                ProfileAcademicProjectsTitle=itemView.findViewById(R.id.profileProjectTitle);
                ProfileAcademicProjectsDescription=itemView.findViewById(R.id.profileDescription);
                ProfileAcademicProjectsLink=itemView.findViewById(R.id.profileProjectLink);

                profileProjectsEditBtn=itemView.findViewById(R.id.profileProjectsEditBtn);
                profileProjectsDeleteBtn=itemView.findViewById(R.id.profileProjectsDeleteBtn);

            }
        }
    }

    private void deleteProject(final String up_id) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    //status=arrayObject.getInt("status");
                    if(arrayObject.getString("status").equals("deleted")){
                        setProjects();
                        Toast.makeText(getActivity(), "Deletion done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "up_id");
                params.put("value", up_id);
                params.put("table", "user_projects");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    public void editTraining(final String trainingId, String trainingCourse, String trainingDuration,String trainingDetails,String trainingCompany){
        LayoutInflater layoutInflater=getLayoutInflater();
        View trainingsAddLayout=layoutInflater.inflate(R.layout.dialog_edit_trainings,null);

        editTrainingsCourse=trainingsAddLayout.findViewById(R.id.editTrainingsCourse);
        editTrainingsDetails=trainingsAddLayout.findViewById(R.id.editTrainingsDetails);
        editTrainingsDuration=trainingsAddLayout.findViewById(R.id.editTrainingsDuration);
        editTrainingsCertifiedBy=trainingsAddLayout.findViewById(R.id.editTrainingsCertifiedBy);

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setView(trainingsAddLayout);
        alertDialog.setCancelable(false);


        editTrainingsCourse.setText(trainingCourse);
        editTrainingsDetails.setText(trainingDetails);
        editTrainingsDuration.setText(trainingDuration);
        editTrainingsCertifiedBy.setText(trainingCompany);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject arrayObject=new JSONObject(response);
                            status=arrayObject.getInt("status");
                            if(status==200){
                                setTrainings();
                            }
                            else{
                                Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                        params.put("update_array[user_training_course]",editTrainingsCourse.getText().toString());
                        params.put("update_array[user_training_company]",editTrainingsCertifiedBy.getText().toString());
                        params.put("update_array[user_training_duration]",editTrainingsDuration.getText().toString());
                        params.put("update_array[user_training_details]",editTrainingsDetails.getText().toString());
                        params.put("key", "utd_id");
                        params.put("value", trainingId);
                        params.put("table","user_training_details");
                        return params;
                    }
                };
                MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    public class ProfileAcademicTrainingsAdapter extends RecyclerView.Adapter<ProfileAcademicTrainingsAdapter.ProfileAcademicTrainingsHolder> {

        private Context context;
        private ArrayList<String> idDataSet,courseDataSet,certifiedByDataSet,durationDataSet,detailsDataSet;

        public ProfileAcademicTrainingsAdapter(Context context, ArrayList<String> idDataSet, ArrayList<String> courseDataSet, ArrayList<String> certifiedByDataSet, ArrayList<String> durationDataSet, ArrayList<String> detailsDataSet) {
            this.context = context;
            this.idDataSet = idDataSet;
            this.courseDataSet = courseDataSet;
            this.certifiedByDataSet = certifiedByDataSet;
            this.durationDataSet = durationDataSet;
            this.detailsDataSet = detailsDataSet;
        }

        @NonNull
        @Override
        public ProfileAcademicTrainingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_profile_academic_trainings,parent,false);
            ProfileAcademicTrainingsHolder holder=new ProfileAcademicTrainingsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileAcademicTrainingsHolder holder, final int position) {
            holder.ProfileAcademicTrainingsCourse.setText(courseDataSet.get(position));
            holder.ProfileAcademicTrainingsDetails.setText(detailsDataSet.get(position));
            holder.ProfileAcademicTrainingsDuration.setText(durationDataSet.get(position));
            holder.ProfileAcademicTrainingsCertifiedBy.setText(certifiedByDataSet.get(position));

            holder.profileTrainingsEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTraining(idDataSet.get(position),courseDataSet.get(position),durationDataSet.get(position),detailsDataSet.get(position),certifiedByDataSet.get(position));
                }
            });

            holder.profileTrainingsDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            getActivity());
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here
                            deleteTraining(idDataSet.get(position));
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();

                }
            });
        }

        @Override
        public int getItemCount() {
            return courseDataSet.size();
        }

        public class ProfileAcademicTrainingsHolder extends RecyclerView.ViewHolder{

            public TextView ProfileAcademicTrainingsCourse,ProfileAcademicTrainingsDuration,ProfileAcademicTrainingsDetails,
                    ProfileAcademicTrainingsCertifiedBy;

            public ImageView profileTrainingsEditBtn,profileTrainingsDeleteBtn;
            public ProfileAcademicTrainingsHolder(View itemView) {
                super(itemView);

                ProfileAcademicTrainingsCourse=itemView.findViewById(R.id.profileTrainingCourse);
                ProfileAcademicTrainingsDuration=itemView.findViewById(R.id.profileDuration);
                ProfileAcademicTrainingsDetails=itemView.findViewById(R.id.profileDetails);
                ProfileAcademicTrainingsCertifiedBy=itemView.findViewById(R.id.profileCertifiedBy);

                profileTrainingsEditBtn=itemView.findViewById(R.id.profileTrainingsEditBtn);
                profileTrainingsDeleteBtn=itemView.findViewById(R.id.profileTrainingsDeleteBtn);
            }
        }
    }

    private void deleteTraining(final String utd_id) {
            StringRequest stringRequest=new StringRequest(Request.Method.POST,urlDelete, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject arrayObject=new JSONObject(response);
                        //status=arrayObject.getInt("status");
                        if(arrayObject.getString("status").equals("deleted")){
                            setTrainings();
                            Toast.makeText(getActivity(), "Deletion done", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                    params.put("key", "utd_id");
                    params.put("value", utd_id);
                    params.put("table", "user_training_details");
                    return params;
                }
            };
            MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void updateLabel(EditText edittext, Calendar calendar) {
        String Format = "yy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.UK);
        edittext.setText(sdf.format(calendar.getTime()));
    };


    public void editWorkExp(final String workExpId, final String workExpCompanyName, String workExpPosition, String workExpJobType, String workExpStartDate, String workExpLastDate, String workExpJobDescription){
        LayoutInflater layoutInflater=getLayoutInflater();
        View workExperienceAddLayout=layoutInflater.inflate(R.layout.dialog_edit_work_experience,null);

        editWorkExperienceCompanyName=workExperienceAddLayout.findViewById(R.id.editWorkExperienceCompanyName);
        editWorkExperiencePosition=workExperienceAddLayout.findViewById(R.id.editWorkExperiencePosition);
        editWorkExperienceJobType=workExperienceAddLayout.findViewById(R.id.editWorkExperienceJobType);
        editWorkExperienceStartDate=workExperienceAddLayout.findViewById(R.id.editWorkExperienceStartDate);
        editWorkExperienceLastDate=workExperienceAddLayout.findViewById(R.id.editWorkExperienceLastDate);
        editWorkExperienceJobDescription=workExperienceAddLayout.findViewById(R.id.editWorkExperienceJobDescription);

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getActivity());
        alertDialog.setView(workExperienceAddLayout);
        alertDialog.setCancelable(false);

        editWorkExperienceCompanyName.setText(workExpCompanyName);
        editWorkExperiencePosition.setText(workExpPosition);
        editWorkExperienceJobType.setText(workExpJobType);
        editWorkExperienceStartDate.setText(workExpStartDate);
        editWorkExperienceLastDate.setText(workExpLastDate);
        editWorkExperienceJobDescription.setText(workExpJobDescription);

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject arrayObject=new JSONObject(response);
                            status=arrayObject.getInt("status");
                            if(status==200){
                                setWorkExp();
                            }
                            else{
                                Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                        params.put("update_array[user_job_type]",editWorkExperienceJobType.getText().toString());
                        params.put("update_array[user_company]",editWorkExperienceCompanyName.getText().toString());
                        params.put("update_array[user_job_profile]",editWorkExperiencePosition.getText().toString());
                        params.put("update_array[user_job_start_date]",editWorkExperienceStartDate.getText().toString());
                        params.put("update_array[user_job_end_date]",editWorkExperienceLastDate.getText().toString());
                        params.put("update_array[user_job_desc]",editWorkExperienceJobDescription.getText().toString());
                        params.put("key", "ued_id");
                        params.put("value",workExpId);
                        params.put("table","user_employment_details");
                        return params;
                    }
                };
                MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    public class ProfileAcademicWorkExperienceAdapter extends RecyclerView.Adapter<ProfileAcademicWorkExperienceAdapter.ProfileAcademicWorkExperienceHolder> {

        private Context context;
        private ArrayList<String> idDataSet,companyNameDataSet,positionDataSet,jobTypeDataSet,startDateDataSet,lastDateDataSet, jobDescriptionDataSet;

        public ProfileAcademicWorkExperienceAdapter(Context context, ArrayList<String> idDataSet, ArrayList<String> companyNameDataSet, ArrayList<String> positionDataSet, ArrayList<String> jobTypeDataSet, ArrayList<String> startDateDataSet, ArrayList<String> lastDateDataSet, ArrayList<String> jobDescriptionDataSet) {
            this.context = context;
            this.idDataSet = idDataSet;
            this.companyNameDataSet = companyNameDataSet;
            this.positionDataSet = positionDataSet;
            this.jobTypeDataSet = jobTypeDataSet;
            this.startDateDataSet = startDateDataSet;
            this.lastDateDataSet = lastDateDataSet;
            this.jobDescriptionDataSet = jobDescriptionDataSet;
        }

        @NonNull
        @Override
        public ProfileAcademicWorkExperienceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_profile_academic_work_experience,parent,false);
            ProfileAcademicWorkExperienceHolder holder=new ProfileAcademicWorkExperienceHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProfileAcademicWorkExperienceHolder holder, final int position) {
            holder.ProfileAcademicWorkExperienceCompanyName.setText(companyNameDataSet.get(position));
            holder.ProfileAcademicWorkExperienceJobType.setText(jobTypeDataSet.get(position));
            holder.ProfileAcademicWorkExperienceStartDate.setText(startDateDataSet.get(position));
            holder.ProfileAcademicWorkExperienceLastDate.setText(lastDateDataSet.get(position));
            holder.ProfileAcademicWorkExperiencePosition.setText(positionDataSet.get(position));
            holder.ProfileAcademicWorkExperienceJobDescription.setText(jobDescriptionDataSet.get(position));

            holder.profileWorkExperienceEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editWorkExp(idDataSet.get(position),companyNameDataSet.get(position),positionDataSet.get(position),jobTypeDataSet.get(position),startDateDataSet.get(position),lastDateDataSet.get(position),jobDescriptionDataSet.get(position));
                }
            });
            holder.profileWorkExperienceDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            getActivity());
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here
                            deleteWorkExp(idDataSet.get(position));
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return companyNameDataSet.size();
        }

        public class ProfileAcademicWorkExperienceHolder extends RecyclerView.ViewHolder{

            public TextView ProfileAcademicWorkExperienceCompanyName,ProfileAcademicWorkExperiencePosition,ProfileAcademicWorkExperienceJobType,
                    ProfileAcademicWorkExperienceStartDate,
                    ProfileAcademicWorkExperienceLastDate,ProfileAcademicWorkExperienceJobDescription;

            public ImageView profileWorkExperienceEditBtn,profileWorkExperienceDeleteBtn;

            public ProfileAcademicWorkExperienceHolder(View itemView) {
                super(itemView);

                ProfileAcademicWorkExperienceCompanyName=itemView.findViewById(R.id.profileCompanyName);
                ProfileAcademicWorkExperiencePosition=itemView.findViewById(R.id.profilePosition);
                ProfileAcademicWorkExperienceJobDescription=itemView.findViewById(R.id.profileJobDescription);
                ProfileAcademicWorkExperienceJobType=itemView.findViewById(R.id.profileJobType);
                ProfileAcademicWorkExperienceStartDate=itemView.findViewById(R.id.profileStartDate);
                ProfileAcademicWorkExperienceLastDate=itemView.findViewById(R.id.profileLastDate);

                profileWorkExperienceDeleteBtn=itemView.findViewById(R.id.profileWorkExperienceDeleteBtn);
                profileWorkExperienceEditBtn=itemView.findViewById(R.id.profileWorkExperienceEditBtn);
            }
        }
    }

    private void deleteWorkExp(final String ued_id) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject=new JSONObject(response);
                    //status=arrayObject.getInt("status");
                    if(arrayObject.getString("status").equals("deleted")){
                        setWorkExp();
                        Toast.makeText(getActivity(), "Deletion done", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Check Again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "ued_id");
                params.put("value", ued_id);
                params.put("table", "user_employment_details");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }
}