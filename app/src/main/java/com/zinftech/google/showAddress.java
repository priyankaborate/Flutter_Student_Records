package com.zinftech.google;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class showAddress extends AppCompatActivity {


    ImageView arrow_for_phone,arrow_for_address;
    RelativeLayout phone_hiddenView,address_hiddenview;
    CardView phone_cardView,addrress_cardview;
    int rotationAngleForPhone = 0,rotationAngleForAddress=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address);


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

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(showAddress.this,DisplayStudents.class);
        startActivity(intent);
        finish();
    }

   

}
















 /*   TextView txtAddress,txtAddress1,txtAddress2,txtAddress3,txtPhoneNum;
    ImageView iconAdress,iconArrow,iconArrowforPhone;
    boolean checkForAddress =false,checkForPhone=false;
    int rotationAngle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address);

        txtAddress=(TextView) findViewById(R.id.textAddress);
        txtAddress1=(TextView) findViewById(R.id.textAddress1);
        txtAddress2=(TextView) findViewById(R.id.textAddress2);
        txtAddress3=(TextView) findViewById(R.id.textAddress3);

        txtPhoneNum=(TextView) findViewById(R.id.textPhoneNum);


        iconAdress=(ImageView) findViewById(R.id.location_icon);
        iconArrow=(ImageView) findViewById(R.id.downArrow);

        iconArrowforPhone=(ImageView) findViewById(R.id.downArrow1);


        txtAddress.setText("Baramati  ");
        txtAddress1.setText("junction");

       checkVisibilityForAddress();

       checkVisibilityForPhone();


    }

    void checkVisibilityForAddress()
    {
        if(checkForAddress==false)
        {
            inVisibleAddress();
        }

        else if(checkForAddress==true)
        {
            visibleAddress();
        }
    }

    void visibleAddress()
    {
        iconArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK when VISIBLE ","CLICK");

                checkForAddress=false;

                txtAddress2.setVisibility(View.GONE);
                txtAddress3.setVisibility(View.GONE);

                ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation",rotationAngle, rotationAngle - 180);
                anim.setDuration(500);
                anim.start();
                rotationAngle -= 180;
                rotationAngle = rotationAngle%360;

                //iconArrow.setImageResource(R.drawable.arrow_down);
                checkVisibilityForAddress();


            }
        });


    }

    private void checkVisibilityForPhone()
    {
    if(checkForPhone==false)
    {
        inVisiblePhone();
    }

        else if(checkForPhone==true)
    {
        visiblePhone();
    }

    }

    private void visiblePhone()
    {
        iconArrowforPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK when VISIBLE ","CLICK");

                checkForPhone=false;

                txtPhoneNum.setVisibility(View.GONE);

                ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation",rotationAngle, rotationAngle - 180);
                anim.setDuration(500);
                anim.start();
                rotationAngle -= 180;
                rotationAngle = rotationAngle%360;

                checkVisibilityForPhone();

            }
        });
    }

    private void inVisiblePhone() {

        iconArrowforPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkForPhone=true;
                Log.d("VISIBLE nahi","VISIBLE");

                txtPhoneNum.setVisibility(View.VISIBLE);

                ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation",rotationAngle, rotationAngle + 180);
                anim.setDuration(500);
                anim.start();
                rotationAngle += 180;
                rotationAngle = rotationAngle%360;


                //iconArrow.setImageResource(R.drawable.up_arrow);

                txtPhoneNum.setText("7028456506");

                checkVisibilityForPhone();
            }

        });


    }

    void inVisibleAddress()
    {
        iconArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkForAddress=true;
                Log.d("VISIBLE nahi","VISIBLE");

                txtAddress2.setVisibility(View.VISIBLE);
                txtAddress3.setVisibility(View.VISIBLE);


                ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation",rotationAngle, rotationAngle + 180);
                anim.setDuration(500);
                anim.start();
                rotationAngle += 180;
                rotationAngle = rotationAngle%360;


                //iconArrow.setImageResource(R.drawable.up_arrow);

                txtAddress2.setText("BorateWasti,near Rohit mangal karyalaya");
                txtAddress3.setText("Maharashtra,413114");

                checkVisibilityForAddress();
            }

        });


    }


    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(showAddress.this,DisplayStudents.class);
        startActivity(intent);
        finish();
    }

    public void onClickText(View view) {

        Intent intent = new Intent(showAddress.this,testActivity.class);
        startActivity(intent);

    }
}*/