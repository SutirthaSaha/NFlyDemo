package in.nfly.dell.nflydemo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.nfly.dell.nflydemo.MySingleton;
import in.nfly.dell.nflydemo.R;
import in.nfly.dell.nflydemo.adapters.HomeIconsAdapter;
import in.nfly.dell.nflydemo.adapters.LearnCourseAdapter;
import in.nfly.dell.nflydemo.adapters.LearnInterviewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LearnInterviewFragment extends Fragment {

    private RecyclerView learnInterviewRecyclerView, learnInterviewBannerRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView learnInterviewImage;
    //private ArrayList<String> titleDataSet=new ArrayList<String>(){{add("HR Questions");add("Software Tools");add("Eng. Topics");add("Company wise");add("Puzzles");add("Miscellaneous");}};
    //private ArrayList<String> imageDataSet=new ArrayList<String>(){{add(Integer.toString(R.drawable.learn_interview_company_wise));add(Integer.toString(R.drawable.ic_computer_white));add(Integer.toString(R.drawable.ic_computer_white));add(Integer.toString(R.drawable.ic_computer_white));add(Integer.toString(R.drawable.ic_computer_white));add(Integer.toString(R.drawable.ic_computer_white));}};

    private String urlInterview="http://nfly.in/gapi/load_all_rows";
    private ArrayList<String> titleDataSet=new ArrayList<String>(){};
    private ArrayList<String> idDataSet=new ArrayList<String>(){};
    private ArrayList<String> imageDataSet=new ArrayList<String>(){};
    private ArrayList<Integer> bannerImageDataSet=new ArrayList<Integer>(){
        {
            add(R.drawable.topics);
            add(R.drawable.test);
            add(R.drawable.coloredtest);}};

    private ArrayList<String> bannerTitleDataSet=new ArrayList<String>(){
        {
            add("6 topics");
            add("25 subtopics");
            add("500+ questions");}};

    public LearnInterviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_learn_interview, container, false);
        learnInterviewRecyclerView=v.findViewById(R.id.learnInterviewRecyclerView);
        layoutManager=new GridLayoutManager(getContext(),2);
        learnInterviewRecyclerView.setLayoutManager(layoutManager);
        learnInterviewImage=v.findViewById(R.id.learnInterviewImage);
        Picasso.with(getContext()).load("https://images2.alphacoders.com/559/559116.png").into(learnInterviewImage);
        setValues();
        setBanner(v);
        return v;
    }
    private void setBanner(View view)
    {
        learnInterviewBannerRecyclerView=view.findViewById(R.id.learnInterviewBannerIconsRecyclerView);
        layoutManager=new GridLayoutManager(getContext(),3);
        learnInterviewBannerRecyclerView.setLayoutManager(layoutManager);

        adapter= new HomeIconsAdapter(getContext(),bannerTitleDataSet,bannerImageDataSet);
        learnInterviewBannerRecyclerView.setAdapter(adapter);

    }

    private void setValues() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlInterview, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        titleDataSet.add(arrayObject.getString("topic_name"));
                        idDataSet.add(arrayObject.getString("topic_id"));
                        imageDataSet.add("http://nfly.in/assets/images/app_icons/"+arrayObject.getString("app_icon"));
                    }
                    adapter=new LearnInterviewAdapter(getContext(),titleDataSet,imageDataSet,idDataSet);
                    learnInterviewRecyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

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
                params.put("key", "topic_id");
                params.put("order", "ASC");
                params.put("table", "nfly_interview_topics");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
