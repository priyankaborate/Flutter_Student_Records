package com.zinftech.google;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    String str="FirstActivity";

    DatabaseReference databaseReference;

    final ArrayList<Student> arrayStudent=new ArrayList<Student>();

    //Button btnDisplay;
    //Button btnAdd;
    EditText editName,editAge,editCollege,editPhoneNum;
    Toast toastAllFields;

    int from;
    int to;

    ImageView StudentImage;
    //Button btnImage;

    private int PICK_IMAGE_REQUEST = 234;
    private  int stdWidth = 300, stdHeight = 200; // image use for sharing
    private Uri filePath;
    private Bitmap bitmap;
    private boolean imageOkToShare;
    private String currentUserMailID;
    private StorageReference storage;
    public String folderName,pkString;





    public ArrayList<Student> getList(){
        return  arrayStudent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage= FirebaseStorage.getInstance().getReference();

        String str="priya";

        from = ContextCompat.getColor(this, R.color.transparent);
        to   = ContextCompat.getColor(this, R.color.iceblue);


        editName=(EditText) findViewById(R.id.editName);
        editCollege=(EditText) findViewById(R.id.editCollege);
        editAge=(EditText) findViewById(R.id.editAge);
        editPhoneNum=(EditText) findViewById(R.id.editPhonenum);


        //btnAdd=(Button) findViewById(R.id.addButton);

        //btnImage = (Button) findViewById(R.id.btn_image);
        StudentImage =(ImageView) findViewById(R.id.img_studentImage);

        StudentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        //btnCancel=(ImageButton) findViewById(R.id.cancelButton);

        //btnDisplay=(Button) findViewById(R.id.btnDisplay);


     /*   btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name= editName.getText().toString();
                String age=editAge.getText().toString();
                String college=editCollege.getText().toString();
                String phoneNum=editPhoneNum.getText().toString();

                Intent intent=new Intent(MainActivity.this,DisplayStudents.class);

                startActivity(intent);
            }
        });*/


        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        toastAllFields = Toast.makeText(context, "please fill all fields", duration);





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                StudentImage.setImageBitmap(bitmap);

                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (width>=stdWidth && height>=stdHeight)
                {
                    imageOkToShare = true;
                } else
                {
                    imageOkToShare = false;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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


        Intent intent = new Intent(MainActivity.this,DisplayStudents.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(MainActivity.this,DisplayStudents.class);
        startActivity(intent);
        finish();

    }

    public void onClicksave(final View view)
    {
        String studentPushKey;

        String name= editName.getText().toString();
        String age= editAge.getText().toString();
        //String age2=String.valueOf(age);
        String college=editCollege.getText().toString();
        String phoneMun=editPhoneNum.getText().toString();


        if(name.matches("") && age.matches("") && phoneMun.matches("") && college.matches("") )
        {
            toastAllFields.show();
            editName.setError("please fill name");
            editCollege.setError("please fill college");
            editAge.setError("please fill age");
            editPhoneNum.setError("please fill mobile number");

        }

        else if(name.matches("") )
        {
            editName.setError("please fill name");
        }

        else if( college.matches(""))
        {
            editCollege.setError("please fill college");
        }

        else if( age.matches("") )
        {
            editAge.setError("please fill age");

        }
        else if( phoneMun.matches("") )
        {
            editPhoneNum.setError("please fill mobile number");

        }

        else if(phoneMun.length()<10 || phoneMun.length()>10)
        {
            Toast.makeText(MainActivity.this, "Mobile Number should 10 digits",
                    Toast.LENGTH_SHORT).show();

        }

        else if(filePath==null)
        {
            Toast.makeText(MainActivity.this, "please select student photo",
                    Toast.LENGTH_SHORT).show();
        }



        else  {


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





            Student std;

            std = new Student(name, Integer.parseInt(age), phoneMun, college);

            studentPushKey=databaseReference.push().getKey();



            StorageReference riversRef = storage.child("image"). child("students").child(studentPushKey).child("pic.jpg");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = riversRef.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(getApplicationContext(), "student image saved", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception exception)
                { //progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });





            databaseReference.child("Students").child(studentPushKey).setValue(std);

            arrayStudent.add(std);
            editName.setText(null);
            editAge.setText(null);
            editCollege.setText(null);
            editPhoneNum.setText(null);

            Toast toast = Toast.makeText(MainActivity.this, "Record Added Successfully", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(MainActivity.this, DisplayStudents.class);
            startActivity(intent);

        }
    }
}