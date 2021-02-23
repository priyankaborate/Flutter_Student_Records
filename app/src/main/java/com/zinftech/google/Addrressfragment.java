package com.zinftech.google;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

public class Addrressfragment extends Fragment
{
    ImageView arrow_for_phone,arrow_for_address;
    RelativeLayout phone_hiddenView,address_hiddenview;
    CardView phone_cardView,addrress_cardview;
    int rotationAngleForPhone = 0,rotationAngleForAddress=0;
    Context context;

    public Addrressfragment(Context context)
    {
            this.context = context;
    }


    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //return inflater.inflate(R.layout.activity_show_address, null);

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.activity_show_address, container, false);

        FragmentTest.Fragment_Value="address_fragment";


        phone_cardView = view.findViewById(R.id.phone_cardview);
        arrow_for_phone = view.findViewById(R.id.arrow_for_phone);
        phone_hiddenView = view.findViewById(R.id.hidden_phone_view);


        addrress_cardview = view.findViewById(R.id.address_cardview);
        arrow_for_address = view.findViewById(R.id.arrow_for_address);
        address_hiddenview = view.findViewById(R.id.hidden_address_view);




        checkVisibilityForPhone();
        checkVisibilityForAddress();


        return view;
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
