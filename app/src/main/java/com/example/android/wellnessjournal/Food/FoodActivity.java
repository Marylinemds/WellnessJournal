package com.example.android.wellnessjournal.Food;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.wellnessjournal.ExpandableListAdapter;
import com.example.android.wellnessjournal.MyImage;
import com.example.android.wellnessjournal.PicturesAdapter;
import com.example.android.wellnessjournal.R;
import com.example.android.wellnessjournal.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maryline on 9/16/2017.
 */

public class FoodActivity extends AppCompatActivity implements PicturesAdapter.ListItemClickHandler{

    private static final String TAG = "FoodActivity";
    private Context mContext = FoodActivity.this;
    private static final int ACTIVITY_NUM = 1;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    private List<MyImage> images;

    ToggleButton buttonGrid;
    ToggleButton buttonList;

    RecyclerView mPicturesGrid;
    PicturesAdapter pictureAdapter;

    ImageView ibCamera;

    final int CAMERA_REQUEST = 1100;

    private Uri mCapturedImageURI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Log.d(TAG, "onCreate: started.");


        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        initData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listHash);
        listView.setAdapter(listAdapter);

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

        setupBottomNavigationView();

        ibCamera = (ImageView) findViewById(R.id.ic_camera);


        ibCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeTakePhoto();

            }
        });
    }


    private void activeTakePhoto() {
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
                    image.setTitle("Test");
                    image.setDescription(
                            "test take a photo and add it to list view");
                    image.setDatetime(System.currentTimeMillis());
                    image.setPath(picturePath);
                    image.setPictureUri(mCapturedImageURI);
                    images.add(image);


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
}
