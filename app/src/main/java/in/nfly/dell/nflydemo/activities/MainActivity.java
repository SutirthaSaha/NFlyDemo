package in.nfly.dell.nflydemo.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import in.nfly.dell.nflydemo.MySingleton;
import in.nfly.dell.nflydemo.R;
import in.nfly.dell.nflydemo.User;
import in.nfly.dell.nflydemo.adapters.HomeCareerAdapter;
import in.nfly.dell.nflydemo.adapters.HomeCompanyAdapter;
import in.nfly.dell.nflydemo.adapters.HomeCourseAdapter;
import in.nfly.dell.nflydemo.adapters.HomeFeaturesIconsAdapter;
import in.nfly.dell.nflydemo.adapters.HomeIconsAdapter;
import in.nfly.dell.nflydemo.adapters.HomePracticeAdapter;
import in.nfly.dell.nflydemo.adapters.HomePrepHubAdapter;
import in.nfly.dell.nflydemo.adapters.HomeSwipeAdapter;
import in.nfly.dell.nflydemo.adapters.HomeTestimonialsAdapter;
import in.nfly.dell.nflydemo.adapters.KnowledgeBaseCompanyWiseAdapter;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayoutHome;
    private Toolbar toolbar;
    private TextView headerTitle;

    private ViewPager viewPager;
    private HomeSwipeAdapter swipeAdapter;

    private RecyclerView HomeBannerIconsRecyclerView;
    private RecyclerView HomeFeatureIconsRecyclerView;
    private RecyclerView CareerRecyclerView;
    private RecyclerView CoursesRecyclerView;
    private RecyclerView CompanyRecyclerView;;
    private RecyclerView PractiseRecyclerView;
    private RecyclerView PrepHubIconsRecyclerView, TestimonialsRecyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ProgressDialog progressDialog;

    private static int currentPage = 0;
    private static int NUM_PAGES;


    private String userDetails="http://nfly.in/gapi/get_details_one";
    private String urlLoadAllRows="http://nfly.in/gapi/load_all_rows";
    private String email;
    
    private int[] swipeImageDataSet= {R.drawable.home_swipe_video,R.drawable.imagepaper,R.drawable.home_swipe_res};
    private String[] swipeTitleDataSet={"Nfly Prepstation","Video Courses","Mock Tests"};
    private String[] swipeSubtitleDataSet={"Everything you needed for campus placements!","More than 200 videos on 7 different courses","50+ tests across 15 diverse topics"};


    private ArrayList<Integer> featureImageDataSet=new ArrayList<Integer>(){
        {
            add(R.drawable.colorvideo);
            add(R.drawable.coloredtest);
            add(R.drawable.colored_company);
            add(R.drawable.colored_placementpapers);
            add(R.drawable.colored_prep);
            add(R.drawable.colorresume);}};
    private ArrayList<String> featureTitleDataSet=new ArrayList<String>(){
        {add("Video Courses");
            add("Weekly Test Series");
            add("Company/Topic Test");
            add("Placement Papers");
            add("Preparation Hub");
            add("Resume Builder");}};
    private ArrayList<Integer> prepHubImageDataSet=new ArrayList<Integer>(){
        {
            add(R.drawable.presentation);
            add(R.drawable.meeting);
            add(R.drawable.reunion);
            add(R.drawable.planning);
            add(R.drawable.tipps);}};
    private ArrayList<Integer> prepHubIdDataSet=new ArrayList<Integer>(){
        {   add(R.id.learn_navigation_course);
            add(R.id.learn_navigation_interview);
            add(R.id.learn_navigation_gd);
            add(R.id.learn_navigation_papers);
            add(R.id.learn_navigation_tips);
        }
    };
    private ArrayList<String> prepHubTitleDataSet=new ArrayList<String>(){
        {add("Video Courses");
            add("Interviews");
            add("Group Discussions");
            add("Placement Papers");
            add("Tips");
           }};
    private ArrayList<String> prepHubSubTitleDataSet=new ArrayList<String>(){
        {add("7 courses");
            add("500+ questions");
            add("100+ topics");
            add("50+ companies");
            add("10 topics");
        }};
    private ArrayList<Integer> practiceIdDataSet=new ArrayList<Integer>(){
        {
            add(R.id.practice_navigation_company_wise);
            add(R.id.practice_navigation_topic_wise);
            add(R.id.practice_navigation_exam_wise);
            add(R.id.practice_navigation_test_series);
        }
    };
    private ArrayList<Integer> practiceImageDataSet=new ArrayList<Integer>(){
        {
            add(R.drawable.colored_company);
            add(R.drawable.colored_placementpapers);
            add(R.drawable.colored_prep);
            add(R.drawable.exam);}};
    private ArrayList<String> practiceTitleDataSet=new ArrayList<String>(){
        {add("Company Wise");
            add("Topic Wise");
            add("Exam Wise");
            add("Test Series");
        }};
    private ArrayList<String> practiceSubTitleDataSet=new ArrayList<String>(){
        {add("50+ companies");
            add("3 tests");
            add("10+ subjects");
            add("Coming soon");
        }};
    private ArrayList<String> testimonialTextDataSet=new ArrayList<String>(){
        {  add("I'm from a private college that too not very reputed. Hence it was very hard for me to get proper opportunites to get jobs especially on campus." +
                "Nfly has been a saviour by providing a platform which binds everything that we need to prepare for jobs, from the technical learning aspects to the grooming.");
            add("Nfly made everything so much simpler." +
                " The tests helped me understand what I need to read up to appear for the interviews"
                );
            add("I have been following the video courses to understand the various courses. The topics were very well explained" +
                    "and i had no trouble learning something new that too from home while i have so much trouble learning similar things at college.");
            add("I needed a place to know more about the various companies and the criteria that I needed to fulfil to be a suitable candidate " +
                    "for jobs. Nfly was the perfect place to look for all of those all in the same place");

        }};
    private ArrayList<String> testimonialNameDataSet=new ArrayList<String>(){
        {add("-Sambarta Goswami");
            add("-Torsha Dutta");
            add("-Riya Ghosh");
            add("-Shantanu Banik");
        }};


    private ArrayList<Integer> bannerImageDataSet=new ArrayList<Integer>(){
        {
        add(R.drawable.colored_video);
            add(R.drawable.colored_group);
        add(R.drawable.colored_articles);}};

    private ArrayList<String> bannerTitleDataSet=new ArrayList<String>(){
        {
            add("200+ in-depth videos");
            add("2500+ happy users");
            add("100+ articles");}};

    private ArrayList<String> companyIdDataSet=new ArrayList<String>(){};
    private ArrayList<String> companyImageDataSet=new ArrayList<String>(){};
    private ArrayList<String> companyTitleDataSet=new ArrayList<String>(){};

    private ArrayList<String> careerIdDataSet=new ArrayList<String>(){};
    private ArrayList<String> careerImageDataSet=new ArrayList<String>(){};
    private ArrayList<String> careerTitleDataSet=new ArrayList<String>(){};

    private ArrayList<String> courseIdDataSet=new ArrayList<String>(){};
    private ArrayList<String> courseImageDataSet=new ArrayList<String>(){};
    private ArrayList<String> courseTitleDataSet=new ArrayList<String>(){};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        getUserDetails(email);
        setToolbar();
        drawerLayoutHome=findViewById(R.id.drawerLayoutHome);

        setToolbar();
        setNavigationDrawer();
        setViewPager();
        setBanner();
        setFeaturesIcons();
        setPrepHubIcons();
        setPractiseIcons();
        setCompanyCards();
        setCareerCards();
        setCoursesCards();
        setTestomialCards();
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void setCoursesCards()
    {
        CoursesRecyclerView=findViewById(R.id.homeCoursesRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
        CoursesRecyclerView.setLayoutManager(layoutManager);

        setValuesCourses();
    }

    private void setValuesCourses(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlLoadAllRows, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        courseTitleDataSet.add(arrayObject.getString("nfly_course_name"));
                        courseImageDataSet.add(arrayObject.getString("nfly_course_bg"));
                        courseIdDataSet.add(arrayObject.getString("nfly_course_id"));
                    }
                    adapter= new HomeCourseAdapter(MainActivity.this,courseIdDataSet,courseTitleDataSet,courseImageDataSet);
                    CoursesRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("key", "course_id");
                params.put("order", "ASC");
                params.put("table", "nfly_course");
                return params;
            }
        };
        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }
    private void setTestomialCards()
    {
        TestimonialsRecyclerView=findViewById(R.id.homeTestimonialsRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
        TestimonialsRecyclerView.setLayoutManager(layoutManager);
        adapter= new HomeTestimonialsAdapter(testimonialTextDataSet,testimonialNameDataSet);
        TestimonialsRecyclerView.setAdapter(adapter);

    }

    private void setCareerCards()
    {
        CareerRecyclerView=findViewById(R.id.homeCareerRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
        CareerRecyclerView.setLayoutManager(layoutManager);
        setValuesCareer();

    }

    private void setValuesCareer(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlLoadAllRows, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        careerIdDataSet.add(arrayObject.getString("job_role_id"));
                        careerTitleDataSet.add(arrayObject.getString("job_role"));
                        careerImageDataSet.add("http://nfly.in/assets/images/job_role/"+arrayObject.getString("job_role_bg"));
                    }
                    careerIdDataSet.add("8");
                    careerTitleDataSet.add("View All");
                    careerImageDataSet.add("none");
                    adapter= new HomeCareerAdapter(MainActivity.this,careerIdDataSet,careerTitleDataSet,careerImageDataSet);
                    CareerRecyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("key", "job_role_id");
                params.put("order", "ASC");
                params.put("table", "job_role");
                return params;
            }
        };
        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }
    private void setCompanyCards()
    {
        CompanyRecyclerView=findViewById(R.id.homeCompanyRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
        CompanyRecyclerView.setLayoutManager(layoutManager);
        setValuesCompany();
    }

    private void setValuesCompany() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlLoadAllRows, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<7;i++){
                        arrayObject=parentArray.getJSONObject(i);
                        companyIdDataSet.add(arrayObject.getString("company_id"));
                        companyTitleDataSet.add(arrayObject.getString("company_name"));
                        companyImageDataSet.add("http://nfly.in/assets/images/company/"+arrayObject.getString("company_cover"));
                    }
                    companyIdDataSet.add("8");
                    companyTitleDataSet.add("View All");
                    companyImageDataSet.add("none");

                    adapter= new HomeCompanyAdapter(MainActivity.this,companyIdDataSet,companyTitleDataSet,companyImageDataSet);
                    CompanyRecyclerView.setAdapter(adapter);
                    progressDialog.cancel();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                params.put("key", "company_id");
                params.put("order", "ASC");
                params.put("table", "company");
                return params;
            }
        };
        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

    private void setPractiseIcons()
    {
        PractiseRecyclerView=findViewById(R.id.homePractiseRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
        PractiseRecyclerView.setLayoutManager(layoutManager);

        adapter= new HomePracticeAdapter(MainActivity.this,practiceTitleDataSet,practiceSubTitleDataSet,practiceImageDataSet,practiceIdDataSet);
        PractiseRecyclerView.setAdapter(adapter);
    }

    private void setPrepHubIcons()
    {
        PrepHubIconsRecyclerView=findViewById(R.id.homePrepHubRecyclerView);
        layoutManager=new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,false);
        PrepHubIconsRecyclerView.setLayoutManager(layoutManager);

        adapter= new HomePrepHubAdapter(MainActivity.this,prepHubTitleDataSet,prepHubSubTitleDataSet,prepHubImageDataSet,prepHubIdDataSet);
        PrepHubIconsRecyclerView.setAdapter(adapter);

    }

    private void setFeaturesIcons()
    {
        HomeFeatureIconsRecyclerView=findViewById(R.id.homeFeaturesIconsRecyclerView);
        layoutManager=new GridLayoutManager(MainActivity.this,3);
        HomeFeatureIconsRecyclerView.setLayoutManager(layoutManager);

        adapter= new HomeFeaturesIconsAdapter(MainActivity.this,featureTitleDataSet,featureImageDataSet);
        HomeFeatureIconsRecyclerView.setAdapter(adapter);
    }

    private void setBanner()
    {
        HomeBannerIconsRecyclerView=findViewById(R.id.homeBannerIconsRecyclerView);
        layoutManager=new GridLayoutManager(MainActivity.this,3);
        HomeBannerIconsRecyclerView.setLayoutManager(layoutManager);

        adapter= new HomeIconsAdapter(MainActivity.this ,bannerTitleDataSet,bannerImageDataSet);
        HomeBannerIconsRecyclerView.setAdapter(adapter);

    }

    private void setViewPager()
    {
        viewPager=findViewById(R.id.homeViewPager);
        swipeAdapter= new HomeSwipeAdapter(this,swipeImageDataSet,swipeTitleDataSet,swipeSubtitleDataSet);
        viewPager.setAdapter(swipeAdapter);
        CircleIndicator indicator = findViewById(R.id.homeSliderIndicator);
        indicator.setViewPager(viewPager);
        NUM_PAGES=swipeImageDataSet.length;

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage==NUM_PAGES){
                    currentPage=0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);}
        }, 2000, 2000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setNavigationDrawer() {
        final NavigationView navigationView;
        navigationView = findViewById(R.id.navigationViewHome);

        User user=new User(MainActivity.this);
        View header=navigationView. getHeaderView(0);
        headerTitle=header.findViewById(R.id.headerTitle);
        headerTitle.setText(user.getFname());
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(MainActivity.this,drawerLayoutHome,toolbar,R.string.drawer_open,R.string.drawer_close);

        drawerLayoutHome.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this,item.getTitle(),Toast.LENGTH_LONG).show();
                Intent intent;
                if (item.getTitle().equals("Home")){
                    intent=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Dashboard")){
                    intent=new Intent(MainActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Profile")){
                    intent=new Intent(MainActivity.this,ProfileActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Learn")){
                    intent=new Intent(MainActivity.this,LearnActivity.class);
                    startActivity(intent);
                }
                if(item.getTitle().equals("Practice")){
                    intent=new Intent(MainActivity.this,PracticeActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Knowledge Base")){
                    intent=new Intent(MainActivity.this,KnowledgeBaseActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Salary Calculator")){
                    intent=new Intent(MainActivity.this,SalaryCalculatorActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Feedback")){
                    intent=new Intent(MainActivity.this,FeedBackActivity.class);
                    startActivity(intent);
                }
                if (item.getTitle().equals("Help")){
                    intent=new Intent(MainActivity.this,HelpActivity.class);
                    startActivity(intent);
                }
                if(item.getTitle().equals("Sign Out")){
                    User user=new User(MainActivity.this);
                    user.logOut();
                    intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                drawerLayoutHome.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.homeToolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void getUserDetails(final String  email) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject;
                    arrayObject=new JSONObject(response);
                    String user_id=arrayObject.getString("user_id");
                    String fname=arrayObject.getString("fname");

                    SharedPreferences sharedPreferences=getSharedPreferences("Login Details", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user_id",user_id);
                    editor.putString("fname",fname);
                    editor.putString("email",email);
                    editor.apply();

                    User user=new User(MainActivity.this);
                    Toast.makeText(MainActivity.this,user.getEmail()+"\n"+user.getFname()+"\n"+user.getUser_id(), Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }
}
