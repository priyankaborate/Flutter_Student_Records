package com.zinftech.google;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Activity_TermsOfService extends AppCompatActivity {

    WebView webView;
    private DatabaseReference databaseReference;
    private String userMailID;
    private ArrayList<String> AdminArray;
    private Boolean admin = false;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("Recycle")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__terms_of_service);

        //  databaseReference = FirebaseDatabase.getInstance().getReference();
        //   FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //userMailID = currentFirebaseUser.getEmail();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));


        AdminArray = new ArrayList<>();

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/TermsofServiceRFH.html");
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Activity_TermsOfService.this, FragmentTest.class);
        startActivity(intent);
    }

   /* @Override
    public void onBackPressed()
    {
        databaseReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot adminSnapshot : dataSnapshot.getChildren()) {
                    String mail = String.valueOf(adminSnapshot.getValue());
                    AdminArray.add(mail);
                    Log.wtf("coach in value event", String.valueOf(AdminArray));
                }

                for (int i = 0; i < AdminArray.size(); i++) {
                    if (AdminArray.contains(userMailID)) {
                        Log.wtf("coach------ ", String.valueOf(AdminArray));
                        admin = true;

                    }
                }

                if (admin.equals(true)) {
                    Intent intent = new Intent(Activity_TermsOfService.this, Activity_AdminHome.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Activity_TermsOfService.this, Activity_Home.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

}