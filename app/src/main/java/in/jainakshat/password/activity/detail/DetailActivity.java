package in.jainakshat.password.activity.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import in.jainakshat.password.R;
import in.jainakshat.password.model.Entity;

/**
 * Created by Akshat on 5/28/2016.
 */
public class DetailActivity extends AppCompatActivity {

    private Entity mEntity;

    private TextView detailTitle;
    private TextView detailPassDisp;
    private TextView detailInfoDisp;

    private FloatingActionButton detailFab;
    private boolean isFieldEditable = false;

    private EditText detailPassEdit;
    private EditText detailInfoEdit;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference entityRef = database.getReference("entity");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Facebook");
        setSupportActionBar(toolbar);

        mEntity = getIntent().getParcelableExtra("entity");

        detailTitle = (TextView) findViewById(R.id.detail_title);
        detailPassDisp = (TextView) findViewById(R.id.detail_pass_disp);
        detailInfoDisp = (TextView) findViewById(R.id.detail_info_disp);

        detailFab = (FloatingActionButton) findViewById(R.id.detail_fab);

        detailPassEdit = (EditText) findViewById(R.id.detail_pass_edit);
        detailInfoEdit = (EditText) findViewById(R.id.detail_info_edit);

        detailPassEdit.setVisibility(View.GONE);
        detailInfoEdit.setVisibility(View.GONE);

        detailTitle.setText(mEntity.getName());
        detailPassDisp.setText(mEntity.getPassword());
        detailInfoDisp.setText(mEntity.getInformation());

        detailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFieldEditable) {
                    updateValues(detailPassEdit.getText().toString(), detailInfoEdit.getText().toString());
                    detailPassEdit.setVisibility(View.GONE);
                    detailInfoEdit.setVisibility(View.GONE);
                    detailPassDisp.setVisibility(View.VISIBLE);
                    detailInfoDisp.setVisibility(View.VISIBLE);
                    detailFab.setImageDrawable(getDrawable(R.drawable.pencil));
                    isFieldEditable = false;
                }
                else {
                    detailInfoEdit.setText(mEntity.getInformation());
                    detailPassEdit.setText(mEntity.getPassword());
                    detailPassEdit.setVisibility(View.VISIBLE);
                    detailInfoEdit.setVisibility(View.VISIBLE);
                    detailPassDisp.setVisibility(View.GONE);
                    detailInfoDisp.setVisibility(View.GONE);
                    detailFab.setImageDrawable(getDrawable(R.drawable.check));
                    isFieldEditable = true;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(isFieldEditable) {
            detailPassEdit.setVisibility(View.GONE);
            detailInfoEdit.setVisibility(View.GONE);
            detailPassDisp.setVisibility(View.VISIBLE);
            detailInfoDisp.setVisibility(View.VISIBLE);
            detailFab.setImageDrawable(getDrawable(R.drawable.pencil));
            isFieldEditable = false;
        }
        else {
            super.onBackPressed();
        }
    }

    public void updateValues(String password, String information) {
        mEntity.setPassword(password);
        mEntity.setInformation(information);
        detailPassDisp.setText(mEntity.getPassword());
        detailInfoDisp.setText(mEntity.getInformation());
        entityRef.child(mEntity.getName()).setValue(mEntity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(detailFab, "Update Success!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
