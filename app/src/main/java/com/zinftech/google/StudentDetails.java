package com.zinftech.google;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class StudentDetails extends AppCompatActivity {

    public String pushKey;

    TextView txtName,txtAge,txtCollege,txtMobile;
    private DatabaseReference mDatabase;

    public String name,college,mobileNum;

    int age;

    ImageView studImage;

    StorageReference filePath;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public static String stringStroage ="gs://fir-7b578.appspot.com/image/students/";

    MenuItem deleteMenu,editMenu;
    private Boolean admin=false;
    private ArrayList<String> AdminArray;
    String userMailID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        ActionBar actionBar=getSupportActionBar();



        pushKey= getIntent().getStringExtra("pushKey");

        Log.d("PUSHKEY",pushKey);

        txtName=findViewById(R.id.studentName);
        txtAge=findViewById(R.id.studentAge);
        txtCollege=findViewById(R.id.studentCollege);
        txtMobile=findViewById(R.id.studentPhoneNum);

        studImage = findViewById(R.id.studentImage);

        filePath= storageRef.child("/image/students/"+pushKey+"/pic.jpg");

        Glide.with(StudentDetails.this)
                .using(new FirebaseImageLoader())
                .load(filePath)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // <= ADDED
                .skipMemoryCache(true) // <= ADDED
                .into(studImage);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userMailID = currentFirebaseUser.getEmail();
        AdminArray = new ArrayList<String>();

        mDatabase.child("Students").child(pushKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name= (String) snapshot.child("name").getValue();
                age= Integer.parseInt(snapshot.child("age").getValue().toString());

                college = (String) snapshot.child("college").getValue();
                mobileNum = (String) snapshot.child("mobileNum").getValue();


                txtName.setText(name);
                txtAge.setText(String.valueOf(age));
                txtCollege.setText(college);
                txtMobile.setText(mobileNum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void onDeleteClick()
    {
            new AlertDialog.Builder(this)
                    .setTitle("Really Delete ?")
                    .setMessage("Are you sure you want to Delete this Student?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {


                            //with swipe
                            mDatabase.child("Students").child(pushKey).removeValue();


                     /*       final StorageReference desertRef = storage.child("image/" + "business/" + offerPushKey + "/" + "pic.jpg");
                            desertRef.delete();*/

                            final StorageReference deleteRef = storage.getReference().child("image/"+"students/"+pushKey+"/"+"pic.jpg");

                            deleteRef.delete();

                            Toast.makeText(StudentDetails.this, "Student Deleted Sucessfully !", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(StudentDetails.this, FragmentTest.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                    }).create().show();

    }

    public void onEditClick()
    {
        Intent intent = new Intent(StudentDetails.this, EditStudent.class);
        intent.putExtra("pushKey", pushKey);
        startActivity(intent);
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.view_student,menu);

        deleteMenu=(MenuItem) menu.findItem(R.id.menu_delete_Product);
        editMenu=(MenuItem) menu.findItem(R.id.menu_edit_Product);


        mDatabase.child("Admin").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot adminSnapshot :dataSnapshot.getChildren())
                {
                    String mail = String.valueOf(adminSnapshot.getValue());
                    AdminArray.add(mail);
                    Log.wtf("coach in value event", String.valueOf(AdminArray));
                }

                for(int i=0;i<AdminArray.size();i++)
                {
                    if(AdminArray.contains(userMailID))
                    {
                        Log.wtf("coach------ ", String.valueOf(AdminArray));
                        admin=true;

                    }
                }

                if(admin.equals(true))
                {
                    editMenu.setVisible(true);
                    deleteMenu.setVisible(true);
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });


        invalidateOptionsMenu();

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_delete_Product:
                onDeleteClick();
                return true;

            case R.id.menu_edit_Product:
                onEditClick();
                return true;
        }
        return true;
    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(StudentDetails.this, FragmentTest.class);
        startActivity(intent);
    }

}