package in.jainakshat.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Akshat on 5/28/2016.
 */
public class DetailActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Facebook");
        setSupportActionBar(toolbar);

        intent = getIntent();
        intent.getExtras();
    }
}
