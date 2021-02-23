package com.zinftech.google;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditStudent extends AppCompatActivity {

    public String pushKey;
    private DatabaseReference mDatabase;
    Student stdObj;
    String name,college,mobileNum;
    int age;
    EditText edtName,edtAge,edtCollege,edtMobile;

    Toast toastAllFields;
    int from;
    int to;
   // Button btnAdd;
   ImageView studentImage;
    private int PICK_IMAGE_REQUEST = 234;
    private  int stdWidth = 300, stdHeight = 200; // image use for sharing
    private Uri filePath;
    private Bitmap bitmap;
    private boolean imageOkToShare;
    private String currentUserMailID;
    private  FirebaseStorage storage;
    private DatabaseReference databaseReference;
    public static String stringStroage ="gs://fir-7b578.appspot.com/image/students/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        pushKey= getIntent().getStringExtra("pushKey");

        Log.d("PUSHKEY",pushKey);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        from = ContextCompat.getColor(this, R.color.transparent);
        to   = ContextCompat.getColor(this, R.color.iceblue);


        edtName=(EditText) findViewById(R.id.editName);
        edtAge=(EditText) findViewById(R.id.editAge);
        edtCollege=(EditText) findViewById(R.id.editCollege);
        edtMobile=(EditText) findViewById(R.id.editPhonenum);
        //btnAdd=(Button) findViewById(R.id.addButton);
        studentImage= (ImageView) findViewById(R.id.img_studentImage);

        studentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImagePressed();
            }
        });


        storage = FirebaseStorage.getInstance();
        studentImage.destroyDrawingCache();
        studentImage.setImageBitmap(null);

        StorageReference gsReference = storage.getReferenceFromUrl(stringStroage+"/"+pushKey+"/"+"pic.jpg");



        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                //do your stuff- uri.toString() will give you download URL\\
            }
        });

        // Load the image using Glide
        Glide.with(EditStudent.this)
                .using(new FirebaseImageLoader())
                .load(gsReference)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // <= ADDED
                .skipMemoryCache(true) // <= ADDED
                .into(studentImage);





        mDatabase.child("Students").child(pushKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                name= (String) snapshot.child("name").getValue();
                age= Integer.parseInt(snapshot.child("age").getValue().toString());

                college = (String) snapshot.child("college").getValue();
                mobileNum = (String) snapshot.child("mobileNum").getValue();


                edtName.setText(name);
                edtAge.setText(""+age);
                edtCollege.setText(college);
                edtMobile.setText(mobileNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        toastAllFields = Toast.makeText(context, "please fill all fields", duration);


    }

    private void ChooseImagePressed()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                studentImage.setImageBitmap(bitmap);

                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (width >= stdWidth && height >= stdHeight) {
                    imageOkToShare = true;
                } else {
                    imageOkToShare = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClicksave(final View view)
    {
        String nameAfterEdit= String.valueOf(edtName.getText());
        String collegeAfterEdit= String.valueOf(edtCollege.getText());
        String ageAfterEdit= String.valueOf(edtAge.getText());
        String mobileNumAfterEdit= String.valueOf(edtMobile.getText());

        if( (nameAfterEdit.matches("")) && (collegeAfterEdit.matches("")) && (mobileNumAfterEdit.matches("")) && ageAfterEdit.matches(""))
        {
            toastAllFields.show();
            edtName.setError("please fill name");
            edtAge.setError("please fill age");
            edtMobile.setError("please fill mobile number");
            edtCollege.setError("please fill college");
        }

        else if(nameAfterEdit.matches("") )
        {
            edtName.setError("please fill name");
        }

        else if(ageAfterEdit.matches(""))
        {
            edtAge.setError("please fill age");
        }

        else if((collegeAfterEdit.matches("")) ) {

            edtCollege.setError("please fill College");
        }

        else if((mobileNumAfterEdit.matches("")) ) {

            edtMobile.setError("please fill mobile number");
        }

        else if(filePath!=null)
        {


            final ValueAnimator anim = new ValueAnimator();
            anim.setIntValues(from, to);

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

            Log.d("PPPPPP",pushKey);



            StorageReference storageRef = storage.getReference().child("image"). child("students").child(pushKey).child("pic.jpg");

                    //.child("image"). child("students").child(pushKey).child("pic.jpg");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = storageRef.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(getApplicationContext(), "Last student image saved", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception exception)
                {
                    // progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });



           SaveDataInDataBase(nameAfterEdit,ageAfterEdit,mobileNumAfterEdit,collegeAfterEdit);
        }
        else
        {
            SaveDataInDataBase(nameAfterEdit,ageAfterEdit,mobileNumAfterEdit,collegeAfterEdit);

        }

    }

    private void SaveDataInDataBase(String nameAfterEdit, String ageAfterEdit, String mobileNumAfterEdit, String collegeAfterEdit)
    {
        Student std = new Student(nameAfterEdit, Integer.parseInt(ageAfterEdit),
                mobileNumAfterEdit, collegeAfterEdit);
        mDatabase.child("Students").child(pushKey).setValue(std);

        Toast.makeText(EditStudent.this, "Information Edited successfully",
                Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(EditStudent.this, FragmentTest.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(EditStudent.this,FragmentTest.class);
        startActivity(intent);
        finish();

    }

    public void onClickCancel(final View view)
    {
        final ValueAnimator anim = new ValueAnimator();
        anim.setIntValues(from, to);

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



        Intent intent = new Intent(EditStudent.this,FragmentTest.class);
        startActivity(intent);
    }
}