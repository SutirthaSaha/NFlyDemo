package in.nfly.dell.nflydemo.fragments.JobWiseFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.nfly.dell.nflydemo.MySingleton;
import in.nfly.dell.nflydemo.PlayerConfig;
import in.nfly.dell.nflydemo.R;
import in.nfly.dell.nflydemo.activities.CourseStudyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobRoleVideosFragment extends Fragment {

    private String job_role_id,job_role_name,video_url;
    private String urlJob="http://nfly.in/gapi/load_rows_one";

    //private TextView jobRoleVideoView;
    private ListView jobRoleVideoList;

    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private YouTubePlayer jobRoleYoutubePlayer;

    private ArrayList<String> titleDataSet=new ArrayList<String>(){};
    private ArrayList<String> urlDataSet=new ArrayList<String>(){};

    public JobRoleVideosFragment() {
        // Required empty public constructor
    }

    public static JobRoleVideosFragment newInstance(String job_role_id, String job_role_name) {
        JobRoleVideosFragment fragment=new JobRoleVideosFragment();
        fragment.job_role_id=job_role_id;
        fragment.job_role_name=job_role_name;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_job_role_videos, container, false);
        //jobRoleVideoView=v.findViewById(R.id.jobRoleVideoView);
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.jobRoleVideoView, youTubePlayerFragment);
        transaction.commit();

        jobRoleVideoList=v.findViewById(R.id.jobRoleVideoList);
        setValues();
        return v;
    }

    private void setValues() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlJob, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        titleDataSet.add((i+1)+".  "+arrayObject.getString("video_name"));
                        urlDataSet.add(arrayObject.getString("video_iframe"));
                    }
                    if (jobRoleYoutubePlayer != null) {
                        jobRoleYoutubePlayer.release();
                    }
                    //video_url=urlDataSet.get(0);
                    video_url="HvselWKPhfY";
                    youTubePlayerFragment.initialize(PlayerConfig.API_KEY, new YouTubePlayer.OnInitializedListener() {

                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                            if (!b) {
                                jobRoleYoutubePlayer= youTubePlayer;
                                jobRoleYoutubePlayer.setFullscreen(false);
                                jobRoleYoutubePlayer.setShowFullscreenButton(false);
                                jobRoleYoutubePlayer.loadVideo(video_url);
                                jobRoleYoutubePlayer.play();
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                            // TODO Auto-generated method stub

                        }
                    });
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,titleDataSet);
                    jobRoleVideoList.setAdapter(adapter);
                    jobRoleVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //jobRoleVideoView.setText(urlDataSet.get(position));
                            video_url=urlDataSet.get(position);
                            if (jobRoleYoutubePlayer != null) {
                                jobRoleYoutubePlayer.release();
                            }
                            youTubePlayerFragment.initialize(PlayerConfig.API_KEY, new YouTubePlayer.OnInitializedListener() {

                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                                    if (!b) {
                                        jobRoleYoutubePlayer= youTubePlayer;
                                        jobRoleYoutubePlayer.setFullscreen(false);
                                        jobRoleYoutubePlayer.setShowFullscreenButton(false);
                                        jobRoleYoutubePlayer.loadVideo(video_url);
                                        jobRoleYoutubePlayer.play();
                                    }
                                }

                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                                    // TODO Auto-generated method stub

                                }
                            });
                        }
                    });


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
                params.put("key", "job_role_id");
                params.put("value", job_role_id);
                params.put("table", "jr_video");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
