package com.zinftech.google;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import java.util.ArrayList;

public class Activity_AboutUs extends AppCompatActivity
{

    private DatabaseReference databaseReference;
    //private String userMailID;
    private ArrayList<String> AdminArray;
    private Boolean admin=false;
    private TextView mailId;
    private TextView txt_versionName;
    String versionName = "";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__about_us);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));


        AdminArray = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        userMailID = currentFirebaseUser.getEmail();
        mailId = (TextView) findViewById(R.id.support_mail);
        txt_versionName = findViewById(R.id.version_name);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        txt_versionName.setText("Version : "+versionName);


        mailId.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "support@zinftech.com", null));
                // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Rquest for subscription");
                startActivity(Intent.createChooser(emailIntent, null));
            }
        });

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        txt_versionName.setText("Version : "+versionName);

    }

    @Override
    public void onBackPressed()
    {

                    Intent intent = new Intent(Activity_AboutUs.this,FragmentTest.class);
                    startActivity(intent);



    }

}