package com.example.android.wellnessjournal.Food;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ToggleButton;

import com.example.android.wellnessjournal.ExpandableListAdapter;
import com.example.android.wellnessjournal.R;
import com.example.android.wellnessjournal.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Maryline on 9/16/2017.
 */

public class FoodActivity extends AppCompatActivity{

    private static final String TAG = "FoodActivity";
    private Context mContext = FoodActivity.this;
    private static final int ACTIVITY_NUM = 1;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Log.d(TAG, "onCreate: started.");

        listView = (ExpandableListView)findViewById(R.id.expandableListView);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        setupBottomNavigationView();

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
}
