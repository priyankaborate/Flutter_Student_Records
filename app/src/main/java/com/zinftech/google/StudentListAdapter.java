package com.zinftech.google;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import static androidx.core.content.ContextCompat.startActivity;


public class StudentListAdapter extends BaseAdapter  {

    Context context;
    public ArrayList<StdWithPK> students;
    StorageReference filePath;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    public static String stringStroage ="gs://fir-7b578.appspot.com/image/students/";
    public Uri imageUrl;
    private String encodedUrl;
    private StorageReference storRef = storage.getReference();

    int from;
    int to;

    private Boolean admin=false;
    private ArrayList<String> AdminArray;
    String userMailID;
    MenuItem deleteMenu,editMenu;
    FirebaseUser currentFirebaseUser;
    //gs://fir-7b578.appspot.com/image/students/-MHBDMlOomICsKAFwctl/pic.jpg


    public StudentListAdapter(Context context,  ArrayList<StdWithPK> students) {
        this.context = context;
        this.students  = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.indexOf(getItem(position));
    }


    private class ViewHolder
    {
        TextView textName;
        TextView textAge;
        TextView textMobileNum;
        TextView textCollege;
        ImageView image;
        ListView simpleList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        if(students.get(position).Std.Type.equals("bigImage")) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview, parent, false);
        }
        else
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.listviewforsmallimage, parent, false);
        }

        from = ContextCompat.getColor(context, R.color.transparent);
        to   = ContextCompat.getColor(context, R.color.iceblue);


         currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userMailID = currentFirebaseUser.getEmail();
        AdminArray = new ArrayList<String>();


        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        TextView textAge = (TextView) convertView.findViewById(R.id.textAge);
        TextView textMobileNum = (TextView) convertView.findViewById(R.id.textMobileNum);
        TextView textCollege = (TextView) convertView.findViewById(R.id.textCollege);
            ImageView image=(ImageView) convertView.findViewById(R.id.image);
            ListView simpleList=(ListView) convertView.findViewById(R.id.simpleListView);

            ImageButton imageDots = (ImageButton) convertView.findViewById(R.id.imgDots);
            ImageButton imageShare = (ImageButton) convertView.findViewById(R.id.imgShare);


        image.destroyDrawingCache();
            image.setImageBitmap(null);

            final Student std_pos=students.get(position).Std;

           /* if(students.get(position).Std.College.equals("vp"))
            {
                filePath= storageRef.child("/Vp/ABC.jpg");
            }
            else if(students.get(position).Std.College.equals("tc"))
            {
                filePath= storageRef.child("/Tc/BBB.jpg");

            }
            else if(students.get(position).Std.College.equals("modern"))
            {
                filePath= storageRef.child("/Modern/DEF.jpg");

            }
            else if(students.get(position).Std.College.equals("garware"))
            {
                filePath= storageRef.child("/Garware/PQR.jpg");

            }
            else if(students.get(position).Std.College.equals("fc"))
            {
                filePath= storageRef.child("/Fc/JPG BABA 4.8.2020jpg.jpg");

            }
            else if(students.get(position).Std.College.equals("indira"))
            {
                filePath= storageRef.child("/Indira/dp.jpg");

            }
            else
            {
                filePath= storageRef.child("/images/greenwall.jpg");

            }
*/

        Log.d("PUSHKEY",students.get(position).Pk);

        filePath= storageRef.child("/image/students/"+students.get(position).Pk+"/pic.jpg");

        final String studPushKey=students.get(position).Pk;

        StorageReference gsReference = storage.getReferenceFromUrl(stringStroage + "/" + studPushKey + "/pic.jpg");

        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(gsReference)
                .diskCacheStrategy(DiskCacheStrategy.NONE) // <= ADDED
                .skipMemoryCache(true) // <= ADDED
                .into(image);


            textName.setText(std_pos.Name );

            Log.d("NAME222222 : ",std_pos.Name);

            textAge.setText(String.valueOf(std_pos.Age));
            textMobileNum.setText(std_pos.MobileNum);
            textCollege.setText(std_pos.College);


            imageShare.setOnClickListener(new View.OnClickListener() {
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



                    storRef.child("/image/students/"+studPushKey+"/"+"pic.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            imageUrl = uri;
                            try
                            {
                                encodedUrl =  URLEncoder.encode(String.valueOf(imageUrl), "UTF-8");

                                final String longUrl ="https://googlestudent.page.link/?link=https://myapp.com/welcome&apn=com.zinftech.google"
                                        +"&apn=com.zinftech.google"
                                        +"&st="+"Student Name "+" - "+std_pos.Name+" - By"+"Student"
                                        +"&sd="+"Description"
                                        +"&si="+encodedUrl;

                                Log.wtf("Long uri",longUrl);

                                FirebaseDynamicLinks.getInstance().createDynamicLink()
                                        .setLongLink(Uri.parse(longUrl))
                                        .buildShortDynamicLink()
                                        .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                                            @Override
                                            public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                                String shortLink;
                                                if (task.isSuccessful()) {
                                                    // Short link created
                                                    shortLink = String.valueOf(task.getResult().getShortLink());
                                                    Log.wtf("shortLink", shortLink);

                                                    Intent intent = new Intent();
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    String msg = String.valueOf("Student Description"+"\n"+shortLink);
                                                    intent.setAction(Intent.ACTION_SEND);
                                                    intent.putExtra(Intent.EXTRA_TEXT, msg);
                                                    intent.setType("text/plain");
                                                    // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    context.startActivity(intent);


                                                } else {
                                                }
                                            }
                                        });
                            }
                            catch (UnsupportedEncodingException e)
                            {

                                String error = String.valueOf(e.getMessage()) ;
                                Log.d("Exception: ", error);
                            }
                        }
                    });


                }
            });


            imageDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //PopUpClass popUpClass = new PopUpClass();
                    //popUpClass.showPopupWindow(v);
                    showPopup(v,position);
                }
            });



        //final Student stud=students.get(position).Std;
        return convertView;
    }


    private void showPopup(View view, final int position) {
        // pass the imageview id
        final View menuItemView = view.findViewById(R.id.imgDots);
        PopupMenu popup = new PopupMenu(context, menuItemView);
        MenuInflater inflate = popup.getMenuInflater();
        //inflate.inflate(R.menu.delete_edit_menu, popup.getMenu());
        Log.d("position -- ", String.valueOf(position));


        inflate.inflate(R.menu.delete_edit_menu,popup.getMenu());



        deleteMenu=(MenuItem) popup.getMenu().findItem(R.id.delete);
        editMenu=(MenuItem) popup.getMenu().findItem(R.id.edit);

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



        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.details:
                        Intent intentDetails = new Intent(context,StudentDetails.class);
                        intentDetails.putExtra("pushKey", students.get(position).Pk);
                        context.startActivity(intentDetails);
                        break;
                    case R.id.edit:
                        Intent intent = new Intent(context,EditStudent.class);
                        intent.putExtra("pushKey", students.get(position).Pk);
                       context.startActivity(intent);
                        break;
                    case R.id.delete:
                        onDeleteClick(position);
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });
        popup.show();
    }

    private void onDeleteClick(final int position)
    {
        new AlertDialog.Builder(context)
                .setTitle("Really Delete ?")
                .setMessage("Are you sure you want to Delete this Student?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {


                        //with swipe
                        mDatabase.child("Students").child(students.get(position).Pk).removeValue();

                        final StorageReference deleteRef = storage.getReference().child("image/"+"students/"+students.get(position).Pk+"/"+"pic.jpg");

                        deleteRef.delete();
                                //child("image/"+"business/"+offerPushKey+"/"+"pic.jpg");
                       // desertRef.delete();


                     /*       final StorageReference desertRef = storage.child("image/" + "business/" + offerPushKey + "/" + "pic.jpg");
                            desertRef.delete();*/

                        Toast.makeText(context, "Student Deleted Sucessfully !", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, FragmentTest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    }
                }).create().show();

    }





}
















