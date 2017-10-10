package com.example.android.wellnessjournal.Food;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.wellnessjournal.ExpandableListAdapter;
import com.example.android.wellnessjournal.Login.LoginActivity;
import com.example.android.wellnessjournal.MyImage;
import com.example.android.wellnessjournal.PicturesAdapter;
import com.example.android.wellnessjournal.R;
import com.example.android.wellnessjournal.Utils.BottomNavigationViewHelper;
import com.example.android.wellnessjournal.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maryline on 9/16/2017.
 */

public class FoodActivity extends AppCompatActivity implements PicturesAdapter.ListItemClickHandler , NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "FoodActivity";
    private Context mContext = FoodActivity.this;
    private static final int ACTIVITY_NUM = 1;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    TextView tv;
    Calendar mCurrentDate;
    int day, month, year;

    private List<MyImage> images;

    ToggleButton buttonGrid;
    ToggleButton buttonList;

    RecyclerView mPicturesGrid;
    PicturesAdapter pictureAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private FirebaseMethods mFirebaseMethods;

    ImageView ibCamera;


    final int CAMERA_REQUEST = 1100;

    private Uri mCapturedImageURI;
    private int imageCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Log.d(TAG, "onCreate: started.");


        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        tv = (TextView) findViewById(R.id.datePicker);
        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        tv.setText(day + "/" + month + "/" + year);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(FoodActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tv.setText(i2 + "/" + i1 + "/" + i);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        mPicturesGrid = (RecyclerView) findViewById(R.id.rv_pictures);


        GridLayoutManager layoutManager;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new GridLayoutManager(this, 4);
        }

        mPicturesGrid.setLayoutManager(layoutManager);

        images = new ArrayList<>();

        pictureAdapter = new PicturesAdapter(images, this);

        mPicturesGrid.setAdapter(pictureAdapter);

        buttonList = (ToggleButton) findViewById(R.id.toggleButtonList);
        buttonGrid = (ToggleButton) findViewById(R.id.toggleButtonGrid);

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FoodActivity.this, "YEAAAAAAAH !", Toast.LENGTH_SHORT).show();

                buttonList.setActivated(true);
                buttonList.setChecked(true);
                buttonGrid.setActivated(false);
                buttonGrid.setChecked(false);

                listView.setVisibility(view.VISIBLE);
                mPicturesGrid.setVisibility(view.INVISIBLE);
            }


        });

        buttonGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FoodActivity.this, "BOUUUH !", Toast.LENGTH_SHORT).show();

                buttonGrid.setActivated(true);
                buttonGrid.setChecked(true);
                buttonList.setActivated(false);
                buttonList.setChecked(false);

                listView.setVisibility(view.INVISIBLE);
                mPicturesGrid.setVisibility(view.VISIBLE);


            }
        });


        Toolbar toptoolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toptoolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupBottomNavigationView();




        ibCamera = (ImageView) findViewById(R.id.ic_camera);



        ibCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeTakePhoto();

            }
        });


        mFirebaseMethods = new FirebaseMethods(this);

        setupFirebaseAuth();
    }


    public void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FoodActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
            return;
        }
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            values);
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

            startActivityForResult(takePictureIntent, CAMERA_REQUEST);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST){

                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor =
                            getContentResolver().query(mCapturedImageURI, projection, null,
                                    null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(
                            MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String picturePath = cursor.getString(column_index_data);
                    MyImage image = new MyImage();
                    //image.setTitle("Test");
                    image.setDescription(
                            "test take a photo and add it to list view");
                    image.setDatetime(System.currentTimeMillis());
                    image.setPath(picturePath);
                    //image.setPictureUri(mCapturedImageURI);
                    images.add(image);

                Bitmap bm = BitmapFactory.decodeFile(picturePath);
                mFirebaseMethods.uploadNewPhoto("new_photo", images.size(), bm, picturePath );


            }
        }//end if resultCode
        pictureAdapter.setImages(images);
    }



    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: settingupBottomNavigationBar");

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Water intake");
        listDataHeader.add("Intermittent Fasting");
        listDataHeader.add("Special treats");
        listDataHeader.add("General comment");

        List<String> waterIntake = new ArrayList<>();
        waterIntake.add("How much water did you have today (in mL)?");

        List<String> intermittentFasting = new ArrayList<>();
        intermittentFasting.add("How many hours between last dinner and breakfast ?");


        List<String> specialTreats = new ArrayList<>();
        specialTreats.add("Processed food");
        specialTreats.add("Meat/Fish");
        specialTreats.add("Egg");
        specialTreats.add("Fried/Oil");
        specialTreats.add("white sugar");

        specialTreats.add("white flour");


        List<String> generalComment = new ArrayList<>();
        generalComment.add("Write your comment here");


        listHash.put(listDataHeader.get(0),waterIntake);
        listHash.put(listDataHeader.get(1),intermittentFasting);
        listHash.put(listDataHeader.get(2),specialTreats);
        listHash.put(listDataHeader.get(3),generalComment);
    }



    @Override
    public void onClick(int clickedItemIndex) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int Id = item.getItemId();

        if (Id== R.id.sign_out){
            mAuth.signOut();
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef2 = mFirebaseDatabase.getReference("user_photos");



        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                images.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MyImage image = snapshot.getValue(MyImage.class);
                    images.add(image);
                }
                pictureAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }});






        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                checkCurrentUser(user);

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    private void checkCurrentUser(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if(user == null){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }
    }





    private int getItemIndex (MyImage image) {
        int index = -1;

        for (int i = 0; i < images.size(); i++) {

            if (images.get(i).getPath().equals(image.getPath())) {
                index = i;
            }

        }
        return index;
    }


}
