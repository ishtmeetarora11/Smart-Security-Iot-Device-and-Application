package com.example.smartsecurityminor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomepageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    TextView usernameTextViewHome;
    TextView tempText;
    TextView humidText;
    TextView motionText;
    TextView gasText;
    Button controlHomeButton;

    public void buttonclicked(View view){
        showControlWindow();
    }


    public void showControlWindow(){
        Intent intent = new Intent(getApplicationContext(), ControlActivity.class);
        startActivity(intent);
    }

    public void showLogInWindow(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(NotificationChannel.DEFAULT_CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        controlHomeButton = findViewById(R.id.controlHomeButton);

        tempText = findViewById(R.id.tempText);
        humidText = findViewById(R.id.humidText);
        gasText = findViewById(R.id.gasText);
        motionText = findViewById(R.id.motionText);
        usernameTextViewHome = findViewById(R.id.usernameTextViewHome);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

      //TRY REPLACING KEY W USER.UID()
       DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
       Log.i("DATABASE REF",myRef.toString());

 


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.i("SNAPSHOT VAL", "THIS WORKS");
                Log.i("SNAPSHOT VAL", snapshot.toString());


                String temp = snapshot.child("temperature").getValue().toString();
                tempText.setText(temp);

                String humid = snapshot.child("humidity").getValue().toString();
                humidText.setText(humid);

                String motion = snapshot.child("motionStatus").getValue().toString();
                motionText.setText(motion);
                if (motion.equals("Yes")){

                    Log.i("NOTIFICATION", "WORKS");

                    Intent intent1 = new Intent(getApplicationContext(), HomepageActivity.class);

                    showNotification(getApplicationContext(), "Notification", "Intruder alert", intent1);


                }

                String gas = snapshot.child("gasLevels").getValue().toString();
                gasText.setText(gas);

                String uname = snapshot.child("username").getValue().toString();
                usernameTextViewHome.setText(uname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomepageActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.logOut :
                FirebaseAuth.getInstance().signOut();
                showLogInWindow();
                return true;
            default :
                return false;
        }

    }

    @Override
    public void onBackPressed() {
        mAuth.signOut();
        super.onBackPressed();
    }
}
