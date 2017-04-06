package com.learnshare.firebaseauth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail , etPassword;

    private Button btnRegisterUser;

    private FirebaseAuth mAuth ;

    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etEmail = (EditText) findViewById(R.id.etEmail);

        etPassword = (EditText) findViewById(R.id.etPassword);

        btnRegisterUser = (Button) findViewById(R.id.btnRegisterUser);

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // here we will do out stuff

                mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful())
                        {
                           // creation of user is unsuccessful
                            Toast.makeText(MainActivity.this,"Error creating the user",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user!=null)
                {
                    // successful scenario

                    Toast.makeText(MainActivity.this,"Successfull creating the user",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    // unsuccessful scenario

                    Toast.makeText(MainActivity.this,"Error creating the user",Toast.LENGTH_SHORT).show();
                }

            }
        };



    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
