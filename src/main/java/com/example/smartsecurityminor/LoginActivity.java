package com.example.smartsecurityminor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView signUpTextView;
    private FirebaseAuth mAuth;


    public void showSignUpWindow(){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void showHomepage(){
        Intent intent1 = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(intent1);
    }

    public void loginClicked(View view) {
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);

        if (emailEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
            Toast.makeText(this, "fill all required details", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                showHomepage();
                            } else {
                                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpTextView = findViewById(R.id.signupTextView);
        signUpTextView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signupTextView) {
            showSignUpWindow();
        }
    }
}