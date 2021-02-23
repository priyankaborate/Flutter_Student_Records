package com.zinftech.google;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class testActivity extends AppCompatActivity {

    ImageView arrow_for_phone,arrow_for_address;
    RelativeLayout phone_hiddenView,address_hiddenview;
    CardView phone_cardView,addrress_cardview;
    int rotationAngleForPhone = 0,rotationAngleForAddress=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        phone_cardView = findViewById(R.id.phone_cardview);
        arrow_for_phone = findViewById(R.id.arrow_for_phone);
        phone_hiddenView = findViewById(R.id.hidden_phone_view);


        addrress_cardview = findViewById(R.id.address_cardview);
        arrow_for_address = findViewById(R.id.arrow_for_address);
        address_hiddenview = findViewById(R.id.hidden_address_view);




        checkVisibilityForPhone();
        checkVisibilityForAddress();

    }

    private void checkVisibilityForAddress()
    {
        arrow_for_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (address_hiddenview.getVisibility() == View.VISIBLE)
                {
                    address_hiddenview.setVisibility(View.GONE);

                    TransitionManager.beginDelayedTransition(addrress_cardview,
                            new AutoTransition());

                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",rotationAngleForAddress, rotationAngleForAddress - 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngleForAddress -= 180;
                    rotationAngleForAddress = rotationAngleForAddress%360;

                }

                else
                {
                    TransitionManager.beginDelayedTransition(addrress_cardview,
                            new AutoTransition());
                    address_hiddenview.setVisibility(View.VISIBLE);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",rotationAngleForAddress, rotationAngleForAddress + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngleForAddress += 180;
                    rotationAngleForAddress = rotationAngleForAddress%360;
                }
            }
        });
    }

    private void checkVisibilityForPhone() {
        arrow_for_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone_hiddenView.getVisibility() == View.VISIBLE)
                {
                    phone_hiddenView.setVisibility(View.GONE);

                    TransitionManager.beginDelayedTransition(phone_cardView,
                            new AutoTransition());

                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",rotationAngleForPhone, rotationAngleForPhone - 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngleForPhone -= 180;
                    rotationAngleForPhone = rotationAngleForPhone%360;

                }

                else
                {
                    TransitionManager.beginDelayedTransition(phone_cardView,
                            new AutoTransition());
                    phone_hiddenView.setVisibility(View.VISIBLE);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation",rotationAngleForPhone, rotationAngleForPhone + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngleForPhone += 180;
                    rotationAngleForPhone = rotationAngleForPhone%360;
                }
            }
        });
    }
}