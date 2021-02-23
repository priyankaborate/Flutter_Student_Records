package com.zinftech.google;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;

public class AddStudentFragment extends Fragment {

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
    Context context;
    ImageButton cancel,save;

    public AddStudentFragment(Context context)
    {
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view;

        view = LayoutInflater.from(context).inflate(R.layout.activity_main, container, false);

        FragmentTest.Fragment_Value="add_fragment";

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage= FirebaseStorage.getInstance().getReference();

        String str="priya";

        from = ContextCompat.getColor(context, R.color.transparent);
        to   = ContextCompat.getColor(context, R.color.iceblue);


        editName=(EditText) view.findViewById(R.id.editName);
        editCollege=(EditText) view.findViewById(R.id.editCollege);
        editAge=(EditText) view.findViewById(R.id.editAge);
        editPhoneNum=(EditText) view.findViewById(R.id.editPhonenum);


        cancel = (ImageButton) view.findViewById(R.id.cancelButton);
        save = (ImageButton) view.findViewById(R.id.addButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
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
                    Toast.makeText(context, "Mobile Number should 10 digits",
                            Toast.LENGTH_SHORT).show();

                }

                else if(filePath==null)
                {
                    Toast.makeText(context, "please select student photo",
                            Toast.LENGTH_SHORT).show();
                }

                else  {
                    final ValueAnimator anim = new ValueAnimator();
                    anim.setIntValues(from, to);

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
                            Toast.makeText(context, "student image saved", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener()
                    {
                        @Override
                        public void onFailure(@NonNull Exception exception)
                        { //progressDialog.dismiss();
                            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                    databaseReference.child("Students").child(studentPushKey).setValue(std);

                    arrayStudent.add(std);
                    editName.setText(null);
                    editAge.setText(null);
                    editCollege.setText(null);
                    editPhoneNum.setText(null);

                    Toast toast = Toast.makeText(context, "Record Added Successfully", Toast.LENGTH_SHORT);
                    toast.show();


                    Intent intent = new Intent(context, FragmentTest.class);
                    startActivity(intent);

                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ValueAnimator anim = new ValueAnimator();
                anim.setIntValues(from, to);

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

                Intent intent = new Intent(context, FragmentTest.class);
                startActivity(intent);

            }
        });

        //btnAdd=(Button) findViewById(R.id.addButton);

        //btnImage = (Button) findViewById(R.id.btn_image);
        StudentImage =(ImageView) view.findViewById(R.id.img_studentImage);

        StudentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        int duration = Toast.LENGTH_SHORT;

        toastAllFields = Toast.makeText(context, "please fill all fields", duration);


      /*  requireActivity().getOnBackPressedDispatcher().addCallback((LifecycleOwner) context,
                new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(context,FragmentTest.class);
                startActivity(intent);
            }
        });
*/


        return view;
    }


@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),filePath);
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


   /* public void onBackPressed() {
        // note: you can also use 'getSupportFragmentManager()'
        FragmentManager mgr = getActivity().getSupportFragmentManager();
        if (mgr.getBackStackEntryCount() == 1) {
            // No backstack to pop, so calling super

        } else {
            mgr.popBackStack();
        }
    }
*/
  /*  @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    DiscoverFragment mainHomeFragment = new DiscoverFragment(context);
                    FragmentTransaction fragmentTransaction =
                            getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.action_discover, mainHomeFragment);
                    fragmentTransaction.commit();

                    return true;
                }
                return false;
            }
        }); }
*/

}
