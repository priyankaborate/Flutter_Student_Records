package com.zinftech.google;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DiscoverFragment extends Fragment {

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

    Context context;


    public DiscoverFragment(Context context)
    {
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view;

        view = LayoutInflater.from(context).inflate(R.layout.activity_display_students, container, false);

        FragmentTest.Fragment_Value="discover";


        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = mAuth.getCurrentUser();

        Log.d("EMAIL ID",user.getEmail());


        stdArrayList=new ArrayList<Student>();
        pushKeyList=new ArrayList<String>();

        simpleList = (ListView) view.findViewById(R.id.simpleListView);

        adapter = new StudentListAdapter(context ,stdWithPKArrayList);
        simpleList.setAdapter(adapter);

        studentObj=new Student();



        displayList();

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent =new Intent(context,StudentDetails.class);
                intent.putExtra("pushKey",pushKeyList.get(position));
                startActivity(intent);

            }
        });

        return view;
    }


    private void displayList() {

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

                adapter = new StudentListAdapter(context, stdWithPKArrayList);

                simpleList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
