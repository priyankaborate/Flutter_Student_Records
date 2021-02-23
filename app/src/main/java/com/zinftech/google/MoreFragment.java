package com.zinftech.google;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MoreFragment extends Fragment {

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

    RelativeLayout relCall,relBusinessDetails,relShareApp,relAboutApp,relSendFeedback,relHelp;



    Context context;

    public MoreFragment(Context context)
    {
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.activity_more_option, container, false);

        FragmentTest.Fragment_Value="more_fragment";


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        relSetting = (RelativeLayout) view.findViewById(R.id.settingpDetails);

        txtSetting = (TextView) view.findViewById(R.id.txtSetting);
        txtBusinessDetails = (TextView) view.findViewById(R.id.txtBusinessDetails);
        txtShareApp = (TextView) view.findViewById(R.id.txtShareApp);
        txtSendFeedback = (TextView) view.findViewById(R.id.txtSendFeedback);
        txtHelp = (TextView) view.findViewById(R.id.txtHelp);
        txtCall = (TextView) view.findViewById(R.id.txtCall);
        txtAboutApp = (TextView) view.findViewById(R.id.txtAboutApp);



        settingIcon = (ImageButton) view.findViewById(R.id.settingIcon);
        aboutIcon = (ImageButton) view.findViewById(R.id.aboutAppIcon);
        callIcon = (ImageButton) view.findViewById(R.id.callIcon);

        googleProfile = (ImageView) view.findViewById(R.id.googleProfile);
        txtName = (TextView) view.findViewById(R.id.txtName);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Glide.with(context)
                .load(user.getPhotoUrl())
                .into(googleProfile);

        txtName.setText(user.getDisplayName());
        txtEmail.setText(user.getEmail());


        from = ContextCompat.getColor(context, R.color.transparent);
        to   = ContextCompat.getColor(context, R.color.iceblue);

        relCall = (RelativeLayout) view.findViewById(R.id.callDetails);
        relBusinessDetails = (RelativeLayout) view.findViewById(R.id.businessDetails);
        relShareApp = (RelativeLayout) view.findViewById(R.id.shareAppDetails);
        relAboutApp = (RelativeLayout) view.findViewById(R.id.aboutAppDetails);
        relSendFeedback = (RelativeLayout) view.findViewById(R.id.sendFeedbackDetails);
        relHelp = (RelativeLayout) view.findViewById(R.id.helpDetails);

        privacyPolicy = (TextView) view.findViewById(R.id.privacyPolicy);
        termsOfService = (TextView) view.findViewById(R.id.termsOfService);

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPrivacyPolicy(v);
            }
        });

        termsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTermsOfservice(v);
            }
        });


        relBusinessDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBusinessDetails(v);
            }
        });

        relShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickShareApp(v);
            }
        });

        relAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAboutApp(v);
            }
        });

        relSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendFeedback(v);
            }
        });

        relHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHelp(v);
            }
        });


        relCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCall(v);
            }
        });



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
                Toast.makeText(context,"No Action is added",Toast.LENGTH_SHORT).show();
                //settingIcon.setColorFilter(getResources().getColor(R.color.black));

            }
        });

        return view;
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
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            return;
        }
        startActivity(callIntent);
    }

    public void onClickTermsOfservice(View view)
    {
        Intent  intent = new Intent(context,Activity_TermsOfService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void onClickPrivacyPolicy(View view)
    {
        Intent  intent = new Intent(context, Activity_PrivacyPolicy.class);
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

        Intent  intent = new Intent(context, Activity_AboutUs.class);
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

        Toast.makeText(context,"No Action is added",Toast.LENGTH_SHORT).show();
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

        Toast.makeText(context,"No Action is added",Toast.LENGTH_SHORT).show();

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

        Toast.makeText(context,"No Action is added",Toast.LENGTH_SHORT).show();

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

        Toast.makeText(context,"No Action is added",Toast.LENGTH_SHORT).show();
    }


}
