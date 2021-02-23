package com.zinftech.google;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_moreOption extends AppCompatActivity {
    public androidx.appcompat.app.ActionBar actionBar;

    TextView call,privacyPolicy,aboutApp,termsOfService;
    private static final int REQUEST_PHONE_CALL= 1;

    RelativeLayout relSetting;
    TextView txtSetting,txtBusinessDetails,txtShareApp,txtSendFeedback,txtHelp,txtCall,txtAboutApp;
    ImageButton settingIcon,callIcon,aboutIcon;
    int from;
     int to;
    ImageView googleProfile;
    TextView txtName,txtEmail;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_option);

        actionBar = getSupportActionBar();
        actionBar.hide();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        relSetting = (RelativeLayout) findViewById(R.id.settingpDetails);

        txtSetting = (TextView) findViewById(R.id.txtSetting);
        txtBusinessDetails = (TextView) findViewById(R.id.txtBusinessDetails);
        txtShareApp = (TextView) findViewById(R.id.txtShareApp);
        txtSendFeedback = (TextView) findViewById(R.id.txtSendFeedback);
        txtHelp = (TextView) findViewById(R.id.txtHelp);
        txtCall = (TextView) findViewById(R.id.txtCall);
        txtAboutApp = (TextView) findViewById(R.id.txtAboutApp);


        settingIcon = (ImageButton) findViewById(R.id.settingIcon);
        aboutIcon = (ImageButton) findViewById(R.id.aboutAppIcon);
        callIcon = (ImageButton) findViewById(R.id.callIcon);

        googleProfile = (ImageView) findViewById(R.id.googleProfile);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Glide.with(this)
                .load(user.getPhotoUrl())
                .into(googleProfile);

        txtName.setText(user.getDisplayName());
        txtEmail.setText(user.getEmail());


        from = ContextCompat.getColor(this, R.color.transparent);
         to   = ContextCompat.getColor(this, R.color.iceblue);

        final int orange   = ContextCompat.getColor(this, R.color.orange);


        relSetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                final ValueAnimator anim = new ValueAnimator();
                anim.setIntValues(from, to);
                ObjectAnimator.ofObject(
                        txtSetting, // Object to animating
                        "textColor", // Property to animate
                        new ArgbEvaluator(), // Interpolation function
                        Color.BLUE, // Start color
                        Color.BLACK // End color
                ).setDuration(1200) // Duration in milliseconds
                        .start();

               // settingIcon.setColorFilter(getResources().getColor(R.color.blue));

                anim.setEvaluator(new ArgbEvaluator());
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        v.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                        anim.setIntValues( to,from);

                        anim.setEvaluator(new ArgbEvaluator());
                        v.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                    }
                });

                anim.setDuration(1000);
                anim.start();
                Toast.makeText(getApplicationContext(),"No Action is added",Toast.LENGTH_SHORT).show();
                //settingIcon.setColorFilter(getResources().getColor(R.color.black));

            }
        });




    }

    public void onClickCall(final View view)
    {

        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);
        ObjectAnimator.ofObject(
                txtCall, // Object to animating
                "textColor", // Property to animate
                new ArgbEvaluator(), // Interpolation function
                Color.BLUE, // Start color
                Color.BLACK // End color
        ).setDuration(1200) // Duration in milliseconds
                .start();

        //callIcon.setColorFilter(getResources().getColor(R.color.blue));

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                anim.setIntValues( to,from);

                anim.setEvaluator(new ArgbEvaluator());
                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(1000);
        anim.start();

        String phoneNum="121";
        Log.wtf("phoneNumber",phoneNum);

        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        if (ActivityCompat.checkSelfPermission(Activity_moreOption.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Activity_moreOption.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            return;
        }
        startActivity(callIntent);
    }

    public void onClickTermsOfservice(View view)
    {
         Intent  intent = new Intent(Activity_moreOption.this,Activity_TermsOfService.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

    }

    public void onClickPrivacyPolicy(View view)
    {
        Intent  intent = new Intent(Activity_moreOption.this, Activity_PrivacyPolicy.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void onClickAboutApp(final View view)
    {
        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);
        ObjectAnimator.ofObject(
                txtAboutApp, // Object to animating
                "textColor", // Property to animate
                new ArgbEvaluator(), // Interpolation function
                Color.BLUE, // Start color
                Color.BLACK // End color
        ).setDuration(1200) // Duration in milliseconds
                .start();

         aboutIcon.setColorFilter(getResources().getColor(R.color.blue));

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                anim.setIntValues( to,from);

                anim.setEvaluator(new ArgbEvaluator());
                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(1000);
        anim.start();

        Intent  intent = new Intent(Activity_moreOption.this, Activity_AboutUs.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    public void onClickHelp(final View view)
    {
        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);
        ObjectAnimator.ofObject(
                txtHelp, // Object to animating
                "textColor", // Property to animate
                new ArgbEvaluator(), // Interpolation function
                Color.BLUE, // Start color
                Color.BLACK // End color
        ).setDuration(1200) // Duration in milliseconds
                .start();

        // settingIcon.setColorFilter(getResources().getColor(R.color.blue));

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                anim.setIntValues( to,from);

                anim.setEvaluator(new ArgbEvaluator());
                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(1000);
        anim.start();

        Toast.makeText(getApplicationContext(),"No Action is added",Toast.LENGTH_SHORT).show();
    }

    public void onClickBusinessDetails(final View view)
    {
        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);
        ObjectAnimator.ofObject(
                txtBusinessDetails, // Object to animating
                "textColor", // Property to animate
                new ArgbEvaluator(), // Interpolation function
                Color.BLUE, // Start color
                Color.BLACK // End color
        ).setDuration(1200) // Duration in milliseconds
                .start();

        // settingIcon.setColorFilter(getResources().getColor(R.color.blue));

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                anim.setIntValues( to,from);

                anim.setEvaluator(new ArgbEvaluator());
                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(1000);
        anim.start();

        Toast.makeText(getApplicationContext(),"No Action is added",Toast.LENGTH_SHORT).show();

    }


    public void onClickShareApp(final View view)
    {
        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);
        ObjectAnimator.ofObject(
                txtShareApp, // Object to animating
                "textColor", // Property to animate
                new ArgbEvaluator(), // Interpolation function
                Color.BLUE, // Start color
                Color.BLACK // End color
        ).setDuration(1200) // Duration in milliseconds
                .start();

        // settingIcon.setColorFilter(getResources().getColor(R.color.blue));

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                anim.setIntValues( to,from);

                anim.setEvaluator(new ArgbEvaluator());
                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(1000);
        anim.start();

        Toast.makeText(getApplicationContext(),"No Action is added",Toast.LENGTH_SHORT).show();

    }

    public void onClickSendFeedback(final View view)
    {
        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);
        ObjectAnimator.ofObject(
                txtSendFeedback, // Object to animating
                "textColor", // Property to animate
                new ArgbEvaluator(), // Interpolation function
                Color.BLUE, // Start color
                Color.BLACK // End color
        ).setDuration(1200) // Duration in milliseconds
                .start();

        // settingIcon.setColorFilter(getResources().getColor(R.color.blue));

        anim.setEvaluator(new ArgbEvaluator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
                anim.setIntValues( to,from);

                anim.setEvaluator(new ArgbEvaluator());
                view.setBackgroundColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        anim.setDuration(1000);
        anim.start();

        Toast.makeText(getApplicationContext(),"No Action is added",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Activity_moreOption.this, DisplayStudents.class);
        startActivity(intent);
    }



}