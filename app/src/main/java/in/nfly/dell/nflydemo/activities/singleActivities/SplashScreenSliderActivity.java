package in.nfly.dell.nflydemo.activities.singleActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import in.nfly.dell.nflydemo.R;
import in.nfly.dell.nflydemo.activities.LoginActivity;
import in.nfly.dell.nflydemo.adapters.SplashScreenSwipeAdapter;
import me.relex.circleindicator.CircleIndicator;

public class SplashScreenSliderActivity extends AppCompatActivity {
    private int[] swipeImageDataSet= {R.drawable.cv,R.drawable.presentation,R.drawable.exam};
    private String[] swipeTitleDataSet={"Resume Builder","Video Courses","Mock Tests"};
    private String[] swipeSubtitleDataSet={"Build your own resume with no effort","Watch videos to learn etc etc","Solve mock tests blahblah this and that"};

    private static int currentPage = 0;
    private static int NUM_PAGES;
    private ViewPager viewPager;
    private SplashScreenSwipeAdapter swipeAdapter;
    private TextView Previous, Next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_slider);

        Previous=findViewById(R.id.SplashScreenSwipePrev);
        Next=findViewById(R.id.SplashScreenSwipeNext);


        setViewPager();
    }
    private void setViewPager()
    {
        viewPager=findViewById(R.id.splashScreenViewPager);
        swipeAdapter= new SplashScreenSwipeAdapter(this,swipeImageDataSet,swipeTitleDataSet,swipeSubtitleDataSet);
        viewPager.setAdapter(swipeAdapter);
        CircleIndicator indicator = findViewById(R.id.splashScreenSliderIndicator);
        indicator.setViewPager(viewPager);
        NUM_PAGES=swipeImageDataSet.length;

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage==NUM_PAGES){
                    Next.setVisibility(View.VISIBLE);
                    Next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent= new Intent(SplashScreenSliderActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });

                    currentPage=0;

                  //  Previous.setVisibility(View.VISIBLE);

                }
                else if(currentPage==0)
                {
                    //Previous.setVisibility(View.GONE);
                   // Next.setVisibility(View.VISIBLE);

                }
                else
                {

                    //Next.setVisibility(View.VISIBLE);
                   // Previous.setVisibility(View.VISIBLE);
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
}
