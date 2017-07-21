package in.jainakshat.password.activity.password;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.jainakshat.password.R;
import in.jainakshat.password.SharedPreferencesManager;
import in.jainakshat.password.activity.main.MainActivity;

/**
 * Created by Akshat on 26-06-2017.
 */

public class PasswordActivity extends AppCompatActivity {

    private TextView mPasswordMessage;

    private EditText mPasswordInput;
    private EditText mPasswordInputComfirm;

    private Button mPasswordNextButton;
    private Button getmPasswordLogoutButton;

    private CoordinatorLayout mPasswordCoordinatorLayout;

    private Boolean isPassSet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isPassSet = false;

        mPasswordCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.password_coordinator_layout);

        mPasswordInput = (EditText) findViewById(R.id.password_input);
        mPasswordInputComfirm = (EditText) findViewById(R.id.password_input_confirm);
        mPasswordMessage = (TextView) findViewById(R.id.password_message);

        mPasswordNextButton = (Button) findViewById(R.id.password_next_button);
        getmPasswordLogoutButton = (Button) findViewById(R.id.password_logout_button);

        mPasswordInput.requestFocus();

        if(SharedPreferencesManager.getPassKey(getApplicationContext()) >= 0000 && SharedPreferencesManager.getPassKey(getApplicationContext()) <= 9999) {
            isPassSet = true;
            mPasswordInputComfirm.setVisibility(View.GONE);
            mPasswordMessage.setText("Enter your PassCode to continue");
        } else {
            isPassSet = false;
            mPasswordInputComfirm.setVisibility(View.VISIBLE);
            mPasswordMessage.setText("Set a new PassCode to access your passwords");
        }

        mPasswordNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPassSet) {
                    int enteredPassCode = Integer.parseInt(mPasswordInput.getText().toString());
                    if(enteredPassCode == SharedPreferencesManager.getPassKey(getApplicationContext())) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Snackbar.make(mPasswordCoordinatorLayout, "Incorrect Pin", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    int enteredPassCode = Integer.parseInt(mPasswordInput.getText().toString());
                    int enteredPassCodeConfirm = Integer.parseInt(mPasswordInputComfirm.getText().toString());
                    if(enteredPassCode == enteredPassCodeConfirm) {
                        SharedPreferencesManager.setPassKey(enteredPassCode);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Snackbar.make(mPasswordCoordinatorLayout, "Pins does not match.", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        getmPasswordLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
