package com.zinftech.google;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class FragmentTest extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    Fragment fragment;

    public static String Fragment_Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);

        Fragment_Value="discover";

        Log.d("FRAGMENT COUNT",Fragment_Value);

        //loading the default fragment


        loadFragment(new DiscoverFragment(FragmentTest.this));

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

       /* FragmentManager fragmentManager;
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                List<Fragment> f = fragmentManager.getFragments();
                Fragment frag = f.get(0);
                currentFragment = frag.getClass().getSimpleName();
            }
        });*/

      /*  FragmentTransaction mFragmentTransaction = getFragmentManager()
                .beginTransaction();

        mFragmentTransaction.addToBackStack(null);*/




    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {




            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         fragment = null;

        switch (item.getItemId()) {
            case R.id.action_discover:
                fragment = new DiscoverFragment(FragmentTest.this);
                break;

            case R.id.action_add:
                fragment = new AddStudentFragment(FragmentTest.this);
                break;

            case R.id.action_Collections:
                fragment = new Addrressfragment(FragmentTest.this);
                break;

            case R.id.action_more:
                fragment = new MoreFragment(FragmentTest.this);
                break;
        }

        return loadFragment(fragment);
    }


    /*@Override
    public void onBackPressed()
    {
        if(!fragment.equals(R.id.action_discover))
        {
            fragment = new DiscoverFragment(FragmentTest.this);
            loadFragment(fragment);
        }
        else
            {
              Log.d("SUPER","SUPER");
            // handle by activity
            super.onBackPressed();
        }
    }*/

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        Log.d("FRAGMENT COUNT", String.valueOf(count));

        Log.d("FRAGMENT VALUE",Fragment_Value);

        if(Fragment_Value.matches("discover")) {
            //Back key pressed on fragment one

            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure,you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface arg0, int arg1)
                        {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            System.exit(0);
                            finish();
                        }
                    }).create().show();

        }else {
           // fragment = new DiscoverFragment(FragmentTest.this);
            //Fragment_Value="discover";
            //loadFragment(fragment);


            fragment = new DiscoverFragment(FragmentTest.this);

            loadFragment(fragment);





            //getSupportFragmentManager().popBackStack();

          /*  Intent intent = new Intent(FragmentTest.this,FragmentTest.class);
            startActivity(intent);

            getSupportFragmentManager().popBackStack();*/
            //Back key pressed on fragment two
        }
    }


   /* @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            finish();
        } else {
            Log.d("ELSE","ELSE");
            getSupportFragmentManager().popBackStack();

        }
    }*/

   /* @Override
    public void onBackPressed() {

        Log.d("BACKPRSSED","BACKPRESSED");

        if (getFragmentManager().getBackStackEntryCount() == 0) {
            Log.d("FINISH","FINISH");
            Log.d("COUNT", String.valueOf(getFragmentManager().getBackStackEntryCount()));


            Intent intent = new Intent(FragmentTest.this,FragmentTest.class);
            startActivity(intent);


            getFragmentManager().popBackStack();
            //this.finish();
        } else {
            Log.d("POPBACK","POPBACK");

            getFragmentManager().popBackStack();
        }
    }
*/

 /*   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            {
                removeCurrentFragment();
                //this.finish();
                return true;
            }
            else
            {
                getSupportFragmentManager().popBackStack();
                removeCurrentFragment();

                return false;
            }



        }

        return super.onKeyDown(keyCode, event);
    }*/


   /* public void removeCurrentFragment()
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment currentFrag =  getSupportFragmentManager().findFragmentById(R.id.action_discover);
    }*/

}