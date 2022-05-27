package com.example.smartsecurityminor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Boolean signUpModeActive = true;
    TextView loginTextView;
    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;


    public void showLoginWindow() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


    public void showHomepage(){
        Intent intent2 = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(intent2);
    }



    public void signupClicked(View view) {


        if (nameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("") || emailEditText.getText().toString().matches("")) {
            Toast.makeText(this, "fill all required details", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //   Users user = new Users(nameEditText.getText().toString(), emailEditText.getText().toString(), ageEditText.getText().toString(), task.getResult().getUser().getUid());
                                reference = FirebaseDatabase.getInstance().getReference().child("USERS").child(task.getResult().getUser().getUid());

                                HashMap<String, String> hashMap = new HashMap<>();

                                hashMap.put("username", nameEditText.getText().toString());
                                hashMap.put("email", emailEditText.getText().toString());
                                hashMap.put("id", task.getResult().getUser().getUid());

                                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(MainActivity.this, "SIGNUP SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                            showLoginWindow();
                                        }
                                    }
                                });

                            }else{
                                Toast.makeText(MainActivity.this, "USER EXISTS", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailTextEdit);

        loginTextView =  findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
         if (firebaseUser != null){
             startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
              }


    }

    @Override
    public void onClick(View view) {
        Button signUpButton = findViewById(R.id.signUpButton);
        if (view.getId() == R.id.loginTextView) {

            if (signUpModeActive) {
                // signUpModeActive = false;
                showLoginWindow();
            }

        }
    }

}