/*

public class StudentListAdapter extends BaseAdapter  {

    Context context;
    public ArrayList<Student> students;

    public StudentListAdapter(Context context,  ArrayList<Student> students) {
        this.context = context;
        this.students  = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.indexOf(getItem(position));
    }


    private class ViewHolder
    {
        TextView textName;
        TextView textAge;
        TextView textMobileNum;
        TextView textCollege;
        ImageView image;
        ListView simpleList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview, null);
            holder = new ViewHolder();

            holder.textName = (TextView) convertView.findViewById(R.id.textName);
            holder.textAge = (TextView) convertView.findViewById(R.id.textAge);
            holder.textMobileNum = (TextView) convertView.findViewById(R.id.textMobileNum);
            holder.textCollege = (TextView) convertView.findViewById(R.id.textCollege);
            holder.image=(ImageView) convertView.findViewById(R.id.image);
            holder.simpleList=(ListView) convertView.findViewById(R.id.simpleListView);

            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            StorageReference filePath= storageRef.child("/images/greenwall.jpg");

            holder.image.destroyDrawingCache();
            holder.image.setImageBitmap(null);

            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(filePath)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // <= ADDED
                    .skipMemoryCache(true) // <= ADDED
                    .into(holder.image);


            Student std_pos=students.get(position);

            holder.textName.setText(std_pos.Name );

            Log.d("NAME222222 : ",std_pos.Name);

            holder.textAge.setText(String.valueOf(std_pos.Age));
            holder.textMobileNum.setText(std_pos.MobileNum);
            holder.textCollege.setText(std_pos.College);


        }

        final Student stud=students.get(position);
        return convertView;
    }





}*/
