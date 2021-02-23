package com.zinftech.google;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.LauncherActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class DisplayStudents extends AppCompatActivity implements Serializable{

    public ListView simpleList;

    public Student studentObj;

    public ArrayList<Student> stdArrayList;

    private DatabaseReference mDatabase;

    public String type,strPushKey;

    public ArrayList<String> pushKeyList;


    public StudentListAdapter adapter;

    public androidx.appcompat.app.ActionBar actionBar;

    BottomNavigationView bottomNavigationView;

    ArrayList<StdWithPK> stdWithPKArrayList=new ArrayList<>();
    StdWithPK stdwithpkObject;

    private static final int REQUEST_PHONE_CALL= 1;
    FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_students);

       // BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        actionBar = getSupportActionBar();

        actionBar.hide();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = mAuth.getCurrentUser();

        Log.d("EMAIL ID",user.getEmail());


        stdArrayList=new ArrayList<Student>();
        pushKeyList=new ArrayList<String>();

        simpleList = (ListView) findViewById(R.id.simpleListView);

        adapter = new StudentListAdapter(DisplayStudents.this ,stdWithPKArrayList);
        simpleList.setAdapter(adapter);

        studentObj=new Student();




/*

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.action_Collections:

                        Intent intent=new Intent(DisplayStudents.this, showAddress.class);
                        startActivity(intent);
                        break;

                    case R.id.action_add:

                        Intent intent2=new Intent(DisplayStudents.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.action_more:
                        //showOptions();

                        Intent intent3=new Intent(DisplayStudents.this, Activity_moreOption.class);
                        startActivity(intent3);

                        break;
                }
                return true;
            }
        });
*/


        displayList();

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =new Intent(DisplayStudents.this,StudentDetails.class);
                intent.putExtra("pushKey",pushKeyList.get(position));
                startActivity(intent);

            }
        });

    }

    private void showOptions() {

        Rect displayRectangle = new Rect();
        Window window = DisplayStudents.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog.Builder builder = new AlertDialog.Builder(DisplayStudents.this,R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(DisplayStudents.this.getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
        dialogView.setMinimumWidth((int)(displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int)(displayRectangle.height() * 1f));
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        //Button buttonOk=dialogView.findViewById(R.id.buttonOk);
       /* buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
*/

        alertDialog.show();
      /*  View menuItemView = findViewById(R.id.action_more);
        PopupMenu popup = new PopupMenu(DisplayStudents.this, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.optionsmenu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                   *//* case R.id.menu_businessDetails:
                        BusinessDetailsMenuSelected();
                        return true;

                    case R.id.menu_share:
                        ShareMenuSelected();
                        return true;*//*

                    case R.id.menu_calltobusiness:
                        Call();
                        return  true;


                    case R.id.menu_aboutapp:
                        AboutUs();
                        return true;

                    case R.id.menu_privacypolicy:
                        privacyPolicy();
                        return true;

                    case R.id.menu_termsofservice:
                        termsOfService();
                        return true;

                }
                return true;
            }
        });
        popup.show();
     */

    }

    private void privacyPolicy()
    {
        Intent  intent = new Intent(DisplayStudents.this, Activity_PrivacyPolicy.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void AboutUs()
    {
        Intent  intent = new Intent(DisplayStudents.this, Activity_AboutUs.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void Call()
    {

           /* myRef.child("Business").child("BusinessDetails").addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {*/
                    //phoneNumber = (String.valueOf( dataSnapshot.child("Phone").getValue()));
                    String phoneNum="121";
                    Log.wtf("phoneNumber",phoneNum);

                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
                    if (ActivityCompat.checkSelfPermission(DisplayStudents.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(DisplayStudents.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                        return;
                    }
                    startActivity(callIntent);
                /*}

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });*/





    }

    private void termsOfService()
    {
        Intent  intent = new Intent(DisplayStudents.this,Activity_TermsOfService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void displayList() {

       // stdArrayList.clear();

        mDatabase.child("Students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot postSnapshot: snapshot.getChildren())
                {
                    String name= (String) postSnapshot.child("name").getValue();
                    int age= Integer.parseInt(postSnapshot.child("age").getValue().toString());

                    strPushKey=postSnapshot.getKey();
                    pushKeyList.add(strPushKey);

                    String college= (String) postSnapshot.child("college").getValue();
                    String mobileNum= (String) postSnapshot.child("mobileNum").getValue();

                    if(age>25)
                    {
                        type="smallImage";
                    }
                    else
                    {
                        type="bigImage";
                    }

                    studentObj=new Student(name,age,mobileNum,college,type);


                    stdwithpkObject= new StdWithPK(strPushKey,studentObj);
                    stdWithPKArrayList.add(stdwithpkObject);

                    Log.d("NAME : ",name);


                    stdArrayList.add(studentObj);

                }

                adapter = new StudentListAdapter(DisplayStudents.this, stdWithPKArrayList);

                simpleList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       /* Log.d("SSSSSS11","SSSSSS");

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("SSSSSS","SSSSSS");

                Toast.makeText(DisplayStudents.this, "Selected !"+stdWithPKArrayList.get(position).Std.Name, Toast.LENGTH_LONG).show();


                Intent intent =new Intent(DisplayStudents.this,StudentDetails.class);
                intent.putExtra("pushKey",pushKeyList.get(position));
                startActivity(intent);

            }
        });*/


    }

    @Override
    public void onBackPressed()
    {
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
    }



}
