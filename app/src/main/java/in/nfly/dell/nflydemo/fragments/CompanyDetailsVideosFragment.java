package in.nfly.dell.nflydemo.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.nfly.dell.nflydemo.MySingleton;
import in.nfly.dell.nflydemo.PlayerConfig;
import in.nfly.dell.nflydemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyDetailsVideosFragment extends Fragment {

    public String company_id,company_name,video_url;
    private String urlCompany="http://nfly.in/gapi/load_rows_one";

    //private TextView companyDetailsVideoView;
    private ListView companyDetailsVideoList;

    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private YouTubePlayer companyDetailsYoutubePlayer;

    private ArrayList<String> titleDataSet=new ArrayList<String>(){};
    private ArrayList<String> urlDataSet=new ArrayList<String>(){};

    public CompanyDetailsVideosFragment() {
        // Required empty public constructor
    }

    public static CompanyDetailsVideosFragment newInstance(String company_id, String company_name) {
        CompanyDetailsVideosFragment fragment = new CompanyDetailsVideosFragment();
        fragment.company_id=company_id;
        fragment.company_name=company_name;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_company_details_videos, container, false);
        //companyDetailsVideoView=v.findViewById(R.id.companyDetailsVideoView);

        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.companyDetailsVideoView, youTubePlayerFragment);
        transaction.commit();

        companyDetailsVideoList=v.findViewById(R.id.companyDetailsVideoList);
        setValues();
        return v;
    }

    private void setValues() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, urlCompany, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject arrayObject;
                    JSONArray parentArray=new JSONArray(response);
                    for(int i=0;i<parentArray.length();i++){
                        arrayObject=parentArray.getJSONObject(i);
                        titleDataSet.add((i+1)+".  "+arrayObject.getString("video_title"));
                        urlDataSet.add(arrayObject.getString("video_url"));
                    }
                    if (companyDetailsYoutubePlayer != null) {
                        companyDetailsYoutubePlayer.release();
                    }
                    //video_url=urlDataSet.get(0);
                    video_url="HvselWKPhfY";
                    youTubePlayerFragment.initialize(PlayerConfig.API_KEY, new YouTubePlayer.OnInitializedListener() {

                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                                    if (!b) {
                                        companyDetailsYoutubePlayer = youTubePlayer;
                                        companyDetailsYoutubePlayer.setFullscreen(false);
                                        companyDetailsYoutubePlayer.setShowFullscreenButton(false);
                                        companyDetailsYoutubePlayer.loadVideo(video_url);
                                        companyDetailsYoutubePlayer.play();
                                    }
                                }
                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                            // TODO Auto-generated method stub

                        }
                    });

                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,titleDataSet);
                    companyDetailsVideoList.setAdapter(adapter);
                    companyDetailsVideoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //companyDetailsVideoView.setText(urlDataSet.get(position));
                            video_url=urlDataSet.get(position);
                            if (companyDetailsYoutubePlayer != null) {
                                companyDetailsYoutubePlayer.release();
                            }
                            youTubePlayerFragment.initialize(PlayerConfig.API_KEY, new YouTubePlayer.OnInitializedListener() {

                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                                    if (!b) {
                                        companyDetailsYoutubePlayer= youTubePlayer;
                                        companyDetailsYoutubePlayer.setFullscreen(false);
                                        companyDetailsYoutubePlayer.setShowFullscreenButton(false);
                                        companyDetailsYoutubePlayer.loadVideo(video_url);
                                        companyDetailsYoutubePlayer.play();
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
                params.put("key", "company_id");
                params.put("value", company_id);
                params.put("table", "company_videos");
                return params;
            }
        };
        MySingleton.getmInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
