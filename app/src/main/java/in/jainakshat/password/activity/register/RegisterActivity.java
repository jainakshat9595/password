package in.jainakshat.password.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import in.jainakshat.password.BaseActivity;
import in.jainakshat.password.FirebaseUtils;
import in.jainakshat.password.R;
import in.jainakshat.password.Utils;
import in.jainakshat.password.activity.login.LoginActivity;
import in.jainakshat.password.activity.password.PasswordActivity;

/**
 * Created by Akshat on 25-06-2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser mCurrentUser;

    private Button mNextButton;
    private Button mLoginButton;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private EditText mInputConfirmPassword;

    private TextInputLayout mLayoutInputEmail;
    private TextInputLayout mLayoutInputPassword;
    private TextInputLayout mLayoutInputConfirmPassword;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mNextButton = (Button) findViewById(R.id.next_button);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mInputEmail = (EditText) findViewById(R.id.input_email);
        mInputPassword= (EditText) findViewById(R.id.input_password);
        mInputConfirmPassword = (EditText) findViewById(R.id.input_con_password);

        mLayoutInputEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        mLayoutInputPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        mLayoutInputConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_con_password);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mCurrentUser = firebaseAuth.getCurrentUser();
                if(mCurrentUser != null) {
                    // Signed In
                    System.out.println("Auth State Listener: "+mCurrentUser.getUid()+" Signed In");
                    Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                    startActivity(intent);
                } else {
                    // Signed Out
                    System.out.println("Auth State Listener: Signed Out");
                }
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mInputEmail.getText().toString();
                String password = mInputPassword.getText().toString();
                String confirm_password = mInputConfirmPassword.getText().toString();

                System.out.println("mInputEmail: " + email);
                System.out.println("mInputPassword: " + password);
                System.out.println("mInputConfirmPassword: " + confirm_password);

                mLayoutInputEmail.setError("");
                mLayoutInputPassword.setError("");
                mLayoutInputConfirmPassword.setError("");

//                if(email == "" || password == "" || confirm_password == "" || password!=confirm_password) {
//
//                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        if(e instanceof FirebaseAuthWeakPasswordException) {
                            mLayoutInputPassword.setError(e.getLocalizedMessage());
                        } else if(e instanceof FirebaseAuthUserCollisionException) {
                            mLayoutInputEmail.setError(e.getLocalizedMessage());
                        } else if(e instanceof FirebaseAuthInvalidCredentialsException) {
                            mLayoutInputEmail.setError(e.getLocalizedMessage());
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        System.out.println("Success");
                    }
                });
            }
        });
    }
}

//    addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//        @Override
//        public void onComplete(@NonNull Task<AuthResult> task) {
//            if(!task.isSuccessful()) {
//                try {
//                    throw task.getException();
//                } catch(FirebaseAuthWeakPasswordException e) {
//                    mLayoutInputPassword.setError(e.getLocalizedMessage());
//                    mLayoutInputPassword.requestFocus();
//                } catch(FirebaseAuthInvalidCredentialsException e) {
//                    mLayoutInputEmail.setError(e.getLocalizedMessage());
//                    mLayoutInputEmail.requestFocus();
//                } catch(FirebaseAuthUserCollisionException e) {
//                    mLayoutInputEmail.setError(e.getLocalizedMessage());
//                    mLayoutInputEmail.requestFocus();
//                } catch(Exception e) {
//                    Log.e(Utils.TAG, e.getMessage());
//                }
//                System.out.println("Login Failed"+task.getResult());
//            }
//        }
//    })