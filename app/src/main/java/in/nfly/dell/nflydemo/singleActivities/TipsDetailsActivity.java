package in.nfly.dell.nflydemo.singleActivities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import in.nfly.dell.nflydemo.R;

public class TipsDetailsActivity extends AppCompatActivity {

    private String tip_id,topic_name,topic_text;
    private Toolbar toolbar;
    private TextView tipsDetailsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_details);

        Intent intent=getIntent();
        tip_id=intent.getStringExtra("tip_id");
        topic_name=intent.getStringExtra("topic_name");
        topic_text=intent.getStringExtra("topic_text");

        Toast.makeText(this, topic_text, Toast.LENGTH_SHORT).show();
        setToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tipsDetailsTextView=findViewById(R.id.tipsDetailsTextView);
        tipsDetailsTextView.setText(topic_text);
    }

    private void setToolbar() {
        toolbar=findViewById(R.id.tipsDetailsToolbar);
        toolbar.setTitle(topic_name);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
    }
}