package com.example.smartsecurityminor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControlActivity extends AppCompatActivity {

    Switch fanSwitch;
    Boolean switchActive;
    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(fuser.getUid());


    public void toggleSwitch(View view){

        String str1;

        if (fanSwitch.isChecked())
            str1 = fanSwitch.getTextOn().toString();

        else
            str1 = fanSwitch.getTextOff().toString();

        reference.child("switch").setValue(str1);

        Toast.makeText(getApplicationContext(), "Fan turned " + str1, Toast.LENGTH_SHORT).show();


        Log.i("SWITCH TOGGLE",  reference.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        fanSwitch = findViewById(R.id.fanSwitch);
    }
}