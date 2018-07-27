package in.nfly.dell.nflydemo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.nfly.dell.nflydemo.MySingleton;
import in.nfly.dell.nflydemo.R;
import in.nfly.dell.nflydemo.User;
import in.nfly.dell.nflydemo.activities.singleActivities.PersonalityTestActivity;
import in.nfly.dell.nflydemo.adapters.ProfilePersonalityAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePersonalityFragment extends Fragment {

    private TextView profilePersonalityText;

    private RecyclerView profilePersonalityRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private String user_id;
    private String urlPersonality="http://nfly.in/profileapi/personality";

    private ArrayList<String> titleDataSet=new ArrayList<String>(){{add("Extraversion");add("Openness");add("Agreeableness");add("Conscientiousness");add("Neuroticism");}};
    private ArrayList<String> textDataSet=new ArrayList<String>(){{add("Extraversion is characterized by excitability, sociability, talkativeness, assertiveness, and high amounts of emotional expressiveness.\n" +
            "People who are high in extraversion are outgoing and tend to gain energy in social situations. People who are low in extraversion (or introverted) tend to be more reserved and have to expend energy in social settings.");
    add("This trait features characteristics such as imagination and insight, and those high in this trait also tend to have a broad range of interests. People who are high in this trait tend to be more adventurous and creative. People low in this trait are often much more traditional and may struggle with abstract thinking.");
    add("This personality dimension includes attributes such as trust, altruism, kindness, affection, and other prosocial behaviors. People who are high in agreeableness tend to be more cooperative while those low in this trait tend to be more competitive and even manipulative.");
    add("Standard features of this dimension include high levels of thoughtfulness, with good impulse control and goal-directed behaviors. Highly conscientiousness tend to be organized and mindful of details.");
    add("Neuroticism is a trait characterized by sadness, moodiness, and emotional instability. Individuals who are high in this trait tend to experience mood swings, anxiety, irritability and sadness. Those low in this trait tend to be more stable and emotionally resilient.");
    }};
    private ArrayList<String> progressDataSet=new ArrayList<String>(){};
    private ImageView personalityEditBtn;

    private String introText="The following graph reflects upon the personality of the candidate based on 5 different parameters.\n" +
            "1. Extraversion\n" +
            "2. Openness\n" +
            "3. Agreeableness\n" +
            "4. Neuroticism\n" +
            "5. Conscienscioustness\n" +
            "\n" +
            "The graph shows the measure of each parameter on a scale of -10 to 10.\n" +
            "\n" +
            "The comment below the graph has been provided by experts and summarizes the graph.";
    public ProfilePersonalityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_personality, container, false);
        profilePersonalityText=view.findViewById(R.id.profilePersonalityText);
        profilePersonalityRecyclerView=view.findViewById(R.id.profilePersonalityRecyclerView);
        personalityEditBtn=view.findViewById(R.id.personalityEditBtn);

        User user=new User(getActivity());
        user_id=user.getUser_id();

        profilePersonalityText.setText(introText);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        profilePersonalityRecyclerView.setLayoutManager(layoutManager);
        setValues();
        personalityEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PersonalityTestActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }
    private void setValues() {
        progressDataSet.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlPersonality, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);

                    progressDataSet.add(jsonObject.getString("extraversion"));
                    progressDataSet.add(jsonObject.getString("openness"));
                    progressDataSet.add(jsonObject.getString("agreeableness"));
                    progressDataSet.add(jsonObject.getString("conscientiousness"));
                    progressDataSet.add(jsonObject.getString("neuroticism"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter=new ProfilePersonalityAdapter(titleDataSet,textDataSet,progressDataSet,getActivity());
                profilePersonalityRecyclerView.setAdapter(adapter);
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
}
